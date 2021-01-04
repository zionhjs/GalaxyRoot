package com.galaxy.jms.module.mapper;


import com.galaxy.common.core.Mapper.Mapper;
import com.galaxy.jms.module.model.ShortConnResult;

public interface JmsShortConnResultMapper extends Mapper<ShortConnResult> {

    ShortConnResult getByUrl(String url);
}