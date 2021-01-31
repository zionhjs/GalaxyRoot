package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.ShortConnResult;
import com.galaxy.common.core.service.Service;

/**
* Created by CodeGenerator on 2021/01/04.
*/
public interface JmsShortConnResultService extends Service<ShortConnResult> {

    ShortConnResult getByUrl(String url);
}
