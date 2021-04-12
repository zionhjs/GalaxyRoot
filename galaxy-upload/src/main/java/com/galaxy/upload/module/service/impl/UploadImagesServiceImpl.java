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
import com.galaxy.common.core.utils.Logger;
import com.galaxy.upload.module.mapper.UploadImagesMapper;
import com.galaxy.upload.module.model.Images;
import com.galaxy.upload.module.service.UploadImagesService;
import com.galaxy.upload.module.utils.ImageUtil;
import com.galaxy.common.core.constants.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UploadImagesServiceImpl extends AbstractService<Images> implements UploadImagesService {

    @Value("${galaxy.amazonProperties.imageBucketName}")
    private String imageBucketName;

    @Value("${galaxy.amazonProperties.image360BucketName}")
    private String image360BucketName;

    //Linux文件下固定路径/home/ec2-user/targetLogoFile.png
    //Windows文件下固定路径D:\\targetLogoFile .png

    //private static String markImg = Constant.OS_PREFIX;

    private static String markImg = "logo.png";

    private static String notLogo = "notLogo.tmp";

    @Autowired
    private UploadImagesMapper uploadImagesMapper;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Override
    public Result uploadImages(MultipartFile multipartFile,String title,String description,String suffix,String level,Integer status,String statusName) {

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
        images.setStatusName(statusName);
        try{
            //添加图片水印
            File file = ImageUtil.addPicMarkToMutipartFile(multipartFile, markImg);

            if (null == file){
                return ResultGenerator.genFailResult(ResultCode.IMAGEAS_LOGO_ERROR,"增加Logo错误，请重新上传图片");
            }

            String bucketName = new String();

            //业务状态(1普通图片:2为360°图片)
            if ("360".equals(statusName)){
                bucketName = image360BucketName;
                images.setStatus(2);
            }else if ("interior".equals(statusName)){
                bucketName = imageBucketName;
                images.setStatus(3);
            }else if ("exterior".equals(statusName)){
                bucketName = imageBucketName;
                images.setStatus(4);
            } else {
                bucketName = imageBucketName;
                images.setStatus(1);
            }

            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket(bucketName, file);
            //图片桶路径
            images.setS3Key240(s3Object240.getKey());
            //图片全路径
            images.setObjectUrl240("https://" + bucketName + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());

            s3Object240.close();
            // 删除临时文件
            file.delete();

            save(images);
            return ResultGenerator.genSuccessResult(images);
        }catch (Exception e){
            Logger.info(this,e.getMessage());
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_ERROR,"上传图片失败" + e);
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
    public Result uploadImagesDownload(MultipartFile multipartFile) {
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
            saveImg("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());

            //删除临时文件
            file.delete();
            return ResultGenerator.genSuccessResult("下载图片地址 D:\\out.png");
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
    public Result testUploadImages(MultipartFile multipartFile) {
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

            saveImg("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());

            //删除临时文件
            file.delete();
            return ResultGenerator.genSuccessResult("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_ERROR,"上传图片失败");
        }
    }

    /**
     * 根据url下载图片到本地
     * @param imgeUrl 图片的路径
     */
    public Result saveImg(String imgeUrl) {
        BufferedImage bufferedImage = null;
        try {
            URL url=new URL(imgeUrl);
            URLConnection urlConnection=url.openConnection();
            HttpURLConnection httpURLConnection=(HttpURLConnection)urlConnection;
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK){
                InputStream inputStream=httpURLConnection.getInputStream();

                bufferedImage= ImageIO.read(inputStream);
                //参数二设置保存图片的格式
                //参数三设置图片保存地址
                ImageIO.write(bufferedImage,"png",new File("D:\\out.png"));
            }else {
                System.out.println("连接失败");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result uploadImagesNotLogo(MultipartFile multipartFile) {
        if (null == multipartFile){
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_NOT_EXIST,"文件不存在");
        }

        try{
            InputStream inputImg = multipartFile.getInputStream();
            Image img = ImageIO.read(inputImg);
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            //取到画笔
            Graphics2D g = bufImg.createGraphics();
            //画底片
            g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
            g.dispose();
            File outFile = new File("out_pic.png");
            ImageIO.write(bufImg, "png", outFile);//写图片

            if (null == outFile){
                return ResultGenerator.genFailResult(ResultCode.IMAGEAS_LOGO_ERROR,"增加Logo错误，请重新上传图片");
            }

            //上传图片
            S3Object s3Object240 = uploadFileToS3Bucket(imageBucketName, outFile);
            //删除临时文件
            outFile.delete();
            return ResultGenerator.genSuccessResult("https://" + "galaxy-image" + ".s3-us-west-1.amazonaws.com/" + s3Object240.getKey());
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.IMAGEAS_ERROR,"上传图片失败");
        }
    }

    @Override
    public Result findByModalData(Integer page, Integer size, String title) {
        PageHelper.startPage(page, size);
        Images images = new Images();
        if ("{}".equalsIgnoreCase(title)){
            images.setTitle("");
        }else {
            images.setTitle(title);
        }
        List<Images> list = uploadImagesMapper.list(images);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResultData(pageInfo);
    }

    @Override
    public Result batchUploadImages(MultipartFile[] multipartFile, String title, String description, String suffix, String level, Integer status, String statusName) {
        if (multipartFile.length==0){
            return ResultGenerator.genFailResult(ResultCode.FILEUPLOAD_ERROR,"上传文件不可为空");
        }
        List<Images> imagesList = new ArrayList<Images>();
        Images images;
        for (MultipartFile file : multipartFile){
            Result result = uploadImages(file,title,description,suffix,level,status,statusName);
            images = (Images) result.getData();
            imagesList.add((images));
        }
        return ResultGenerator.genSuccessResult(imagesList);
    }

    @Override
    public Result detail(Long id) {
        Images images = findById(id);
        if (null != images){
            //主要积分点击+1
            images.getRating();
        }
        return ResultGenerator.genSuccessResult(images);
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 根据url获取图片的路径
     */
    private String getImgPath(String imgUrl) {
        return imgUrl.substring(imgUrl.indexOf("n/") + 1, imgUrl.lastIndexOf("/"));
    }

    /**
     * 根据url获取图片的名称
     */
    private String getImgFileName(String imgUrl) {
        return imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
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
