package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.MomentComment;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;


/**
* Created by CodeGenerator on 2020/12/23.
*/
public interface CmsMomentCommentService extends Service<MomentComment> {

    Result add(MomentComment momentComment);

    Result detail(Long id);

    Result list(Integer page,Integer size,MomentComment momentComment);
}
