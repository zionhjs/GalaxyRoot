package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.Blog;
import com.galaxy.cms.module.service.CmsBlogService;
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
* Created by CodeGenerator on 2020/12/23.
*/
@RestController
@RequestMapping("/blog")
@Api(tags = {"/blog"}, description = "博客管理模块")
public class CmsBlogController {

    @Resource
    private CmsBlogService cmsBlogService;

    @ApiOperation(value = "首页统一查询", notes = "首页统一查询")
    @RequestMapping(value = "/homeFindByTitle", method = {RequestMethod.POST, RequestMethod.GET})
    public Result homeFindByTitle(@RequestParam(defaultValue="1",required = false) Integer page,
                       @RequestParam(defaultValue="20",required = false) Integer size,
                       @RequestBody(required = false) String title) {
        return cmsBlogService.homeFindByTitle(page, size , title);
    }

    @ApiOperation(value = "新增博客", notes = "新增博客")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody Blog blog) {
        blog.setCreatedAt(new Date());
        blog.setIsDelete(false);
        cmsBlogService.save(blog);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(blog);
        return result;
    }

    @ApiOperation(value = "删除博客", notes = "删除博客")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        Blog blog=new Blog();
        blog.setId(id);
        blog.setIsDelete(true);
        cmsBlogService.update(blog);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改博客", notes = "修改博客")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody Blog blog) {
        blog.setUpdatedAt(new Date());
        cmsBlogService.update(blog);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(blog);
        return result;
    }

    @ApiOperation(value = "获取博客单个详情", notes = "获取博客单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        return cmsBlogService.detail(id);
    }

    @ApiOperation(value = "分页查询博客", notes = "分页查询博客")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) Blog blog) {
        return cmsBlogService.list(page, size,blog);
    }
}
