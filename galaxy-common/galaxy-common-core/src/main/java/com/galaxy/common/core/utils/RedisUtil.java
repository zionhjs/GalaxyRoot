package com.galaxy.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis基本操作
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长 1 天，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    /**
     * 设置key并以秒为单位计算key过期时间
     * @param key
     * @param value
     * @param expire
     */
    public void setWithExpire(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

	/**
	 * 设置key并以秒为单位计算key过期时间
	 * @param key
	 * @param value
	 * @param expire
	 */
    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, NOT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz) {
        String value = valueOperations.get(key);
        return value == null ? null : fromJson(value, clazz);
    }

    public Object get(String key) {
        return valueOperations.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置Hash的属性
     * @param key
     * @param hashKey
     * @param value
     */
    public void hset(String key, String hashKey, String value){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(hashKey)){
            return;
        }
        hashOperations.put(key, hashKey, value);
    }

    /**
     * 批量设置Hash的属性
     * @param key
     * @param
     */
    /*public void hmset(String key, Map<String, Object> map){
        if(StringUtils.isBlank(key) || MapUtil.isEmpty(map)){
            return;
        }
        hashOperations.putAll(key, map);
    }*/

    public String hget(String key, String hashKey) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)){
            return null;
        }
        return (String) hashOperations.get(key, hashKey);
    }

    public <T> T hget(String key, String hashKey, Class<T> clazz) {
        String value = hget(key, hashKey);

        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSONObject.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSONObject.parseObject(json, clazz);
    }
}
