package com.galaxy.common.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface VerifyIdempotent {
    // redis的key值一部分
    String value();
    // 过期时间
    long expireMillis();
}
