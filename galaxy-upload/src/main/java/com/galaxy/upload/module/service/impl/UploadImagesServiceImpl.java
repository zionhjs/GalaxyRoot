package com.galaxy.upload.module.service.impl;

import com.galaxy.common.core.service.AbstractService;
import com.galaxy.upload.module.mapper.UploadImagesMapper;
import com.galaxy.upload.module.module.Images;
import com.galaxy.upload.module.service.UploadImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadImagesServiceImpl extends AbstractService<Images> implements UploadImagesService {

    @Autowired
    private UploadImagesMapper uploadImagesMapper;

}
