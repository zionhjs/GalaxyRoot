package com.galaxy.cms.module.feign;

import com.galaxy.cms.module.feign.factory.RemoteUploadServiceFallbackFactory;
import com.galaxy.common.core.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用feign调用上传服务
 */
@FeignClient(name = "galaxy-upload",contextId = "remoteUploadService",fallbackFactory = RemoteUploadServiceFallbackFactory.class)
public interface RemoteUploadService {

    /**
     * 分页查询图片
     * @param page
     * @param size
     * @param title
     * @return
     */
    @RequestMapping(value = "/images/findByModalData", method = RequestMethod.POST)
    Result imagesDetailData(@RequestParam(defaultValue="1",required=false,value = "page") Integer page,
                      @RequestParam(defaultValue="20",required=false,value = "size") Integer size,
                      @RequestParam(required = false,value = "title") String title);

    /**
     * 分页查询视频
     * @param page
     * @param size
     * @param title
     * @return
     */
    @RequestMapping(value = "/video/findByModalData", method = RequestMethod.POST)
    Result videoDetailData(@RequestParam(defaultValue="1",required=false,value = "page") Integer page,
                      @RequestParam(defaultValue="20",required=false,value = "size") Integer size,
                      @RequestParam(required = false,value = "title") String title);

}
