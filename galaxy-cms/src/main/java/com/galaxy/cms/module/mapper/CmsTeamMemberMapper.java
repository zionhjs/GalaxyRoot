package com.galaxy.cms.module.mapper;

import com.galaxy.cms.module.model.TeamMember;
import com.galaxy.common.core.Mapper.Mapper;

import java.util.List;

public interface CmsTeamMemberMapper extends Mapper<TeamMember> {

    List<TeamMember> selectTeamMemberByTeamId(Long teamId);
}