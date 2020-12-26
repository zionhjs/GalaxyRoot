package com.galaxy.upload.module.controller;

import com.galaxy.common.core.controller.BaseController;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.utils.HttpClientUtil;
import com.galaxy.upload.module.model.Images;
import com.galaxy.upload.module.service.UploadImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

@RestController
@RequestMapping(value = "/images")
@Api(tags = {"/images"}, description = "图片管理模块")
public class UploadImagesController extends BaseController {

    @Autowired
    private UploadImagesService uploadImagesService;

    /**
     * *  下载单个文件
     * @return
     * @author jiejing
     */
    @RequestMapping(value = "/toDowmNoteByVid", method = RequestMethod.POST)
    public void toDowmNoteByVid(HttpServletRequest request, HttpServletResponse response) {
        //url
        String notesUrl = "https://galaxy-image.s3-us-west-1.amazonaws.com/2020-12-25T04:43:04.762_out_pic.png";
        //下载文件名称
        String notesName = "百度图片";
        ServletOutputStream out = null;
        InputStream inputStream = null;

        try {
            //文件名
            String pdfName = notesName ;
            //路径
            String path = notesUrl ;
            // 获取外部文件流
            //logger.info("下载中------invPdfUrl=" +path);
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            /**
             * 输出文件到浏览器
             */
            int len = 0;
            // 输出 下载的响应头，如果下载的文件是中文名，文件名需要经过url编码
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(pdfName, "UTF-8"));
            response.setHeader("Cache-Control", "no-cache");
            out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            System.out.println("下载完成");
        } catch (Exception e) {
            System.out.println("下载异常");
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @ApiOperation(value = "根据url下载图片", notes = "根据url下载图片")
    @RequestMapping(value = "/saveImg", method = RequestMethod.POST)
    public Result saveImg(@RequestParam String url){
        return uploadImagesService.saveImg(url);
    }

    @ApiOperation(value = "测试上传图片", notes = "测试上传图片")
    @RequestMapping(value = "/testUploadImages", method = RequestMethod.POST)
    public Result testUploadImages(@RequestParam MultipartFile multipartFile){
        return uploadImagesService.testUploadImages(multipartFile);
    }

    @ApiOperation(value = "单个文件下载", notes = "单个文件下载")
    @RequestMapping(value = "/downloadImage", method = {RequestMethod.POST,RequestMethod.GET})
    public Result downloadImage(@RequestParam String imageName, HttpServletRequest request, HttpServletResponse response){
        return uploadImagesService.downloadImage(imageName,request,response);
    }

    @ApiOperation(value = "上传图片成功之后自动下载", notes = "上传图片成功之后自动下载")
    @RequestMapping(value = "/uploadImagesDownload", method = RequestMethod.POST)
    public Result uploadImagesDownload(@RequestBody MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response){
        return uploadImagesService.uploadImagesDownload(multipartFile);
    }

    @ApiOperation(value = "上传图片只返回url", notes = "上传图片只返回url")
    @RequestMapping(value = "/uploadImagesUrl", method = RequestMethod.POST)
    public Result uploadImagesUrl(@RequestBody MultipartFile multipartFile){
        return uploadImagesService.uploadImagesUrl(multipartFile);
    }

    @ApiOperation(value = "上传图片直接保存", notes = "上传图片直接保存")
    @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
    public Result uploadImages(@RequestParam MultipartFile multipartFile,
                               @RequestParam(value="title") String title,
                               @RequestParam(value="description") String description,
                               @RequestParam(value = "suffix") String suffix,
                               @RequestParam(value="level") String level){
        return uploadImagesService.uploadImages(multipartFile,title,description,suffix,level);
    }

    @ApiOperation(value = "修改图片", notes = "修改图片")
    @RequestMapping(value = "/updateImages", method = {RequestMethod.POST,RequestMethod.GET})
    public Result updateImages(@RequestBody Images images) {
        return uploadImagesService.updateImages(images);
    }

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
    public Result add(@RequestBody Images images) {
        images.setCreatedAt(new Date());
        images.setIsDelete(false);
        uploadImagesService.save(images);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(images);
        return result;
    }

    @ApiOperation(value = "删除图片", notes = "删除图片")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        return uploadImagesService.delete(id);
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST,RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        Images images = uploadImagesService.findById(id);
        return ResultGenerator.genSuccessResult(images);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST,RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page,@RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) Images images) {
        return uploadImagesService.list(page,size,images);
    }


}
