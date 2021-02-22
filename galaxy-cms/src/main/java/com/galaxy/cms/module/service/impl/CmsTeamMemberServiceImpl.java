package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsTeamMemberMapper;
import com.galaxy.cms.module.model.TeamMember;
import com.galaxy.cms.module.service.CmsTeamMemberService;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/26.
*/
@Service
@Transactional
public class CmsTeamMemberServiceImpl extends AbstractService<TeamMember> implements CmsTeamMemberService {
    @Resource
    private CmsTeamMemberMapper cmsTeamMemberMapper;

    @Override
    public Result delete(Long id) {



        TeamMember teamMember=new TeamMember();
        teamMember.setId(id);
        teamMember.setIsDelete(true);
        update(teamMember);
        return ResultGenerator.genSuccessResult();
    }
}
