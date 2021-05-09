package com.galaxy.upload.module.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.galaxy.common.core.exception.BusinessException;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.common.core.utils.Logger;
import com.galaxy.upload.module.mapper.UploadVideoMapper;
import com.galaxy.upload.module.model.Video;
import com.galaxy.upload.module.service.UploadVideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UploadVideoServiceImpl extends AbstractService<Video> implements UploadVideoService {

    @Autowired
    private UploadVideoMapper uploadVideoMapper;

    @Value("${galaxy.amazonProperties.imageBucketName}")
    private String imageBucketName;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${galaxy.amazonProperties.videoBucketName}")
    private String videoBucketName;

    @Value("${galaxy.amazonProperties.video360BucketName}")
    private String video360BucketName;

    /**
     * 上传视频
     * @param multipartFile
     * @param title
     * @param description
     * @param suffix
     * @param level
     * @return
     */
    @Override
    public Result uploadVideo(MultipartFile multipartFile, String title, String description, String suffix, String level,Integer status,String statusName) {

        if (multipartFile.isEmpty()){
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NOT_EXIST,"文件不存在");
        }

        if(null == title) {
            title = "title";
        }
        if(null == description) {
            description = "description";
        }
        if(null == suffix) {
            suffix = "ex";
        }
        if(null == level) {
            level = "star";
        }
        if(null == status) {
            status = 1;
        }

        Video video = new Video();
        video.setStatus(status);
        video.setCreatedAt(new Date());
        video.setTitle(title);
        video.setDescription(description);
        video.setSuffix(suffix);
        video.setLevel(level);
        video.setVideoName(multipartFile.getOriginalFilename());
        video.setContentType(multipartFile.getContentType());
        video.setSize(multipartFile.getSize());
        video.setStatusName(statusName);
        try{

            //video.setThumbnail(new Binary(aws3Service.getVideoPic(multipartFile)));
            uploadFile(multipartFile, video);
            return ResultGenerator.genSuccessResult(video);
        }catch (Exception e){
            Logger.info(this, "上传视频失败" + e.getMessage());
            return ResultGenerator.genFailResult(ResultCode.VIDEO_ERROR,"上传视频失败" + e);
        }
    }

    @Override
    public Result uploadVideoUrl(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()){
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NOT_EXIST,"文件不存在");
        }

        try{
            File sourceFile = convertMultiPartFileToFile(multipartFile);


            // for encode Video to 480p / 720p / 1080p
            File targetFile480 = new File("target480.mp4");

            targetFile480 = encodeVideo(sourceFile, targetFile480, 854, 480);
            S3Object s3Object480 = uploadFileToS3Bucket(videoBucketName, targetFile480);

            //删除本地临时文件
            sourceFile.delete();
            targetFile480.delete();
            return ResultGenerator.genSuccessResult("https://" + videoBucketName + ".s3-us-west-1.amazonaws.com/" + s3Object480.getKey());
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.VIDEO_ERROR,"上传视频失败");
        }
    }

    //参数：视频路径和缩略图保存路径
    public Result fetchFrame(String videofile,Video video) {
        try {
            long start = System.currentTimeMillis();
            File targetFile = new File("out_frame_pic.png");
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
            ff.start();
            int length = ff.getLengthInFrames();
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 去掉前10帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabImage();
                if ((i > 10) && (f.image != null)) {
                    break;
                }
                i++;
            }
            ImageIO.write(FrameToBufferedImage(f), "jpg", targetFile);
            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket(imageBucketName, targetFile);
            //删除临时文件
            targetFile.delete();
            //ff.flush();
            ff.stop();
            System.out.println(System.currentTimeMillis() - start);
            //同步修改视频得封面推片
            update(video);
            return ResultGenerator.genSuccessResult("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.VIDEO_FRAME_IMAGEAS_ERROR,"截取帧数图片失败");
        }
    }

    @Override
    public Result list(Integer page, Integer size, Video video) {
        PageHelper.startPage(page, size);
        video.setIsDelete(false);
        List<Video> list = uploadVideoMapper.list(video);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public Result findByModalData(Integer page, Integer size, String title) {
        PageHelper.startPage(page, size);
        Video video = new Video();
        if ("{}".equalsIgnoreCase(title)){
            video.setTitle("");
        }else {
            video.setTitle(title);
        }
        List<Video> list = uploadVideoMapper.list(video);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public Result delete(Long id) {
        Video video=new Video();
        video.setId(id);
        video.setIsDelete(true);
        update(video);

        //删除远程视频
        amazonS3Client.deleteObject(new DeleteObjectRequest(videoBucketName, video.getS3Key480()));

        return ResultGenerator.genSuccessResult();
    }

    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    public void uploadFile(final MultipartFile multipartFile, Video video){
        try {
            //这个是原视频
            File sourceFile = convertMultiPartFileToFile(multipartFile);

            String bucketName = new String();

            //业务状态(1普通图片:2为360°图片)
            if (1 == video.getStatus()){
                bucketName = videoBucketName;
            }else if (2 == video.getStatus()){
                bucketName = video360BucketName;
            }

            video.setS3BucketName(bucketName);

        // for encode Video to 480p / 720p / 1080p
            File targetFile480 = new File("target480.mp4");

            targetFile480 = encodeVideo(sourceFile, targetFile480, 854, 480);
            S3Object s3Object480 = uploadFileToS3Bucket(bucketName, targetFile480);
            video.setS3Key480(s3Object480.getKey());
            video.setObjectUrl480("https://" + bucketName + ".s3-us-west-1.amazonaws.com/" + s3Object480.getKey());

            //视频添加封面图片
            Result result = fetchFrame(sourceFile.getName(),new Video());

            //删除本地临时文件
            sourceFile.delete();
            targetFile480.delete();

            video.setFrameImages(result.getData().toString());
            //添加视频记录
            save(video);
        } catch (final AmazonServiceException ex) {
            ex.printStackTrace();
            Logger.info(this,"File upload is failed.");
            Logger.info(this,"Error= {} while uploading file." + ex.getMessage());
        }
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile){
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
            outputStream.flush(); outputStream.close();
        } catch (final IOException e) {
            // LOGGER.error("Error converting the multi-part file to file= ", e.getMessage());
            e.printStackTrace();
            throw new BusinessException("转换失败");
        }
        return file;
    }

    // for encode file & resize File
    private File encodeVideo(java.io.File source, java.io.File target, int width, int height){
        // Audio
        AudioAttributes audio = new AudioAttributes();
        //设置编码器
        audio.setCodec("aac");
        //设置比特率
        audio.setBitRate(64000);
        audio.setChannels(2);
        //设置节录率
        audio.setSamplingRate(44100);

        // Video
        VideoAttributes video = new VideoAttributes();
        //设置编码器
        video.setCodec("h264");
        video.setX264Profile(VideoAttributes.X264_PROFILE.BASELINE);
        //设置大小
        video.setSize(new VideoSize(width,height));
        int rate = 64000;
        //设置比特率
        video.setBitRate(rate * (int)(height/480)*(int)(height/480)*2);
        //设置帧率(越小越清晰)
        video.setFrameRate(1);

        //转换
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp4");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);

        // Listener

        // execute
        try{
            MultimediaObject multimediaObject = new MultimediaObject(source);
            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, target, attrs);
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException("添加Logo失败");
        }
        return target;
    }

    private S3Object uploadFileToS3Bucket(final String bucketName, final File file) {
        final String objectKey = LocalDateTime.now() + "_" + file.getName();
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectKey, file);
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        return amazonS3Client.getObject(bucketName, objectKey);
    }

}
