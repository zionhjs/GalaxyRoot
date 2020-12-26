package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.Team;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;


/**
* Created by CodeGenerator on 2020/12/22.
*/
public interface CmsTeamService extends Service<Team> {

    Result add(Team team);

    Result list(Integer page, Integer size, Team team);

    Result detail(Long id);
}
