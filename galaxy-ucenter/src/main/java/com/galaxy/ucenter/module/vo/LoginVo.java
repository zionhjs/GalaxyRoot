package com.galaxy.ucenter.module.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @ClassName LoginVo
 * @Description //TODO
 * @Author cjm
 * @Date 2020/12/6
 * @Version 1.0
 **/
public class LoginVo {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    @NotNull(message = "手机号或者邮箱不可为空")
    private String phone;

    @NotNull(message = "密码不可为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull(message = "验证码key不可为空")
    @ApiModelProperty(value = "验证码key")
    private String verifyKey;

    @NotNull(message = "登录验证码不可为空")
    @ApiModelProperty(value = "登录验证码")
    private String verifyCode;

    @NotNull(message = "渠道号不可为空")
    @ApiModelProperty(value = "1代表用户登录2代表后台用户登录")
    private String channel;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
