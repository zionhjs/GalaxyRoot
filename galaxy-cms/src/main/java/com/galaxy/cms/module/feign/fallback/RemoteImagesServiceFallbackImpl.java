package com.galaxy.cms.module.feign.fallback;

import com.galaxy.cms.module.feign.RemoteImagesService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteImagesServiceFallbackImpl implements RemoteImagesService {

	@Setter
	private Throwable cause;

}
