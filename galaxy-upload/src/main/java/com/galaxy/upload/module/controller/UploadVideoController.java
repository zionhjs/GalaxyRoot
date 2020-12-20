package com.galaxy.upload.module.controller;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.upload.module.model.Video;
import com.galaxy.upload.module.service.UploadVideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/galaxy/video")
@Api(tags = {"/galaxy/video"}, description = "视频管理模块")
public class UploadVideoController {

    @Autowired
    private UploadVideoService uploadVideoService;

    @ApiOperation(value = "上传视频", notes = "上传视频")
    @RequestMapping(value = "/uploadVideo", method = {RequestMethod.POST,RequestMethod.GET})
    public Result uploadVideo(@RequestParam(value = "multipartFile") MultipartFile multipartFile,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value="description") String description,
                              @RequestParam(value = "suffix") String suffix,
                              @RequestParam(value="level") String level){
        return uploadVideoService.uploadVideo(multipartFile,title,description,suffix,level);
    }

    @ApiOperation(value = "新增视频", notes = "新增视频")
    @RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
    public Result add(@RequestBody Video video) {
        video.setCreatedAt(new Date());
        video.setIsDelete(false);
        uploadVideoService.save(video);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(video);
        return result;
    }

    @ApiOperation(value = "删除视频", notes = "删除视频")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        Video video=new Video();
        video.setId(id);
        video.setIsDelete(true);
        uploadVideoService.update(video);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改视频", notes = "修改视频")
    @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
    public Result update(@RequestBody Video video) {
        uploadVideoService.update(video);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(video);
        return result;
    }

    @ApiOperation(value = "获取视频单个详情", notes = "获取视频单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST,RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        Video video = uploadVideoService.findById(id);
        return ResultGenerator.genSuccessResult(video);
    }

    @ApiOperation(value = "分页查询视频", notes = "分页查询视频")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST,RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page,@RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) Video video) {
        PageHelper.startPage(page, size);
        video.setIsDelete(false);
        List<Video> list = uploadVideoService.findByModel(video);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
