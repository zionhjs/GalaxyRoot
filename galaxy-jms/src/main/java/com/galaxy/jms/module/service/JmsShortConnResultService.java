package com.galaxy.jms.module.service;

import com.galaxy.common.core.service.Service;
import com.galaxy.jms.module.model.ShortConnResult;

/**
* Created by CodeGenerator on 2021/01/04.
*/
public interface JmsShortConnResultService extends Service<ShortConnResult> {

    ShortConnResult getByUrl(String url);
}
