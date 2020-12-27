package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsMomentLikeMapper;
import com.galaxy.cms.module.model.MomentLike;
import com.galaxy.cms.module.service.CmsMomentLikeService;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
* Created by CodeGenerator on 2020/12/23.
*/
@Service
@Transactional
public class CmsMomentLikeServiceImpl extends AbstractService<MomentLike> implements CmsMomentLikeService {
    @Resource
    private CmsMomentLikeMapper cmsMomentLikeMapper;

    @Override
    public Result add(MomentLike momentLike) {

        //预防重复点赞
        Integer rows = cmsMomentLikeMapper.findMomentLikeById(momentLike);
        if (rows > 0) {
            return ResultGenerator.genFailResult(ResultCode.MOMENT_LIKE_ERROR,"不可重复点赞!");
        }

        if (1 == momentLike.getType()){
            //给博客点赞
            rows = cmsMomentLikeMapper.updateBlogMomentLikeById(momentLike.getSubjectId(), 1);
            if (0 == rows) {
                return ResultGenerator.genFailResult(ResultCode.BLOG_LIKE_ERROR,"为博客点赞失败，请重新点赞!");
            }

        }else if (2 == momentLike.getType()){
            //给评论点赞
            rows = cmsMomentLikeMapper.updateMomentCommentLikeById(momentLike.getSubjectId(), 1);
            if (0 == rows) {
                return ResultGenerator.genFailResult(ResultCode.COMMENT_MOMENT_LIKE_ERROR,"为评论点赞失败，请重新点赞!");
            }
        }

        if (rows > 0){
            momentLike.setCreateTime(new Date());
            momentLike.setIsDelete(false);
            rows = cmsMomentLikeMapper.insert(momentLike);
            if (rows > 0){
                return ResultGenerator.genSuccessResult();
            }else {
                return ResultGenerator.genFailResult(ResultCode.LIKE_DETAILS_ERROR,"增加点赞详情失败!");
            }
        }else {
            return ResultGenerator.genFailResult(ResultCode.LIKE_ERROR,"点赞失败!");
        }
    }

    @Override
    public Result updateLike(MomentLike momentLike) {

        MomentLike userMomentLike = cmsMomentLikeMapper.selectByIdAndIsDelete(momentLike.getSubjectId(),momentLike.getCreateBy());
        if (null == userMomentLike){
            return ResultGenerator.genFailResult(ResultCode.LIKE_ERROR_ERROR,"点赞记录不存在，或者已删除!");
        }

        //删除点赞详情
        Integer rows = cmsMomentLikeMapper.updateLike(momentLike);
        if (rows > 0){
            if (1 == momentLike.getType()){
                //给博客取消点赞数量
                rows = cmsMomentLikeMapper.updateBlogMomentLikeById(userMomentLike.getSubjectId(), -1);
                if (0 == rows) {
                    return ResultGenerator.genFailResult(ResultCode.CANCEL_BLOG_LIKE_ERROR,"为博客取消点赞失败，请重新点赞!");
                }
            }else if (2 == momentLike.getType()){
                //给评论取消点赞数量
                rows = cmsMomentLikeMapper.updateMomentCommentLikeById(userMomentLike.getSubjectId(), -1);
                if (0 == rows) {
                    return ResultGenerator.genFailResult(ResultCode.CANCEL_COMMENT_LIKE_ERROR,"为评论取消点赞失败，请重新取消点赞!");
                }
            }
        }else {
            return ResultGenerator.genFailResult(ResultCode.CANCEL_LIKE_ERROR,"取消点赞失败!");
        }
        return ResultGenerator.genSuccessResult();
    }
}
