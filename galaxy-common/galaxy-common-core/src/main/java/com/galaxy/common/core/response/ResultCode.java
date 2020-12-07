package com.galaxy.common.core.response;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500),//服务器内部错误

    OUT_TIME_TOKEN(800012),//token 失效
    NOT_EXIST_TOKEN_EXCEPTION(800011),//token不存在(请重新登录再访问)
    PARAM_ERROR(800010),//手机号或者邮箱必须输入一个
    FILE_DOWNLOAD_ERROR(800009),//文件下载失败
    FILE_BULL_ERROR(800008),//文件不能为空
    FILEUPLOAD_ERROR(800007),//上传文件失败
    USER_LOGIN_CHANNEL_ERROR(800006),//登录渠道不存在，请重新登录
    NOT_LOGIN_EXCEPTION(800005),//用户未登录,请重新登录
    VERFIY_CODE_TIME_ERROR(800004),//验证码已经过期
    VERFIY_CODE_ERROR(800003),//验证码输入错误
    VERFIY_TOKEN_ERROR(800002),//图形验证码token不存在
    PASSWORD_ERROR(800001),//密码输入错误，请重新输入
    USER_NOT_EXIST(800000)//用户信息不存在[账号可能被停用或删除]
    ;

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
