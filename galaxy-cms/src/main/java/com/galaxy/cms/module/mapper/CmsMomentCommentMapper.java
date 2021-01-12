package com.galaxy.cms.module.mapper;


import com.galaxy.cms.module.model.MomentComment;
import com.galaxy.common.core.Mapper.Mapper;

import java.util.List;

public interface CmsMomentCommentMapper extends Mapper<MomentComment> {

    List<MomentComment> selectMomentCommentByBlogId(Long momentId);

    MomentComment detail(Long id);

    List<MomentComment> list(MomentComment momentComment);
}