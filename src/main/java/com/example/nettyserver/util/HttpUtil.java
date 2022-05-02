package com.example.nettyserver.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用http请求对象
 */
@Slf4j
public class HttpUtil {

    private final String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
    /**
     * 百度云官网获取的 API Key
     */
    private final String ak = "https://aip.baidubce.com/oauth/2.0/token?";
    /**
     * 度云官网获取的 Secret Key
     */
    private final String sk = "https://aip.baidubce.com/oauth/2.0/token?";

    private RestTemplate restTemplate;
    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Secret Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public <T> T doGet(String url, Map<String, Object> param, Class<T> tClass){
        String result = cn.hutool.http.HttpUtil.get(url, param);
        return convertResponseToClass(result, tClass);
    }

    public <T> T doPost(String url, Map<String, Object> param, Class<T> tClass){
        String result = cn.hutool.http.HttpUtil.post(url, param);
        return convertResponseToClass(result, tClass);
    }

    /**
     * 返回结果转换方法
     * @param res 返回结果字符串
     * @param tClass 转换目标类型
     * @param <T> 转换结果
     * @return
     */
    public <T> T convertResponseToClass(String res, Class<T> tClass){
        String jsonString = JSON.toJSONString(res);

        try {
            T object = JSON.parseObject(jsonString, tClass);
        } catch (Exception e) {
            log.error("class convert is fail : {} ",e.getMessage());
        }
        return null;
    }

}
