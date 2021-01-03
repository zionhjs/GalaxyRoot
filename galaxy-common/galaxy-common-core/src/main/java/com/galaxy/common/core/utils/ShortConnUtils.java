package com.galaxy.common.core.utils;

import com.galaxy.common.core.vo.ShortConnResult;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ShortConnUtils
 * @Description //短连接生成公共类
 * @Author cjm
 * @Date 2020/11/9 14:26
 * @Version 1.0
 **/
public class ShortConnUtils {

    /**
     * 短连接生成
     * @param url
     * @return
     */
    public static ShortConnResult createShortConn(String url) {
        RestTemplate restTemplate = new RestTemplate();
        //三维推api接口url地址,该链接需要从https://3w.cn/api.html官网获取最新的api接口地址和最新的key，否则无法调用成功
        String apiUrl = "http://suo.im/api.htm?url={url}&format=json&key={key}&expireDate={expireDate}&domain={domain}";

        Map<String, String> paraMap = new HashMap<String, String>();
        //要缩短的长网址
        paraMap.put("url", url);
        //用户自己的key，扫码登录后可见
        paraMap.put("key", "5fa8d861be96bd0cb29c938d@328e2ddd10343329faa75e7112d3bc54");
        //过期时间，若expireDate为空，默认有效期3个月。若expireDate>=2040-01-01,则永久有效
        paraMap.put("expireDate", "2040-01-01");
        //可选择域名。“0”代表b6i.cn；“1”代表nxw.so。若为空，默认为b6i.cn
        paraMap.put("domain", "0");

        ShortConnResult result = restTemplate.getForObject(apiUrl, ShortConnResult.class, paraMap);

        return result;
        /*if (result != null && "0".equals(result.getCode())) {
            System.out.println(result.getUrl());
            return result;
        } else {
            //此处调用接口失败，请根据自己的业务逻辑进行容错处理
            System.out.println(result.getUrl());
            return result;
        }*/
    }

}
