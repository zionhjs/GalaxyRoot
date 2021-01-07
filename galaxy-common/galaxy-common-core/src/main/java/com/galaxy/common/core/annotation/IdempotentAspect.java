package com.galaxy.common.core.annotation;

import com.galaxy.common.core.exception.BusinessException;
import com.galaxy.common.core.utils.KeyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 自定义切点
 */
@Component
@Aspect
@ConditionalOnClass(RedisTemplate.class)
public class IdempotentAspect {

    private static final String KEY_TEMPLATE = "idempotent_%s";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 切点(自定义注解)
     */
    @Pointcut("@annotation(com.galaxy.common.core.annotation.VerifyIdempotent)")
    public void executeIdempotent() {

    }

    /**
     * 切点业务
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("executeIdempotent()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取当前方法信息
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取注解
        VerifyIdempotent idempotent = method.getAnnotation(VerifyIdempotent.class);
        //根据 key前缀 + @Idempotent.value() + 方法签名 + 参数 构建缓存键值
        //请求的参数,将参数所在的数组转换成json
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        String key = String.format(KEY_TEMPLATE, idempotent.value() + "_" + KeyUtil.generate(method, arguments));
        //通过setnx确保只有一个接口能够正常访问
        //调用KeyUtil工具类生成key
        //https://segmentfault.com/a/1190000002870317 -- JedisCommands接口的分析
        //nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
        //expx expx的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒
        // key value nxxx(set规则) expx(取值规则) time(过期时间) 500L 5毫秒 5000L 5秒
        String redisRes = redisTemplate.execute((RedisCallback<String>) conn -> ((JedisCommands) conn.getNativeConnection()).set(key, key, "NX", "PX", idempotent.expireMillis()));
        // Jedis jedis = new Jedis("127.0.0.1",6379);
        // jedis.auth("xuzz");
        // jedis.select(0);
        // String redisRes = jedis.set(key, key,"NX","EX",idempotent.expirMillis());
        if (Objects.equals("OK", redisRes)) {
            return joinPoint.proceed();
        } else {
			throw new BusinessException("您的业务已受理，请勿重复提交");
        }
    }
}
