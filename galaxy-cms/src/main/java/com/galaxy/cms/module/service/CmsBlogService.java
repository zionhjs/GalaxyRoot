package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.Blog;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;

/**
* Created by CodeGenerator on 2020/12/23.
*/
public interface CmsBlogService extends Service<Blog> {

    Result detail(Long id);

    Result list(Integer page, Integer size, Blog blog);
}
