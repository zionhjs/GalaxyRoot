package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.BlogImages;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;

import java.util.List;


/**
* Created by CodeGenerator on 2020/12/27.
*/
public interface CmsBlogImagesService extends Service<BlogImages> {

    Result batch(List<BlogImages> blogImagesList);
}
