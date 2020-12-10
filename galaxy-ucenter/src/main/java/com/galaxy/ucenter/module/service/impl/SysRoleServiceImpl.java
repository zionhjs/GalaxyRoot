package com.galaxy.ucenter.module.service.impl;

import com.galaxy.common.core.service.AbstractService;
import com.galaxy.ucenter.module.mapper.SysRoleMapper;
import com.galaxy.ucenter.module.model.SysRole;
import com.galaxy.ucenter.module.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysRoleServiceImpl extends AbstractService<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

}
