package com.galaxy.cms.module.feign.factory;


import com.galaxy.cms.module.feign.RemoteUploadService;
import com.galaxy.cms.module.feign.fallback.RemoteUploadServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteUploadServiceFallbackFactory implements FallbackFactory<RemoteUploadService> {

	@Override
	public RemoteUploadService create(Throwable throwable) {
		RemoteUploadServiceFallbackImpl remoteUserServiceFallback = new RemoteUploadServiceFallbackImpl();
		remoteUserServiceFallback.setCause(throwable);
		return remoteUserServiceFallback;
	}
}
