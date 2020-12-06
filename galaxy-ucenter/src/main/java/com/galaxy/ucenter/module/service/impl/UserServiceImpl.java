package com.galaxy.ucenter.module.service.impl;

import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.ucenter.module.mapper.UserMapper;
import com.galaxy.ucenter.module.model.User;
import com.galaxy.ucenter.module.service.UserService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result list(Long id) {
        User user = userMapper.selectByPrimaryKey(id);

        return ResultGenerator.genSuccessResult(user);
    }
}
