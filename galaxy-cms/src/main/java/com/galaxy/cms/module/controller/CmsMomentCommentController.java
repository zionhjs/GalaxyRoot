package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.MomentComment;
import com.galaxy.cms.module.service.CmsMomentCommentService;
import com.galaxy.common.core.controller.BaseController;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.utils.Logger;
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
@Api(tags = {"/moment/comment"}, description = "评论管理模块")
public class CmsMomentCommentController extends BaseController {
    @Resource
    private CmsMomentCommentService cmsMomentCommentService;

    @ApiOperation(value = "新增评论", notes = "新增评论")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody MomentComment momentComment) {
        Logger.info(this, "/moment/comment/add 新增评论接口入参--->" + momentComment);
        momentComment.setCreatedBy(String.valueOf(super.getUserId()));
        return cmsMomentCommentService.add(momentComment);
    }

    @ApiOperation(value = "删除评论", notes = "删除评论")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        MomentComment momentComment=new MomentComment();
        momentComment.setId(id);
        momentComment.setIsDelete(true);
        cmsMomentCommentService.update(momentComment);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改评论", notes = "修改评论")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody MomentComment momentComment) {
        momentComment.setUpdatedAt(new Date());
        cmsMomentCommentService.update(momentComment);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(momentComment);
        return result;
    }

    @ApiOperation(value = "获取评论单个详情", notes = "获取评论单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        MomentComment momentComment = cmsMomentCommentService.findById(id);
        return ResultGenerator.genSuccessResult(momentComment);
    }

    @ApiOperation(value = "分页查询评论", notes = "分页查询评论")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) MomentComment momentComment) {
        PageHelper.startPage(page, size);
        momentComment.setIsDelete(false);
        List<MomentComment> list = cmsMomentCommentService.findByModel(momentComment);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
