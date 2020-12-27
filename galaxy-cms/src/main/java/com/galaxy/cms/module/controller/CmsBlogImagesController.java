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
@Api(tags = {"/blog/images"}, description = "管理模块")
public class CmsBlogImagesController {
    @Resource
    private CmsBlogImagesService cmsBlogImagesService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody BlogImages blogImages) {
        blogImages.setCreatedAt(new Date());
        blogImages.setIsDelete(false);
        cmsBlogImagesService.save(blogImages);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(blogImages);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        BlogImages blogImages=new BlogImages();
        blogImages.setId(id);
        blogImages.setIsDelete(true);
        cmsBlogImagesService.update(blogImages);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody BlogImages blogImages) {
        blogImages.setUpdatedAt(new Date());
        cmsBlogImagesService.update(blogImages);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(blogImages);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        BlogImages blogImages = cmsBlogImagesService.findById(id);
        return ResultGenerator.genSuccessResult(blogImages);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) BlogImages blogImages) {
        PageHelper.startPage(page, size);
        blogImages.setIsDelete(false);
        List<BlogImages> list = cmsBlogImagesService.findByModel(blogImages);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
