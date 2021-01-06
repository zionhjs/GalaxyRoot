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
	public Result imagesDetailData(Integer page, Integer size, String title) {
		System.out.println("分查查询图片错误问题");
		return null;
	}

	@Override
	public Result videoDetailData(Integer page, Integer size, String title) {
		System.out.println("分页查询视频错误问题");
		return null;
	}
}
