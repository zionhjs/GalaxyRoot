package com.galaxy.ucenter.module.service;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;
import com.galaxy.ucenter.module.model.User;

public interface UserService extends Service<User> {

    Result list(Long id);
}
