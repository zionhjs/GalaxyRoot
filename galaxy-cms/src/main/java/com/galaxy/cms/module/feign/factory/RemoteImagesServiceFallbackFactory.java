package com.galaxy.cms.module.feign.factory;

import com.galaxy.cms.module.feign.RemoteImagesService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteImagesServiceFallbackFactory implements FallbackFactory<RemoteImagesService> {

	@Override
	public RemoteImagesService create(Throwable throwable) {
		return null;
	}
}
