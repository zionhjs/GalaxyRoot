package com.galaxy.cms.module.service.impl;

import com.galaxy.cms.module.feign.RemoteUploadService;
import com.galaxy.cms.module.mapper.CmsBlogImagesMapper;
import com.galaxy.cms.module.mapper.CmsBlogMapper;
import com.galaxy.cms.module.mapper.CmsMomentCommentMapper;
import com.galaxy.cms.module.model.Blog;
import com.galaxy.cms.module.model.BlogImages;
import com.galaxy.cms.module.model.BlogTag;
import com.galaxy.cms.module.service.CmsBlogImagesService;
import com.galaxy.cms.module.service.CmsBlogService;
import com.galaxy.cms.module.service.CmsBlogTagService;
import com.galaxy.cms.module.vo.HomeListVo;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.common.core.utils.DigitUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private CmsBlogImagesService cmsBlogImagesService;

    @Resource
    private RemoteUploadService remoteUploadService;

    @Resource
    private CmsBlogTagService cmsBlogTagService;

    @Override
    public Result add(Blog blog) {
        blog.setId(DigitUtil.generatorLongId());
        blog.setCreatedAt(new Date());
        blog.setIsDelete(false);
        save(blog);

        if (blog.getBlogImagesList().size() > 0){
            for (BlogImages d:blog.getBlogImagesList()) {
                d.setBlogId(blog.getId());
                d.setCreatedAt(new Date());
                d.setIsDelete(false);
            }
            cmsBlogImagesService.saveList(blog.getBlogImagesList());
        }

        List<BlogTag> blogTagList = new ArrayList<BlogTag>();
        BlogTag blogTag;
        //添加博客详情
        if (null != blog.getTagName()){
            String[] array = blog.getTagName().split(",");
            for (int i = 0;i < array.length; i++){
                //
                List<String> stringList = cmsBlogTagService.selectAllTag();
                //如果不包含，代表是新增的标签
                if (!stringList.contains(array[i])){
                    blogTag = new BlogTag();
                    blogTag.setIsDelete(false);
                    blogTag.setCreatedAt(new Date());
                    blogTag.setTagName(array[i]);
                    blogTagList.add(blogTag);
                }
            }
        }
        //如果完全一样就不会添加博客标签
        if (blogTagList.size() > 0){
            cmsBlogTagService.save(blogTagList);
        }

        Result result = ResultGenerator.genSuccessResult();
        result.setData(blog);
        return result;
    }

    @Override
    public Result detail(Long id) {

        Blog blog = cmsBlogMapper.detail(id);
        if (null == blog){
            return ResultGenerator.genFailResult(ResultCode.BLOG_DETAIL_ERROR,"博客不存在或者已删除");
        }

        cmsBlogMapper.updateBlogBrowseNum(id);

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
            d.setCommentNum(d.getMomentCommentList().size());
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public Result findByModalOrderByTime(Integer page, Integer size, Blog blog) {
        PageHelper.startPage(page, size);
        blog.setIsDelete(false);
        List<Blog> list = cmsBlogMapper.findByModalOrderByTime(blog);
        for (Blog d:list) {
            d.setBlogImagesList(cmsBlogImagesMapper.selectBlogImagesByBlogId(d.getId()));
            d.setMomentCommentList(cmsMomentCommentMapper.selectMomentCommentByBlogId(d.getId()));
            d.setCommentNum(d.getMomentCommentList().size());
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
        Result imagesResult = remoteUploadService.imagesDetailData(page,size,title);
        homeListVo.setImagesPageInfo(imagesResult.getData());

        //视频分页查询
        Result videoResult = remoteUploadService.imagesDetailData(page,size,title);
        homeListVo.setVideoPageInfo(videoResult.getData());

        return ResultGenerator.genSuccessResult(homeListVo);
    }

    @Override
    public Result updateBlog(Blog blog) {
        Integer rows = cmsBlogMapper.getBlogCountById(blog.getId());
        if (0 == rows){
            return ResultGenerator.genFailResult(ResultCode.BLOG_NOT_EXIST,"博客不存在或者已删除");
        }

        blog.setUpdatedAt(new Date());

        if (blog.getBlogImagesList().size() > 0){
            //批量删除以前的博客图片
            cmsBlogImagesMapper.batchDeleteBlogImages(blog.getId());
            //批量添加现在得博客图片
            for (BlogImages blogImages:blog.getBlogImagesList()){
                blogImages.setBlogId(blog.getId());
            }
            cmsBlogImagesService.batch(blog.getBlogImagesList());
        }

        rows = updateRows(blog);
        if (0 == rows){
            return ResultGenerator.genFailResult(ResultCode.BLOG_UPDATE_ERROR,"更新博客失败，请重新更新");
        }else {

            List<BlogTag> blogTagList = new ArrayList<BlogTag>();
            BlogTag blogTag;
            //添加博客详情
            if (null != blog.getTagName()){
                String[] array = blog.getTagName().split(",");
                for (int i = 0;i < array.length; i++){
                    //
                    List<String> stringList = cmsBlogTagService.selectAllTag();
                    //如果不包含，代表是新增的标签
                    if (!stringList.contains(array[i])){
                        blogTag = new BlogTag();
                        blogTag.setIsDelete(false);
                        blogTag.setCreatedAt(new Date());
                        blogTag.setTagName(array[i]);
                        blogTagList.add(blogTag);
                    }
                }
            }
            //如果完全一样就不会添加博客标签
            if (blogTagList.size() > 0){
                cmsBlogTagService.save(blogTagList);
            }

            Result result=ResultGenerator.genSuccessResult();
            result.setData(blog);
            return result;
        }
    }
}
