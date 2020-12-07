package com.galaxy.ucenter.module.vo;

/**
 * @ClassName CaptchaVo
 * @Description //TODO
 * @Author cjm
 * @Date 2020/12/7 21:29
 * @Version 1.0
 **/
public class CaptchaVo {

    private String verifyToken;

    private String data;

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
