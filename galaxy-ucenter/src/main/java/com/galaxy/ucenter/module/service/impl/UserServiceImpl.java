package com.galaxy.ucenter.module.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.galaxy.common.core.constants.Constant;
import com.galaxy.common.core.response.Result;
import com.galaxy.common.core.response.ResultCode;
import com.galaxy.common.core.response.ResultGenerator;
import com.galaxy.common.core.service.AbstractService;
import com.galaxy.common.core.utils.Logger;
import com.galaxy.common.core.utils.Md5Utils;
import com.galaxy.common.core.utils.RedisUtil;
import com.galaxy.common.core.utils.TokenUtil;
import com.galaxy.common.core.vo.SysUserVo;
import com.galaxy.ucenter.module.mapper.SysMenuMapper;
import com.galaxy.ucenter.module.mapper.UserMapper;
import com.galaxy.ucenter.module.model.User;
import com.galaxy.ucenter.module.service.UserService;
import com.galaxy.ucenter.module.vo.CaptchaVo;
import com.galaxy.ucenter.module.vo.LoginVo;
import com.galaxy.ucenter.module.vo.VerfiyCodeVo;
import com.wf.captcha.GifCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

   /* @Autowired
    private RedisService redisService;*/

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Result list(Long id) {
        User user = userMapper.selectUser(id);

        return ResultGenerator.genSuccessResult(user);
    }

    @Override
    public Result logout(Long userId) {
        SysUserVo sysUserVo = null;
        String token=(String) redisUtil.get(userId+"USERID");
        try {
            redisUtil.get(Constant.REDIS_KEY_LOGIN + token);
            sysUserVo = JSONObject.parseObject((String) redisUtil.get(Constant.REDIS_KEY_LOGIN + token),SysUserVo.class);
            System.out.println(JSONObject.parseObject((String) redisUtil.get(Constant.REDIS_KEY_LOGIN + token),SysUserVo.class));
            //sysUserVo = JSONObject.toJavaObject((JSON) redisUtils.get(Constant.REDIS_KEY_LOGIN + token),SysUserVo.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("存入redis异常");
        }

        redisUtil.delete(userId+"USERID");

        if (sysUserVo != null){
            redisUtil.delete(Constant.REDIS_KEY_LOGIN + token);

            return ResultGenerator.genSuccessResult();
        }

        return ResultGenerator.genFailResult(ResultCode.NOT_LOGIN_EXCEPTION,"用户未登录,请重新登录");
    }

    @Override
    public Result login(LoginVo vo) {

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
            return ResultGenerator.genFailResult(ResultCode.PASSWORD_ERROR,"用户名或密码错误");
        }

        SysUserVo userVo = new SysUserVo();

        List<Object> sysMenuList = new ArrayList<Object>();
        if (null != user.getSysRoleId()){
            sysMenuList = sysMenuMapper.selectMenuByRoleId(user.getSysRoleId());
            userVo = sysMenuMapper.selectRoleById(user.getSysRoleId());
        }

        //创建token
        String token = (String) redisUtil.get(user.getId() + "USERID");

        Boolean loginFlag = false;

        if(StringUtils.isNotBlank(token)){
            //说明已登陆，或直接断网
            redisUtil.delete(Constant.REDIS_KEY_LOGIN + token);
            redisUtil.delete(user.getId()+"USERID");
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
            sysUserVo.setExpireTime(System.currentTimeMillis() + 2505600000L);
            sysUserVo.setChannel(vo.getChannel());
            sysUserVo.setUserName(user.getUserName());
            sysUserVo.setSysMenuList(sysMenuList);
            sysUserVo.setRoleId(userVo.getRoleId());
            sysUserVo.setRoleName(userVo.getRoleName());
            //redisService.put(Constant.REDIS_KEY_LOGIN, token, new RedisModel(su.getId(), System.currentTimeMillis() + magConfig.getExpireTime()), magConfig.getExpireTime());
            redisUtil.setWithExpire(Constant.REDIS_KEY_LOGIN + token, sysUserVo , 2505600000L);
            redisUtil.set(user.getId()+"USERID",token);
        }catch (Exception e){
            e.printStackTrace();
            Logger.info(this,"登录token存入redis产生异常："+e.getMessage());
            throw new RuntimeException("存入redis异常");
        }
        return ResultGenerator.genSuccessResult(sysUserVo);
    }

    @Override
    public Result captcha() {
        GifCaptcha specCaptcha = new GifCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        System.out.print("登录验证码" + verCode);
        String verifyToken = TokenUtil.getToken();
        // 存入redis并设置过期时间为30秒
        redisUtil.setWithExpire(Constant.REDIS_KEY_VERFIY + verifyToken, new VerfiyCodeVo(verCode,System.currentTimeMillis() + Constant.verifyCodeForTempValidTime)  , Constant.verifyCodeForTempValidTime);
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setVerifyToken(verifyToken);
        captchaVo.setData(specCaptcha.toBase64());
        // 将key和base64返回给前端
        return ResultGenerator.genSuccessResult(captchaVo);
    }

    @Override
    public Result add(User user) {

        User userVo = userMapper.findUserByPhone(user.getPhone());
        if (null != userVo){
            return ResultGenerator.genFailResult(ResultCode.PHONE_ERROR,"手机号已存在");
        }

        user.setCreatedAt(new Date());
        user.setStatus(1);
        user.setRegisterTime(new Date());
        user.setPassword(Md5Utils.getMd5(user.getPassword()));
        save(user);
        return ResultGenerator.genSuccessResult(user);
    }

}
