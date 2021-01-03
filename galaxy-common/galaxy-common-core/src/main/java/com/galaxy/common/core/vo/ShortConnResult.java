package com.galaxy.common.core.vo;

import java.util.Date;

/**
 * @ClassName Quartz
 * @Description //TODO
 * @Author cjm
 * @Date 2020/11/5 14:48
 * @Version 1.0
 **/
public class ShortConnResult {

    //处理结果：‘0’代表成功，‘1’代表失败
    private String code;
    //生成的短网址，如果生成失败，则返回原链接
    private String url;
    //异常描述
    private String err;
    //生成时间
    private Date createTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
