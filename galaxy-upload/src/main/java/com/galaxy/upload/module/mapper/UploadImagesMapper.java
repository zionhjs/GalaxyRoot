package com.galaxy.upload.module.mapper;

import com.galaxy.common.core.Mapper.Mapper;
import com.galaxy.upload.module.model.Images;

public interface UploadImagesMapper extends Mapper<Images> {

    Images getImagesDetailsById(Long id);
}
