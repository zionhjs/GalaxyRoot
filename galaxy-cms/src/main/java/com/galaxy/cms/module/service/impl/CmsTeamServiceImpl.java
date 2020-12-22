package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsTeamMapper;
import com.galaxy.cms.module.model.Team;
import com.galaxy.cms.module.service.CmsTeamService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/22.
*/
@Service
@Transactional
public class CmsTeamServiceImpl extends AbstractService<Team> implements CmsTeamService {

    @Resource
    private CmsTeamMapper cmsTeamMapper;

}
