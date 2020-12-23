package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.MomentLike;
import com.galaxy.cms.module.service.CmsMomentLikeService;
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
@RequestMapping("/moment/like")
@Api(tags = {"/moment/like"}, description = "管理模块")
public class MomentLikeController {
    @Resource
    private CmsMomentLikeService cmsMomentLikeService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody MomentLike momentLike) {
        momentLike.setCreateTime(new Date());
        momentLike.setIsDelete(false);
        cmsMomentLikeService.save(momentLike);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(momentLike);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        MomentLike momentLike=new MomentLike();
        momentLike.setId(id);
        momentLike.setIsDelete(true);
        cmsMomentLikeService.update(momentLike);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody MomentLike momentLike) {
        cmsMomentLikeService.update(momentLike);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(momentLike);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        MomentLike momentLike = cmsMomentLikeService.findById(id);
        return ResultGenerator.genSuccessResult(momentLike);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) MomentLike momentLike) {
        PageHelper.startPage(page, size);
        momentLike.setIsDelete(false);
        List<MomentLike> list = cmsMomentLikeService.findByModel(momentLike);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
