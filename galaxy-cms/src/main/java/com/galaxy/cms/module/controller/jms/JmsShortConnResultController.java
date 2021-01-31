package com.galaxy.cms.module.controller.jms;

import com.galaxy.cms.module.model.ShortConnResult;
import com.galaxy.cms.module.service.JmsShortConnResultService;
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
* Created by CodeGenerator on 2021/01/04.
*/
@RestController
@RequestMapping("/short/conn/result")
@Api(tags = {"/short/conn/result"}, description = "管理模块")
public class JmsShortConnResultController {
    @Resource
    private JmsShortConnResultService jmsShortConnResultService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody ShortConnResult shortConnResult) {
        shortConnResult.setCreateAt(new Date());
        shortConnResult.setIsDelete(false);
        jmsShortConnResultService.save(shortConnResult);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(shortConnResult);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        ShortConnResult shortConnResult=new ShortConnResult();
        shortConnResult.setId(id);
        shortConnResult.setIsDelete(true);
        jmsShortConnResultService.update(shortConnResult);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody ShortConnResult shortConnResult) {
        jmsShortConnResultService.update(shortConnResult);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(shortConnResult);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        ShortConnResult shortConnResult = jmsShortConnResultService.findById(id);
        return ResultGenerator.genSuccessResult(shortConnResult);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) ShortConnResult shortConnResult) {
        PageHelper.startPage(page, size);
        shortConnResult.setIsDelete(false);
        List<ShortConnResult> list = jmsShortConnResultService.findByModel(shortConnResult);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
