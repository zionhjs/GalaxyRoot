package com.galaxy.gateway.filter;

import com.netflix.zuul.exception.ZuulException;

//增加该注解让过滤器生效
//简单使用ZuulFilter实现网关，后续优化
/*@Component
@AllArgsConstructor*/
public class LoginFilter {

	/*private JWTUtils jwtutils;

	@Autowired
	private MagConfig magConfig;

	*//**
	 * 过滤器类型，前置过滤器
	 * @return
	 *//*
	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	*//**
	 * 过滤器顺序，越小越先执行
	 * @return
	 *//*
	@Override
	public int filterOrder() {
		return 0;
	}

	*//**
	 * 返回true代表需要权限校验，false代表不需要用户校验即可访问
	 * @return
	 *//*
	@Override
	public boolean shouldFilter() {

		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		String url =  request.getRequestURI(); //获取请求的路径

		String[] s = url.split("/");

		if (magConfig.getNotCheckLoginUrl().contains(s[s.length-1])) {
			return false;
		}else {
			return true;
		}
	}

	*//**
	 * 业务逻辑
	 * 只有上面返回true的时候，才会进入到该方法
	 * @return
	 * @throws ZuulException
	 *//*
	@Override
	public Object run()  {
		System.out.println("网关过滤器进来了");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		HttpServletResponse response = requestContext.getResponse();

		String accessToken = request.getHeader("accessToken");
		if (StringUtils.isBlank(accessToken)) {
			try {
				ResponseUtil.writeResponse(response,ResponseUtil.failure(ResultCode.ERROR_20006.code(),ResultCode.ERROR_20006.message()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		try {
			boolean check = jwtutils.verifyAccessToken(accessToken);
			if (!check){
				try {
					ResponseUtil.writeResponse(response,ResponseUtil.failure(ResultCode.ERROR_401.code(),ResultCode.ERROR_401.message()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch (JWTVerificationException e){
			try {
				ResponseUtil.writeResponse(response,ResponseUtil.failure(ResultCode.ERROR_20008.code(),ResultCode.ERROR_20008.message()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}*/
}
