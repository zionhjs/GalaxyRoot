package com.galaxy.ucenter.module.controller;

import com.galaxy.common.core.response.Result;
import com.galaxy.ucenter.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理用户得好友
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list")
    public Result list(@RequestParam Long id) throws Exception{
        return userService.list(id);
    }

}
