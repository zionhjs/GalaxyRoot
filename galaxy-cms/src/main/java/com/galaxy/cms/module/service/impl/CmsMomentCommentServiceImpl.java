package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsBlogMapper;
import com.galaxy.cms.module.mapper.CmsMomentCommentMapper;
import com.galaxy.cms.module.model.MomentComment;
import com.galaxy.cms.module.service.CmsMomentCommentService;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
* Created by CodeGenerator on 2020/12/23.
*/
@Service
@Transactional
public class CmsMomentCommentServiceImpl extends AbstractService<MomentComment> implements CmsMomentCommentService {

    @Resource
    private CmsMomentCommentMapper cmsMomentCommentMapper;

    @Resource
    private CmsBlogMapper cmsBlogMapper;

    @Override
    public Result add(MomentComment momentComment) {

        Integer rows = cmsBlogMapper.getBlogCountById(momentComment.getMomentId());
        if (0 == rows){
            return ResultGenerator.genFailResult(ResultCode.BLOG_NOT_EXIST,"博客不存在或者已删除");
        }

        momentComment.setCreatedAt(new Date());
        momentComment.setIsDelete(false);
        rows = saveRows(momentComment);
        if (0 == rows){
            return ResultGenerator.genFailResult(ResultCode.COMMENT_SAVE_ERROR,"新增评论失败，请重新添加");
        }else {
            Result result= ResultGenerator.genSuccessResult();
            result.setData(momentComment);
            return result;
        }
    }

    @Override
    public Result detail(Long id) {
        MomentComment momentComment = cmsMomentCommentMapper.detail(id);
        return ResultGenerator.genSuccessResult(momentComment);
    }

    @Override
    public Result list(Integer page,Integer size,MomentComment momentComment) {
        PageHelper.startPage(page, size);
        momentComment.setIsDelete(false);
        List<MomentComment> list = cmsMomentCommentMapper.list(momentComment);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
