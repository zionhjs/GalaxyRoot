package com.galaxy.cms.module.controller;

import com.galaxy.cms.module.model.TeamMember;
import com.galaxy.cms.module.service.CmsTeamMemberService;
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
* Created by CodeGenerator on 2020/12/26.
*/
@RestController
@RequestMapping("/team/member")
@Api(tags = {"/team/member"}, description = "团队成员管理模块")
public class CmsTeamMemberController {
    @Resource
    private CmsTeamMemberService cmsTeamMemberService;

    @ApiOperation(value = "新增团队成员", notes = "新增团队成员")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@RequestBody TeamMember teamMember) {
        teamMember.setCreatedAt(new Date());
        teamMember.setIsDelete(false);
        cmsTeamMemberService.save(teamMember);
        Result result= ResultGenerator.genSuccessResult();
        result.setData(teamMember);
        return result;
    }

    @ApiOperation(value = "删除团队成员", notes = "删除团队成员")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delete(@RequestParam Long id) {
        TeamMember teamMember=new TeamMember();
        teamMember.setId(id);
        teamMember.setIsDelete(true);
        cmsTeamMemberService.update(teamMember);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "修改团队成员", notes = "修改团队成员")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public Result update(@RequestBody TeamMember teamMember) {
        teamMember.setUpdatedAt(new Date());
        cmsTeamMemberService.update(teamMember);
        Result result=ResultGenerator.genSuccessResult();
        result.setData(teamMember);
        return result;
    }

    @ApiOperation(value = "获取团队成员单个详情", notes = "获取团队成员单个详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    public Result detail(@RequestParam Long id) {
        TeamMember teamMember = cmsTeamMemberService.findById(id);
        return ResultGenerator.genSuccessResult(teamMember);
    }

    @ApiOperation(value = "分页查询团队成员", notes = "分页查询团队成员")
    @RequestMapping(value = "/findByModal", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(@RequestParam(defaultValue="1",required=false) Integer page, @RequestParam(defaultValue="20",required=false) Integer size, @RequestBody(required =false) TeamMember teamMember) {
        PageHelper.startPage(page, size);
        teamMember.setIsDelete(false);
        List<TeamMember> list = cmsTeamMemberService.findByModel(teamMember);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
