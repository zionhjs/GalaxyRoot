package com.galaxy.cms.module.mapper;


import com.galaxy.cms.module.model.Team;
import com.galaxy.common.core.Mapper.Mapper;

import java.util.List;

public interface CmsTeamMapper extends Mapper<Team> {

    List<Team> list(Team team);

    Integer getCountById(Long id);
}