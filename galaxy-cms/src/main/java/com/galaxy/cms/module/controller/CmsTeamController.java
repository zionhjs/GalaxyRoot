package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.Team;
import com.galaxy.cms.module.service.CmsTeamService;
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

@RestController
@RequestMapping(value = "/team")
@Api(tags = {"/team"}, description = "团队管理模块")
public class CmsTeamController extends BaseController {

    @Resource
    private CmsTeamService cmsTeamService;

    @ApiOperation(value = "新增团队", notes = "新增团队")
    @RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.GET})
    public Result add(@RequestBody Team team) {
        team.setCreatedAt(new Date());
        team.setIsDelete(false);
        cmsTeamService.save(team);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(team);
        return result;
    }

    @ApiOperation(value = "删除团队", notes = "删除团队")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        Team team=new Team();
        team.setId(id);
        team.setIsDelete(true);
        cmsTeamService.update(team);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改团队", notes = "修改团队")
    @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET})
    public Result update(@RequestBody Team team) {
        cmsTeamService.update(team);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(team);
        return result;
    }

    @ApiOperation(value = "获取团队单个详情", notes = "获取团队单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST,RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        Team team = cmsTeamService.findById(id);
        return ResultGenerator.genSuccessResult(team);
    }

    @ApiOperation(value = "分页查询团队", notes = "分页查询团队")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST,RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page,@RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) Team team) {
        PageHelper.startPage(page, size);
        team.setIsDelete(false);
        List<Team> list = cmsTeamService.findByModel(team);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
