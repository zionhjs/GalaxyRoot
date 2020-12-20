package com.galaxy.upload.module.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class UploadVideoServiceImpl extends AbstractService<Video> implements UploadVideoService {

    @Autowired
    private UploadVideoMapper uploadVideoMapper;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${galaxy.amazonProperties.videoBucketName}")
    private String videoBucketName;

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
    public Result uploadVideo(MultipartFile multipartFile, String title, String description, String suffix, String level) {

        if (multipartFile.isEmpty()){
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NOT_EXIST,"文件不存在");
        }

        if(title == null) {
            title = "title";
        }
        if(description == null) {
            description = "description";
        }
        if(suffix == null) {
            suffix = "ex";
        }
        if(level == null) {
            level = "star";
        }

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setSuffix(suffix);
        video.setLevel(level);
        video.setVideoName(multipartFile.getOriginalFilename());
        video.setContentType(multipartFile.getContentType());
        video.setSize(multipartFile.getSize());
        try{

            //video.setThumbnail(new Binary(aws3Service.getVideoPic(multipartFile)));
            uploadFile(multipartFile, video);
            return ResultGenerator.genSuccessResult(video);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.VIDEO_ERROR,"上传视频失败");
        }
    }

    @Override
    public Result testUploadVideo(MultipartFile multipartFile) {
        return null;
        //S3Object s3Object480 = uploadFileToS3Bucket(videoBucketName, targetFile480);
        /*System.out.println("https://" + videoBucketName + ".s3-us-west-1.amazonaws.com/" + s3Object480.getKey());
        return ResultGenerator.genSuccessResult("https://" + videoBucketName + ".s3-us-west-1.amazonaws.com/" + s3Object480.getKey());*/
    }

    public void uploadFile(final MultipartFile multipartFile, Video video){
        try {
            File sourceFile = convertMultiPartFileToFile(multipartFile);

            video.setS3BucketName(videoBucketName);

            // for encode Video to 480p / 720p / 1080p
            File targetFile480 = new File("target480.mp4");

            targetFile480 = encodeVideo(sourceFile, targetFile480, 854, 480);
            S3Object s3Object480 = uploadFileToS3Bucket(videoBucketName, targetFile480);
            video.setS3Key480(s3Object480.getKey());
            video.setObjectUrl480("https://" + videoBucketName + ".s3-us-west-1.amazonaws.com/" + s3Object480.getKey());

            //删除本地临时文件
            sourceFile.delete();
            targetFile480.delete();
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
