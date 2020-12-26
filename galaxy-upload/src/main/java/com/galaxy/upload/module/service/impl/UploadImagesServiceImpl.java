package com.galaxy.upload.module.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.upload.module.mapper.UploadImagesMapper;
import com.galaxy.upload.module.model.Images;
import com.galaxy.upload.module.service.UploadImagesService;
import com.galaxy.upload.module.utils.ImageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UploadImagesServiceImpl extends AbstractService<Images> implements UploadImagesService {

    @Value("${galaxy.amazonProperties.imageBucketName}")
    private String imageBucketName;

    private static String markImg = "targetLogoFile.tmp";

    @Autowired
    private UploadImagesMapper uploadImagesMapper;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Override
    public Result uploadImages(MultipartFile multipartFile,String title,String description,String suffix,String level) {

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

        Images images = new Images();
        images.setCreatedAt(new Date());
        images.setTitle(title);
        images.setDescription(description);
        images.setSuffix(suffix);
        images.setLevel(level);
        images.setImageName(multipartFile.getOriginalFilename());
        images.setContentType(multipartFile.getContentType());
        images.setSize(multipartFile.getSize());
        try{

            //添加图片水印
            File file = ImageUtil.addPicMarkToMutipartFile(multipartFile, markImg);

            if (null == file){
                return ResultGenerator.genFailResult(ResultCode.IMAGEAS_LOGO_ERROR,"增加Logo错误，请重新上传图片");
            }

            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket(imageBucketName, file);
            //图片桶路径
            images.setS3Key240(s3Object240.getKey());
            //图片全路径
            images.setObjectUrl240("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
            //删除临时文件
            file.delete();
            save(images);
            return ResultGenerator.genSuccessResult(images);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_ERROR,"上传图片失败");
        }
    }

    @Override
    public Result uploadImagesUrl(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()){
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NOT_EXIST,"文件不存在");
        }

        try{
            //添加图片水印
            File file = ImageUtil.addPicMarkToMutipartFile(multipartFile, markImg);

            if (null == file){
                return ResultGenerator.genFailResult(ResultCode.IMAGEAS_LOGO_ERROR,"增加Logo错误，请重新上传图片");
            }

            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket(imageBucketName, file);
            //删除临时文件
            file.delete();
            return ResultGenerator.genSuccessResult("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_ERROR,"上传图片失败");
        }
    }

    @Override
    public Result uploadImagesDownload(MultipartFile multipartFile,HttpServletRequest request, HttpServletResponse response) {
        if (multipartFile.isEmpty()){
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NOT_EXIST,"文件不存在");
        }

        try{
            //添加图片水印
            File file = ImageUtil.addPicMarkToMutipartFile(multipartFile, markImg);

            if (null == file){
                return ResultGenerator.genFailResult(ResultCode.IMAGEAS_LOGO_ERROR,"增加Logo错误，请重新上传图片");
            }

            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket(imageBucketName, file);

            //下载图片到本地
            downloadImage("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey(),request,response);

            //删除临时文件
            file.delete();
            return ResultGenerator.genSuccessResult("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_ERROR,"上传图片失败");
        }
    }

    @Override
    public Result downloadImage(String imageName, HttpServletRequest request, HttpServletResponse response) {
        String fileUrl = imageName;
        if (fileUrl != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
           /* String realPath = request.getServletContext().getRealPath(
                    "//WEB-INF//");*/
            /*File file = new File(realPath, fileName);*/
            File file = new File(fileUrl);
            if (file.exists()) {
                //response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + imageName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return ResultGenerator.genFailResult(ResultCode.FILE_DOWNLOAD_ERROR,"文件下载失败");
    }

    @Override
    public Result updateImages(Images images) {
        if(images.getTitle() == null) {
            images.setTitle("title");
        }
        if(images.getDescription() == null) {
            images.setDescription("description");
        }
        if(images.getSuffix() == null) {
            images.setSuffix("ex");
        }
        if(images.getLevel() == null) {
            images.setLevel("star");
        }
        images.setUpdatedAt(new Date());
        update(images);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(images);
        return result;
    }

    @Override
    public Result delete(Long id) {

        Images images =  uploadImagesMapper.getImagesDetailsById(id);
        if (null == images){
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NULL_ERROR,"图片不存在或者已删除");
        }

        images.setId(id);
        images.setIsDelete(true);
        update(images);

        //删除远程图片
        amazonS3Client.deleteObject(new DeleteObjectRequest(imageBucketName, images.getS3Key240()));

        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result list(Integer page, Integer size, Images images) {
        PageHelper.startPage(page, size);
        List<Images> list = uploadImagesMapper.list(images);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 上传到亚马逊上去
     * @param bucketName
     * @param file
     * @return
     */
    public S3Object uploadFileToS3Bucket(final String bucketName, final File file) {
        final String objectKey = LocalDateTime.now() + "_" + file.getName();
        System.out.println("Uploading file with name= " + objectKey);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectKey, file);
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        return amazonS3Client.getObject(bucketName, objectKey);
    }
}
