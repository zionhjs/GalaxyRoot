package com.galaxy.upload.module.service;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;
import com.galaxy.upload.module.model.Video;
import org.springframework.web.multipart.MultipartFile;

public interface UploadVideoService extends Service<Video> {

    Result uploadVideo(MultipartFile multipartFile, String title, String description, String suffix, String level);

    Result uploadVideoUrl(MultipartFile multipartFile);

    Result fetchFrame(String videofile,Video video);
}
