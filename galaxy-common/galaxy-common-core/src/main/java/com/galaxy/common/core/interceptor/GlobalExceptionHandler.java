package com.galaxy.common.core.interceptor;

import com.galaxy.common.core.exception.BusinessException;
import com.galaxy.common.core.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
    public Result handler(Exception e) {
    	if( e instanceof BusinessException){
    		BusinessException businessException = (BusinessException) e;
            if(null != businessException.getMessage()){
            	Result result = new Result();
				result.setMessage("系统异常");
				return result;
			} else {
				Result result = new Result();
				result.setMessage("系统异常");
				return result;
			}
        }else if (e instanceof MethodArgumentNotValidException) {
			//hibernate校验框架
    		BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
			StringBuilder errorMsg = new StringBuilder();
			for (ObjectError error : bindingResult.getAllErrors()) {
				errorMsg.append(error.getDefaultMessage()).append(",");
			}
			errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
			Result result = new Result();
			result.setMessage("系统异常");
			return result;
		} else if (e instanceof MissingServletRequestParameterException) {
			Result result = new Result();
			result.setMessage("系统异常");
			return result;
		}else {
			Result result = new Result();
			result.setMessage("系统异常");
			return result;
		}
	}
 }
