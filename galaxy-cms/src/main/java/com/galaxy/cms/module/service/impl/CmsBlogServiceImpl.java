package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.mapper.CmsBlogImagesMapper;
import com.galaxy.cms.module.mapper.CmsBlogMapper;
import com.galaxy.cms.module.mapper.CmsMomentCommentMapper;
import com.galaxy.cms.module.model.Blog;
import com.galaxy.cms.module.service.CmsBlogService;
import com.galaxy.cms.module.vo.HomeListVo;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
* Created by CodeGenerator on 2020/12/23.
*/
@Service
@Transactional
public class CmsBlogServiceImpl extends AbstractService<Blog> implements CmsBlogService {

    @Resource
    private CmsBlogMapper cmsBlogMapper;

    @Resource
    private CmsBlogImagesMapper cmsBlogImagesMapper;

    @Resource
    private CmsMomentCommentMapper cmsMomentCommentMapper;

    @Override
    public Result detail(Long id) {

        cmsBlogMapper.updateBlogBrowseNum(id);

        Blog blog = cmsBlogMapper.detail(id);
        blog.setBlogImagesList(cmsBlogImagesMapper.selectBlogImagesByBlogId(id));
        blog.setMomentCommentList(cmsMomentCommentMapper.selectMomentCommentByBlogId(id));
        return ResultGenerator.genSuccessResult(blog);
    }

    @Override
    public Result list(Integer page, Integer size, Blog blog) {
        PageHelper.startPage(page, size);
        blog.setIsDelete(false);
        List<Blog> list = cmsBlogMapper.list(blog);
        for (Blog d:list) {
            d.setBlogImagesList(cmsBlogImagesMapper.selectBlogImagesByBlogId(d.getId()));
            d.setMomentCommentList(cmsMomentCommentMapper.selectMomentCommentByBlogId(d.getId()));
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public Result homeFindByTitle(Integer page, Integer size, String title) {
        PageHelper.startPage(page, size);
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setIsDelete(false);
        List<Blog> list = cmsBlogMapper.list(blog);
        for (Blog d:list) {
            d.setBlogImagesList(cmsBlogImagesMapper.selectBlogImagesByBlogId(d.getId()));
            d.setMomentCommentList(cmsMomentCommentMapper.selectMomentCommentByBlogId(d.getId()));
        }
        //博客分页查询
        PageInfo blogPageInfo = new PageInfo(list);
        HomeListVo homeListVo = new HomeListVo();
        homeListVo.setBlogPageInfo(blogPageInfo);

        //图片分页查询


        //视频分页查询


        return ResultGenerator.genSuccessResult(homeListVo);
    }
}
