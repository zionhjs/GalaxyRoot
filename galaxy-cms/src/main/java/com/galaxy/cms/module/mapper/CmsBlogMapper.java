package com.galaxy.cms.module.mapper;

import com.galaxy.cms.module.model.Blog;
import com.galaxy.common.core.Mapper.Mapper;

import java.util.List;

public interface CmsBlogMapper extends Mapper<Blog> {

    Blog detail(Long id);

    void updateBlogBrowseNum(Long id);

    List<Blog> list(Blog blog);

    Integer getBlogCountById(Long id);

    List<Blog> findByModalOrderByTime(Blog blog);
}