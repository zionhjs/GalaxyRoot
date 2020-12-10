package com.galaxy.ucenter.module.controller;

import com.galaxy.common.core.controller.BaseController;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.ucenter.module.model.SysRole;
import com.galaxy.ucenter.module.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/galaxy/role")
@Api(tags = {"/galaxy/role"}, description = "用户角色模块")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
    public Result add(@RequestBody SysRole sysRole) {
        sysRole.setIsDelete(false);
        sysRoleService.save(sysRole);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(sysRole);
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        SysRole sysRole=new SysRole();
        sysRole.setId(id);
        sysRole.setIsDelete(true);
        sysRoleService.update(sysRole);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
    public Result update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(sysRole);
        return result;
    }

    @ApiOperation(value = "获取单个详情", notes = "获取单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST,RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        SysRole sysRole = sysRoleService.findById(id);
        return ResultGenerator.genSuccessResult(sysRole);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST,RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) SysRole sysRole) {
        PageHelper.startPage(page, size);
        sysRole.setIsDelete(false);
        List<SysRole> list = sysRoleService.findByModel(sysRole);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
