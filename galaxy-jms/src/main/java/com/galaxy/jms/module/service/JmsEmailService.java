package com.galaxy.jms.module.service;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;
import com.galaxy.jms.module.model.Email;

/**
* Created by CodeGenerator on 2021/01/02.
*/
public interface JmsEmailService extends Service<Email> {

    Result sendQuery(String userEmail,String messageInside,String messageOutside);

    Result sendGenerateUrl(String userEmail);
}
