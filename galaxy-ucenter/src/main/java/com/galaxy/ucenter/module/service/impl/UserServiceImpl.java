package com.galaxy.ucenter.module.service.impl;

import com.galaxy.common.core.constants.Constant;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.common.core.service.RedisService;
import com.galaxy.common.core.utils.Logger;
import com.galaxy.common.core.utils.Md5Utils;
import com.galaxy.common.core.utils.TokenUtil;
import com.galaxy.ucenter.module.mapper.UserMapper;
import com.galaxy.ucenter.module.model.User;
import com.galaxy.ucenter.module.service.UserService;
import com.galaxy.ucenter.module.vo.LoginVo;
import com.galaxy.ucenter.module.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result list(Long id) {
        User user = userMapper.selectUser(id);

        return ResultGenerator.genSuccessResult(user);
    }

    @Override
    public Result login(LoginVo vo) {

        readWriteLock.writeLock().lock();

        SysUserVo sysUserVo = new SysUserVo();
        User user = new User();

        //手机号或者邮箱登录
        if (vo.getPhone().contains("@")){
            //邮箱登录
            user = userMapper.findUserByEmail(vo.getPhone());
        }else {
            //手机号登录
            user = userMapper.findUserByPhone(vo.getPhone());
        }

        if (null == user){
            return ResultGenerator.genFailResult(ResultCode.USER_NOT_EXIST,"用户信息不存在[账号可能被停用或删除]");
        }

        if(!Md5Utils.getMd5(vo.getPassword()).equals(user.getPassword())){
            return ResultGenerator.genFailResult(ResultCode.PASSWORD_ERROR,"密码输入错误，请重新输入");
        }

        //创建token
        String token= (String) redisService.get(user.getId() + "USERID");

        Boolean loginFlag = false;

        if(StringUtils.isNotBlank(token)){
            //说明已登陆，或直接断网
            redisService.delete(Constant.REDIS_KEY_LOGIN + token);
            redisService.delete(user.getId()+"USERID");
        }else{
            //true首次
            loginFlag=true;
        }
        token = TokenUtil.getToken();

        try {
            sysUserVo.setUserId(user.getId());
            sysUserVo.setPhone(user.getPhone());
            sysUserVo.setEmail(user.getEmail());
            sysUserVo.setToken(token);
            sysUserVo.setExpireTime(2505600000L);
            sysUserVo.setChannel(vo.getChannel());
            sysUserVo.setUserName(user.getUserName());
            //redisService.put(Constant.REDIS_KEY_LOGIN, token, new RedisModel(su.getId(), System.currentTimeMillis() + magConfig.getExpireTime()), magConfig.getExpireTime());
            redisService.setWithExpire(Constant.REDIS_KEY_LOGIN + token, sysUserVo , 2505600000L);
            redisService.set(user.getId()+"USERID",token);
        }catch (Exception e){
            e.printStackTrace();
            Logger.info(this,"登录token存入redis产生异常："+e.getMessage());
            throw new RuntimeException("存入redis异常");
        }
        return ResultGenerator.genSuccessResult(sysUserVo);
    }
}
