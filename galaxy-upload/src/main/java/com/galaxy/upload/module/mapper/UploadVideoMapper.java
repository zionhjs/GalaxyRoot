package com.galaxy.upload.module.mapper;

import com.galaxy.common.core.Mapper.Mapper;
import com.galaxy.upload.module.model.Video;

import java.util.List;

public interface UploadVideoMapper extends Mapper<Video> {

    List<Video> list(Video video);
}
