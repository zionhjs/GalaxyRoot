package com.galaxy.ucenter.module.mapper;

import com.galaxy.common.core.Mapper.Mapper;
import com.galaxy.ucenter.module.model.SysMenu;

import java.util.List;

public interface SysMenuMapper extends Mapper<SysMenu> {

    List<Object> selectMenuByRoleId(String sysRoleId);

}