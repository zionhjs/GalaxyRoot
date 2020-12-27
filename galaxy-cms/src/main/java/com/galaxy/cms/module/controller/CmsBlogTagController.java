package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.BlogTag;
import com.galaxy.cms.module.service.CmsBlogTagService;
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
@RequestMapping("/blog/tag")
@Api(tags = {"/blog/tag"}, description = "博客标签管理模块")
public class CmsBlogTagController {
    @Resource
    private CmsBlogTagService cmsBlogTagService;

    @ApiOperation(value = "新增博客标签", notes = "新增博客标签")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody BlogTag blogTag) {
        blogTag.setCreatedAt(new Date());
        blogTag.setIsDelete(false);
        cmsBlogTagService.save(blogTag);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(blogTag);
        return result;
    }

    @ApiOperation(value = "删除博客标签", notes = "删除博客标签")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        BlogTag blogTag=new BlogTag();
        blogTag.setId(id);
        blogTag.setIsDelete(true);
        cmsBlogTagService.update(blogTag);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改博客标签", notes = "修改博客标签")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody BlogTag blogTag) {
        blogTag.setUpdatedAt(new Date());
        cmsBlogTagService.update(blogTag);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(blogTag);
        return result;
    }

    @ApiOperation(value = "获取博客标签单个详情", notes = "获取博客标签单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        BlogTag blogTag = cmsBlogTagService.findById(id);
        return ResultGenerator.genSuccessResult(blogTag);
    }

    @ApiOperation(value = "分页查询博客标签", notes = "分页查询博客标签")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) BlogTag blogTag) {
        PageHelper.startPage(page, size);
        blogTag.setIsDelete(false);
        List<BlogTag> list = cmsBlogTagService.findByModel(blogTag);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
