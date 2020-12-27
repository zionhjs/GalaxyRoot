package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.MomentLike;
import com.galaxy.cms.module.service.CmsMomentLikeService;
import com.galaxy.common.core.controller.BaseController;
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
@Api(tags = {"/moment/like"}, description = "点赞管理模块")
public class CmsMomentLikeController extends BaseController {
    @Resource
    private CmsMomentLikeService cmsMomentLikeService;

    @ApiOperation(value = "新增点赞", notes = "新增点赞")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody MomentLike momentLike) {
        momentLike.setCreateBy(String.valueOf(super.getUserId()));
        return cmsMomentLikeService.add(momentLike);
    }

    @ApiOperation(value = "删除点赞", notes = "删除点赞")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        MomentLike momentLike=new MomentLike();
        momentLike.setId(id);
        momentLike.setIsDelete(true);
        cmsMomentLikeService.update(momentLike);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "取消点赞", notes = "取消点赞")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result updateLike(@RequestBody MomentLike momentLike) {
        momentLike.setCreateBy(String.valueOf(super.getUserId()));
        return cmsMomentLikeService.updateLike(momentLike);
    }

    @ApiOperation(value = "获取点赞单个详情", notes = "获取点赞单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        MomentLike momentLike = cmsMomentLikeService.findById(id);
        return ResultGenerator.genSuccessResult(momentLike);
    }

    @ApiOperation(value = "分页查询点赞", notes = "分页查询点赞")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) MomentLike momentLike) {
        PageHelper.startPage(page, size);
        momentLike.setIsDelete(false);
        List<MomentLike> list = cmsMomentLikeService.findByModel(momentLike);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
