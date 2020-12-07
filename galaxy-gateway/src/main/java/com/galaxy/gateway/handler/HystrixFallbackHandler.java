package com.galaxy.gateway.handler;

/**
 * @author cjm
 * @date 2020/12/7
 * Hystrix 降级处理
 */
//@Slf4j
//@Component
public class HystrixFallbackHandler{
	/*@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		Optional<Object> originalUris = serverRequest.attribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);

		originalUris.ifPresent(originalUri -> log.error("网关执行请求:{}失败,hystrix服务降级处理", originalUri));

		return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("服务异常"));
	}*/
}
