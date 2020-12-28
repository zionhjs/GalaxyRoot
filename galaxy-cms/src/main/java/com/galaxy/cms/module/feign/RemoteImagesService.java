package com.galaxy.cms.module.feign;

import com.galaxy.common.core.constants.ServiceNameConstants;
import com.rabbitmq.http.client.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import sun.security.util.SecurityConstants;

@FeignClient(contextId = "remoteUploadService")
public interface RemoteImagesService {

	

}
