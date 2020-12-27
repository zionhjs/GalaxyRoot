package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsBlogImagesMapper;
import com.galaxy.cms.module.model.BlogImages;
import com.galaxy.cms.module.service.CmsBlogImagesService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/27.
*/
@Service
@Transactional
public class CmsBlogImagesServiceImpl extends AbstractService<BlogImages> implements CmsBlogImagesService {
    @Resource
    private CmsBlogImagesMapper cmsBlogImagesMapper;

}
