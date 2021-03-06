package com.galaxy.upload.module.service;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;
import com.galaxy.upload.module.model.Video;
import org.springframework.web.multipart.MultipartFile;

public interface UploadVideoService extends Service<Video> {

    Result uploadVideo(MultipartFile multipartFile, String title, String description, String suffix, String level,Integer status,String statusName);

    Result uploadVideoUrl(MultipartFile multipartFile);

    Result fetchFrame(String videofile,Video video);

    Result list(Integer page, Integer size, Video video);

    Result findByModalData(Integer page, Integer size, String title);

    Result delete(Long id);
}
