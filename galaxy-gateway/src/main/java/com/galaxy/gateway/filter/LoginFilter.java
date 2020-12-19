package com.galaxy.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.galaxy.common.core.UserContext;
import com.galaxy.common.core.constants.Constant;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.utils.Logger;
import com.galaxy.common.core.utils.RedisUtils;
import com.galaxy.common.core.vo.SysUserVo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

//增加该注解让过滤器生效
//简单使用ZuulFilter实现网关，后续优化
//网关过滤器暂时只用来校验assessToken是否失效
@Component
@AllArgsConstructor
public class LoginFilter extends ZuulFilter {

	@Resource
	private RedisUtils redisUtils;

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {

		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		String url =  request.getRequestURI(); //获取请求的路径

		String[] s = url.split("/");

		if ("/swagger-ui.*,/static.*,/a2billing.*,/favicon.*,/webjars/springfox-swagger-ui.*,/v2/api-docs.*,/galaxy/user/login,/galaxy/user/logout,/galaxy/user/add".
				contains(s[s.length-1])) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public Object run()  {
		Logger.info(this,"网关过滤器进来了");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		HttpServletResponse response = requestContext.getResponse();

		String accessToken = request.getHeader("accessToken");

		if (null != accessToken){
			SysUserVo sysUserVo = JSONObject.parseObject((String) redisUtils.get(Constant.REDIS_KEY_LOGIN + accessToken),SysUserVo.class);
			//SysUserVo sysUserVo = (SysUserVo) redisUtils.get(Constant.REDIS_KEY_LOGIN + accessToken);
			if (null != sysUserVo){
				long curTime = System.currentTimeMillis();
				if (curTime > sysUserVo.getExpireTime()) {
					Logger.info(this, "token 失效--" + ":curTime is " + curTime + " expireTime is " + sysUserVo.getExpireTime());
					Result result = new Result();
					result.setCode(ResultCode.OUT_TIME_TOKEN);
					result.setMessage("token 失效");
					try {
						ResultGenerator.writeResponse(response,result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					//校验通过
					//存入本地线程变量
					UserContext.init(sysUserVo);
				}
			}else {
				Result result = new Result();
				result.setCode(ResultCode.USER_LOGIN_CHANNEL_ERROR);
				result.setMessage("用户未登录,请重新登录");
				try {
					ResultGenerator.writeResponse(response,result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			Result result = new Result();
			result.setCode(ResultCode.NOT_EXIST_TOKEN_EXCEPTION);
			result.setMessage("token不存在(请重新登录再访问)");
			try {
				ResultGenerator.writeResponse(response,result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
