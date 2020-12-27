package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.BlogImages;
import com.galaxy.cms.module.service.CmsBlogImagesService;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2020/12/27.
*/
@RestController
@RequestMapping("/blog/images")
@Api(tags = {"/blog/images"}, description = "博客图片管理模块")
public class CmsBlogImagesController {
    @Resource
    private CmsBlogImagesService cmsBlogImagesService;

    @ApiOperation(value = "批量添加博客图片", notes = "批量添加博客图片")
    @RequestMapping(value = "/batch", method = {RequestMethod.POST, RequestMethod.GET})
    public Result batch(@RequestBody List<BlogImages> blogImagesList) {
        cmsBlogImagesService.saveList(blogImagesList);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(blogImagesList);
        return result;
    }

    @ApiOperation(value = "新增博客图片", notes = "新增博客图片")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody BlogImages blogImages) {
        blogImages.setCreatedAt(new Date());
        blogImages.setIsDelete(false);
        cmsBlogImagesService.save(blogImages);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(blogImages);
        return result;
    }

    @ApiOperation(value = "删除博客图片", notes = "删除博客图片")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        BlogImages blogImages=new BlogImages();
        blogImages.setId(id);
        blogImages.setIsDelete(true);
        cmsBlogImagesService.update(blogImages);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改博客图片", notes = "修改博客图片")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody BlogImages blogImages) {
        blogImages.setUpdatedAt(new Date());
        cmsBlogImagesService.update(blogImages);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(blogImages);
        return result;
    }

    @ApiOperation(value = "获取博客图片单个详情", notes = "获取博客图片单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        BlogImages blogImages = cmsBlogImagesService.findById(id);
        return ResultGenerator.genSuccessResult(blogImages);
    }

    @ApiOperation(value = "分页查询博客图片", notes = "分页查询博客图片")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) BlogImages blogImages) {
        PageHelper.startPage(page, size);
        blogImages.setIsDelete(false);
        List<BlogImages> list = cmsBlogImagesService.findByModel(blogImages);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
