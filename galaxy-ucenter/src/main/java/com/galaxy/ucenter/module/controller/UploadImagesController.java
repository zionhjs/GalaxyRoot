package com.galaxy.ucenter.module.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.galaxy.common.core.utils.ImageUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/upload")
@Api(tags = {"/upload"}, description = "图片模块")
public class UploadImagesController {

    @Autowired
    private AmazonS3 amazonS3Client;

    private static String markImg = "D:\\targetLogoFile.png";

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String uploadFile(MultipartFile multipartFile){
        try{
            //判断文件是否存在
            if (multipartFile == null){
                return "文件不存在";
                //throw new RuntimeException("文件不存在");
            }

            //添加图片水印
            File file = ImageUtil.addPicMarkToMutipartFile(multipartFile, markImg);
            //添加文字水印
            //file = addWorkMarkToMutipartFile(file, "study");

            //获取文件的完整名称
            /*String originalFilename = multipartFile.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)){
                throw new RuntimeException("文件不存在");
            }*/

            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket("galaxy-image", file);
            System.out.println("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "文件上传成功";
    }

    public S3Object uploadFileToS3Bucket(final String bucketName, final File file) {
        final String objectKey = LocalDateTime.now() + "_" + file.getName();
        System.out.println("Uploading file with name= " + objectKey);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectKey, file);
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        return amazonS3Client.getObject(bucketName, objectKey);
    }

}
