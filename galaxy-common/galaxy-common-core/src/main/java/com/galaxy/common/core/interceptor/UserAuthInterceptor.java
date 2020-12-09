package com.galaxy.common.core.interceptor;

import com.galaxy.common.core.utils.Logger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现接口校验
 */
@Slf4j
@Component
@AllArgsConstructor
public class UserAuthInterceptor implements HandlerInterceptor {

	private RedisTemplate redisTemplate;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		String url = request.getRequestURI();

		String[] s = url.split("/");

		//登录/注册/找回密码不需要拦截
		if ("/galaxy/user/login".contains(s[s.length-1])) {
			return true;
		}else {
			//默认没有接口权限 返回false 失败
			Logger.info(this,"没有接口权限");
			return false;
		}
	}


	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
