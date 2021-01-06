package com.galaxy.cms.module.feign.fallback;

import com.galaxy.cms.module.feign.RemoteImagesService;
import com.galaxy.common.core.response.Result;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteImagesServiceFallbackImpl implements RemoteImagesService {

	@Override
	public Result detail(Long id) {
		System.out.println("出错了出错了");
		return null;
	}
}
