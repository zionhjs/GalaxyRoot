package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsBlogMapper;
import com.galaxy.cms.module.model.Blog;
import com.galaxy.cms.module.service.CmsBlogService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/23.
*/
@Service
@Transactional
public class CmsBlogServiceImpl extends AbstractService<Blog> implements CmsBlogService {

    @Resource
    private CmsBlogMapper cmsBlogMapper;

}
