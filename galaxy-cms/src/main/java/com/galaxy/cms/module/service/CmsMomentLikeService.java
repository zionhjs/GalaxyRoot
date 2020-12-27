package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.MomentLike;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;


/**
* Created by CodeGenerator on 2020/12/23.
*/
public interface CmsMomentLikeService extends Service<MomentLike> {

    Result add(MomentLike momentLike);

    Result updateLike(MomentLike momentLike);
}
