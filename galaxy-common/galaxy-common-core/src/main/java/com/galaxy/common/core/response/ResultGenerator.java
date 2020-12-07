package com.galaxy.common.core.response;

import com.alibaba.fastjson.JSONObject;
import com.galaxy.common.core.utils.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static Result genFailResult(ResultCode code,String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
    }

    /**
     * 直接write方式返回
     * @param response
     * @param result
     * @throws Exception
     */
    public static void writeResponse(HttpServletResponse response, Result result) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
            //Logger.info(this , e.printStackTrace());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
