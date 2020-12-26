package com.galaxy.upload.module.mapper;

import com.galaxy.common.core.Mapper.Mapper;
import com.galaxy.upload.module.model.Images;

import java.util.List;

public interface UploadImagesMapper extends Mapper<Images> {

    Images getImagesDetailsById(Long id);

    List<Images> list(Images images);
}
