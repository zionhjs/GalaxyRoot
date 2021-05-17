package com.galaxy.cms.module.mapper;

import com.galaxy.cms.module.model.BlogTag;
import com.galaxy.common.core.Mapper.Mapper;

import java.util.List;

public interface CmsBlogTagMapper extends Mapper<BlogTag> {

    List<String> selectAllTag();
}