package com.galaxy.cms.module.mapper;


import com.galaxy.cms.module.model.BlogImages;
import com.galaxy.common.core.Mapper.Mapper;

import java.util.List;

public interface CmsBlogImagesMapper extends Mapper<BlogImages> {

    List<BlogImages> selectBlogImagesByBlogId(Long blogId);
}