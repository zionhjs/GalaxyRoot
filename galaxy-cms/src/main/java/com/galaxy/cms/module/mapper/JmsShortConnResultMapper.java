package com.galaxy.cms.module.mapper;


import com.galaxy.cms.module.model.ShortConnResult;
import com.galaxy.common.core.Mapper.Mapper;

public interface JmsShortConnResultMapper extends Mapper<ShortConnResult> {

    ShortConnResult getByUrl(String url);
}