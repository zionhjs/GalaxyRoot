package com.galaxy.common.core.controller;

import com.galaxy.common.core.constants.Constant;
import com.galaxy.common.core.utils.RedisUtils;
import com.galaxy.common.core.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 增加公共controller，也可直接从ThreadLocal中取
 */
public class BaseController {

	@Autowired
	private HttpServletRequest request;

	@Resource
	private RedisUtils redisUtils;

	public Long getUserId() {
		String token = request.getHeader(Constant.TOKEN_NAME);
		SysUserVo sysUserVo;
		try {
			sysUserVo = (SysUserVo) redisUtils.get(Constant.REDIS_KEY_LOGIN + token);
		}catch (Exception e){
			return null;
			//throw new RedisException();
		}
		if (null == sysUserVo) {
			return null;
		}
		return sysUserVo.getUserId();
	}

	public SysUserVo getUser() {
		String token = request.getHeader(Constant.TOKEN_NAME);
		SysUserVo sysUserVo;
		try {
			sysUserVo = (SysUserVo) redisUtils.get(Constant.REDIS_KEY_LOGIN + token);
		}catch (Exception e){
			return null;
			//throw new RedisException();
		}
		if (null == sysUserVo) {
			return null;
		}
		return sysUserVo;
	}

}
