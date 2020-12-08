package com.galaxy.gateway.filter;

import com.galaxy.common.core.UserContext;
import com.galaxy.common.core.constants.Constant;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.RedisService;
import com.galaxy.common.core.utils.Logger;
import com.galaxy.common.core.vo.SysUserVo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

//增加该注解让过滤器生效
//简单使用ZuulFilter实现网关，后续优化
//网关过滤器暂时只用来校验assessToken是否失效
@Component
@AllArgsConstructor
public class LoginFilter extends ZuulFilter {

	@Autowired
	private RedisService redisService;


	/**
	 * 过滤器类型，前置过滤器
	 * @return
	 */
	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	/**
	 * 过滤器顺序，越小越先执行
	 * @return
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 返回true代表需要权限校验，false代表不需要用户校验即可访问
	 * @return
	 */
	@Override
	public boolean shouldFilter() {

		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		String url =  request.getRequestURI(); //获取请求的路径

		String[] s = url.split("/");

		if ("/swagger-ui.*,/static.*,/a2billing.*,/favicon.*,/webjars/springfox-swagger-ui.*,/v2/api-docs.*,/galaxy/user/login".
				contains(s[s.length-1])) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 业务逻辑
	 * 只有上面返回true的时候，才会进入到该方法
	 * @return
	 * @throws ZuulException
	 */
	@Override
	public Object run()  {
		Logger.info(this,"网关过滤器进来了");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		HttpServletResponse response = requestContext.getResponse();

		String accessToken = request.getHeader("accessToken");

		if (null != accessToken){
			SysUserVo sysUserVo = (SysUserVo) redisService.get(Constant.REDIS_KEY_LOGIN + accessToken);
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
