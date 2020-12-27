package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsBlogTagMapper;
import com.galaxy.cms.module.model.BlogTag;
import com.galaxy.cms.module.service.CmsBlogTagService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/27.
*/
@Service
@Transactional
public class CmsBlogTagServiceImpl extends AbstractService<BlogTag> implements CmsBlogTagService {

    @Resource
    private CmsBlogTagMapper cmsBlogTagMapper;

}
