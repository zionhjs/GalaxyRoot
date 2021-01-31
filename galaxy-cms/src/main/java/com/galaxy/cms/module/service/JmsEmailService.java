package com.galaxy.cms.module.service;

import com.galaxy.cms.module.model.Email;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;

/**
* Created by CodeGenerator on 2021/01/02.
*/
public interface JmsEmailService extends Service<Email> {

    Result sendQuery(String userEmail, String messageInside, String messageOutside);

    Result sendGenerateUrl(String userEmail);
}
