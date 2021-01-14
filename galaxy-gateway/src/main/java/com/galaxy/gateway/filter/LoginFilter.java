package com.galaxy.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.common.core.config.MagConfig;
import com.galaxy.common.core.constants.Constant;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.utils.Logger;
import com.galaxy.common.core.utils.RedisUtil;
import com.galaxy.common.core.vo.SysUserVo;
import com.galaxy.gateway.vo.SysMenuVo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

//增加该注解让过滤器生效
//简单使用ZuulFilter实现网关，后续优化
//网关过滤器暂时只用来校验assessToken是否失效
@Component
@AllArgsConstructor
public class LoginFilter extends ZuulFilter {

	@Resource
	private RedisUtil redisUtil;

	@Autowired
	private MagConfig magConfig;

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

		//false 不拦截,true 拦截
		boolean tag = true;
		List<String> urlsList = Arrays.asList(magConfig.getNotCheckLoginUrl().split(","));
		for (String u :urlsList) {
			//如果包含的话那就是不拦截
			if (u.replaceAll("/","").equalsIgnoreCase(s[s.length-1])){
				tag = false;
				break;
			}
		}
		return tag;
	}

	@Override
	public Object run()  {
		Logger.info(this,"网关过滤器进来了");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		HttpServletResponse response = requestContext.getResponse();

		//允许所有来源访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		//允许访问的方式
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		//本次预检请求的有效期
		response.setHeader("Access-Control-Max-Age", "3600");
		//请求头
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Origin, Accept, accessToken");

		String url = request.getRequestURI(); //获取请求的路径
		String[] s = url.split("/");

		String accessToken = request.getHeader("accessToken");
		Result result = new Result();
		if (null != accessToken){
			SysUserVo sysUserVo = JSONObject.parseObject((String) redisUtil.get(Constant.REDIS_KEY_LOGIN + accessToken),SysUserVo.class);
			//SysUserVo sysUserVo = (SysUserVo) redisUtils.get(Constant.REDIS_KEY_LOGIN + accessToken);
			if (null != sysUserVo){
				long curTime = System.currentTimeMillis();
				if (curTime > sysUserVo.getExpireTime()) {
					Logger.info(this, "accessToken 失效--" + ":curTime is " + curTime + " expireTime is " + sysUserVo.getExpireTime());
					result = new Result();
					result.setCode(ResultCode.OUT_TIME_TOKEN);
					result.setMessage("accessToken 失效");
					try {
						ResultGenerator.writeResponse(response,result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {

					int i = 0;

					//增加权限校验
					if (sysUserVo.getSysMenuList().size() > 0 ){
						for (Object obj :sysUserVo.getSysMenuList()) {
							ObjectMapper mapper = new ObjectMapper();
							SysMenuVo sysMenuVo = mapper.convertValue(obj, SysMenuVo.class);
							if (sysMenuVo.getLinkUrl().contains(s[s.length-1])){
								i = ++i;
							}
						}
						if (0 == i){
							result = new Result();
							result.setCode(ResultCode.USER_PERMISSION_ERROR);
							result.setMessage("暂无权限使用此功能");
							try {
								ResultGenerator.writeResponse(response,result);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

					//校验通过
					//存入本地线程变量
					//UserContext.init(sysUserVo);
				}
			}else {
				result = new Result();
				result.setCode(ResultCode.USER_LOGIN_CHANNEL_ERROR);
				result.setMessage("用户未登录,请重新登录");
				try {
					ResultGenerator.writeResponse(response,result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			result = new Result();
			result.setCode(ResultCode.NOT_EXIST_TOKEN_EXCEPTION);
			result.setMessage("accessToken不存在(请重新登录再访问)");
			try {
				ResultGenerator.writeResponse(response,result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
