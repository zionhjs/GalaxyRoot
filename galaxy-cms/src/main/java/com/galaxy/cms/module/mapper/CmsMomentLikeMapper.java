package com.galaxy.cms.module.mapper;

import com.galaxy.cms.module.model.MomentLike;
import com.galaxy.common.core.Mapper.Mapper;
import org.apache.ibatis.annotations.Param;

public interface CmsMomentLikeMapper extends Mapper<MomentLike> {

    Integer findMomentLikeById(MomentLike momentLike);

    Integer updateBlogMomentLikeById(@Param("subjectId") Long subjectId,@Param("num") int num);

    Integer updateMomentCommentLikeById(@Param("subjectId") Long subjectId,@Param("num") int num);

    MomentLike selectByIdAndIsDelete(@Param("subjectId") Long subjectId,@Param("createBy") String createBy);

    Integer updateLike(MomentLike momentLike);
}