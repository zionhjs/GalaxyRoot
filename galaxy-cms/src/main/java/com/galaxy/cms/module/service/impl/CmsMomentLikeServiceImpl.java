package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsMomentLikeMapper;
import com.galaxy.cms.module.model.MomentLike;
import com.galaxy.cms.module.service.CmsMomentLikeService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/23.
*/
@Service
@Transactional
public class CmsMomentLikeServiceImpl extends AbstractService<MomentLike> implements CmsMomentLikeService {
    @Resource
    private CmsMomentLikeMapper cmsMomentLikeMapper;

}
