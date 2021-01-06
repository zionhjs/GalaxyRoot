package com.galaxy.cms.module.feign;

import com.galaxy.common.core.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "galaxy-upload")
public interface RemoteImagesService {

    @RequestMapping(value = "/images/detail", method = RequestMethod.GET)
    Result detail(@RequestParam("id") Long id);

}
