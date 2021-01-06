package com.galaxy.cms.module.feign.fallback;

import com.galaxy.cms.module.feign.RemoteUploadService;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.utils.Logger;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class RemoteUploadServiceFallbackImpl implements RemoteUploadService {

	@Setter
	private Throwable cause;

	@Override
	public Result imagesDetailData(Integer page, Integer size, String title) {
		System.out.println("分查查询图片错误问题");
		Logger.info(this,"分查查询图片错误问题" + cause);
		return null;
	}

	@Override
	public Result videoDetailData(Integer page, Integer size, String title) {
		System.out.println("分页查询视频错误问题");
		Logger.info(this,"分页查询视频错误问题" + cause);
		return null;
	}
}
