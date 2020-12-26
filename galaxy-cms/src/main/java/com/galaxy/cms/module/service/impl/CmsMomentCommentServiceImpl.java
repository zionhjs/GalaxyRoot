package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsMomentCommentMapper;
import com.galaxy.cms.module.model.MomentComment;
import com.galaxy.cms.module.service.CmsMomentCommentService;
import com.galaxy.common.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2020/12/23.
*/
@Service
@Transactional
public class CmsMomentCommentServiceImpl extends AbstractService<MomentComment> implements CmsMomentCommentService {

    @Resource
    private CmsMomentCommentMapper cmsMomentCommentMapper;

}