package com.galaxy.upload.module.service;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;
import com.galaxy.upload.module.model.Images;
import org.springframework.web.multipart.MultipartFile;

public interface UploadImagesService extends Service<Images> {

    Result uploadImages(MultipartFile multipartFile,String title,String description,String suffix,String level);

    Result updateImages(Images images);

    Result delete(Long id);
}
