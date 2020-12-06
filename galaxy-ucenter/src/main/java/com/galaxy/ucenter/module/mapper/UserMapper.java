package com.galaxy.ucenter.module.mapper;

import com.galaxy.common.core.Mapper.Mapper;
import com.galaxy.ucenter.module.model.User;

public interface UserMapper extends Mapper<User> {

    User selectUser(Long id);

    User findUserByPhone(String phone);

    User findUserByEmail(String email);
}
