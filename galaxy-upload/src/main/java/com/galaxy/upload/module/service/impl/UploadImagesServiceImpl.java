package com.galaxy.upload.module.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.upload.module.mapper.UploadImagesMapper;
import com.galaxy.upload.module.module.Images;
import com.galaxy.upload.module.service.UploadImagesService;
import com.galaxy.upload.module.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

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
        Images images = new Images();
        images.setTitle(title);
        images.setDescription(description);
        images.setSuffix(suffix);
        images.setLevel(level);
        try{
            //判断文件是否存在
            if (multipartFile == null){
                return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NOT_EXIST,"文件不存在");
            }

            //添加图片水印
            File file = ImageUtil.addPicMarkToMutipartFile(multipartFile, markImg);

            if (null == file){
                return ResultGenerator.genFailResult(ResultCode.IMAGEAS_LOGO_ERROR,"增加Logo错误，请重新上传图片");
            }

            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket(imageBucketName, file);
            images.setS3Key240("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
            //删除临时文件
            file.delete();
            save(images);
            return ResultGenerator.genSuccessResult(images);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_ERROR,"上传图片失败");
        }
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
