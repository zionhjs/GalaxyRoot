package com.galaxy.common.core;

import com.galaxy.common.core.vo.SysUserVo;

import java.util.Optional;

public class UserContext {
    private UserContext() {

    }

    private static UserContext userContext = new UserContext();

    private static ThreadLocal<SysUserVo> sysUserLocal = new ThreadLocal<SysUserVo>();

    public static void init(SysUserVo sysUserVo) {
        userContext.sysUserLocal.set(sysUserVo);
    }
    
    public static Optional<SysUserVo> getCurrentSysUser() {
    	if(null!=userContext.sysUserLocal.get()){
    		return	Optional.of(userContext.sysUserLocal.get());
    	}
    	
        return Optional.empty();
    }

    public static void clean() {
        userContext.sysUserLocal.remove();
    }

}

