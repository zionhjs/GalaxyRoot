package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsBlogTypeMapper;
import com.galaxy.cms.module.model.BlogType;
import com.galaxy.cms.module.service.CmsBlogTypeService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2021/05/09.
*/
@Service
@Transactional
public class CmsBlogTypeServiceImpl extends AbstractService<BlogType> implements CmsBlogTypeService {

    @Resource
    private CmsBlogTypeMapper cmsBlogTypeMapper;

}
