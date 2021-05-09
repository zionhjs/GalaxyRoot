package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.BlogType;
import com.galaxy.cms.module.service.CmsBlogTypeService;
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
* Created by CodeGenerator on 2021/05/09.
*/
@RestController
@RequestMapping("/blog/type")
@Api(tags = {"/blog/type"}, description = "博客类别管理模块")
public class CmsBlogTypeController {
    @Resource
    private CmsBlogTypeService blogTypeService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public Result add(@RequestBody BlogType blogType) {
        blogType.setCreatedAt(new Date());
        blogType.setIsDelete(false);
        blogTypeService.save(blogType);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(blogType);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Result delete(@RequestParam Long id) {
        BlogType blogType=new BlogType();
        blogType.setId(id);
        blogType.setIsDelete(true);
        blogTypeService.update(blogType);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public Result update(@RequestBody BlogType blogType) {
        blogType.setUpdatedAt(new Date());
        blogTypeService.update(blogType);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(blogType);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public Result detail(@RequestParam Long id) {
        BlogType blogType = blogTypeService.findById(id);
        return ResultGenerator.genSuccessResult(blogType);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, BlogType blogType) {
        PageHelper.startPage(page, size);
        blogType.setIsDelete(false);
        List<BlogType> list = blogTypeService.findByModel(blogType);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
