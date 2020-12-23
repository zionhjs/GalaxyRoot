package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.MomentComment;
import com.galaxy.cms.module.service.CmsMomentCommentService;
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
@RequestMapping("/moment/comment")
@Api(tags = {"/moment/comment"}, description = "管理模块")
public class CmsMomentCommentController {
    @Resource
    private CmsMomentCommentService cmsMomentCommentService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody MomentComment momentComment) {
        momentComment.setCreatedAt(new Date());
        momentComment.setIsDelete(false);
        cmsMomentCommentService.save(momentComment);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(momentComment);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        MomentComment momentComment=new MomentComment();
        momentComment.setId(id);
        momentComment.setIsDelete(true);
        cmsMomentCommentService.update(momentComment);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody MomentComment momentComment) {
        momentComment.setUpdatedAt(new Date());
        cmsMomentCommentService.update(momentComment);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(momentComment);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        MomentComment momentComment = cmsMomentCommentService.findById(id);
        return ResultGenerator.genSuccessResult(momentComment);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) MomentComment momentComment) {
        PageHelper.startPage(page, size);
        momentComment.setIsDelete(false);
        List<MomentComment> list = cmsMomentCommentService.findByModel(momentComment);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
