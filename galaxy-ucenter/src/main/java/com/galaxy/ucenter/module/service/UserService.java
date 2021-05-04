package com.galaxy.ucenter.module.service;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.service.Service;
import com.galaxy.ucenter.module.model.User;
import com.galaxy.ucenter.module.vo.LoginVo;

public interface UserService extends Service<User> {

    Result list(Long id);

    Result login(LoginVo vo);

    Result logout(Long userId);

    Result captcha();

    Result add(User user);
}

