package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.BlogTag;
import com.galaxy.common.core.service.Service;

import java.util.List;


/**
* Created by CodeGenerator on 2020/12/27.
*/
public interface CmsBlogTagService extends Service<BlogTag> {

    List<String> selectAllTag();
}
