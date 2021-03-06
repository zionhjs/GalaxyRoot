package com.galaxy.common.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.galaxy.common.core.constants.Constant;
import com.galaxy.common.core.utils.RedisUtil;
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
	private RedisUtil redisUtil;

	public Long getUserId() {
		String accessToken = request.getHeader(Constant.TOKEN_NAME);
		SysUserVo sysUserVo;
		try {
			sysUserVo = JSONObject.parseObject((String) redisUtil.get(Constant.REDIS_KEY_LOGIN + accessToken),SysUserVo.class);
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
		String accessToken = request.getHeader(Constant.TOKEN_NAME);
		SysUserVo sysUserVo;
		try {
			sysUserVo = JSONObject.parseObject((String) redisUtil.get(Constant.REDIS_KEY_LOGIN + accessToken),SysUserVo.class);
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
