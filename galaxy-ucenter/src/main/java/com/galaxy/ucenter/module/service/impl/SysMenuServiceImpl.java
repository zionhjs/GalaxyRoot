package com.galaxy.ucenter.module.service.impl;

import com.galaxy.common.core.service.AbstractService;
import com.galaxy.ucenter.module.mapper.SysMenuMapper;
import com.galaxy.ucenter.module.model.SysMenu;
import com.galaxy.ucenter.module.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/11.
*/
@Service
@Transactional
public class SysMenuServiceImpl extends AbstractService<SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper tSysMenuMapper;

}
