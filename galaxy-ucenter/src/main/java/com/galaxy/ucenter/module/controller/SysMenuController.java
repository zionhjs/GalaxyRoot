package com.galaxy.ucenter.module.controller;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.ucenter.module.model.SysMenu;
import com.galaxy.ucenter.module.service.SysMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2020/12/11.
*/
@RestController
@RequestMapping("/sys/menu")
@Api(tags = {"/sys/menu"}, description = "管理模块")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody SysMenu sysMenu) {
        sysMenu.setIsDelete(false);
        sysMenuService.save(sysMenu);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(sysMenu);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        SysMenu sysMenu=new SysMenu();
        sysMenu.setId(id);
        sysMenu.setIsDelete(true);
        sysMenuService.update(sysMenu);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody SysMenu sysMenu) {
        sysMenuService.update(sysMenu);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(sysMenu);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        SysMenu sysMenu = sysMenuService.findById(id);
        return ResultGenerator.genSuccessResult(sysMenu);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) SysMenu sysMenu) {
        PageHelper.startPage(page, size);
        sysMenu.setIsDelete(false);
        List<SysMenu> list = sysMenuService.findByModel(sysMenu);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
