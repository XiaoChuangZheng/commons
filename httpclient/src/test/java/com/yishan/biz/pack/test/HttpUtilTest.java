package com.yishan.biz.pack.test;

import com.yishan.biz.pack.httpclient.HttpUtil;
import com.yishan.biz.pack.httpclient.domain.HttpResult;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import constants.MethodEnum;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 * <p>
 * 创建日期 2018年04月24日}
 *
 * @author zhengxiaochuang(zhengxiaochuang @ eefung.com)
 */
public class HttpUtilTest {
    @Test
    public void testGet() throws IOException {
        String url = "http://localhost:11111/test";
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id","111");
        objectObjectHashMap.put("ss","222");
        HttpResult execute = HttpUtil.execute(url, new HashMap<>(), objectObjectHashMap, MethodEnum.GET);
        System.out.println(execute.getResult());
    }

    @Test
    public void testPost() throws IOException {
        String url = "http://localhost:11111/test";
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id","111");
        HttpResult execute = HttpUtil.execute(url, new HashMap<>(), objectObjectHashMap, MethodEnum.POST);
        System.out.println(execute.getResult());
    }

    @Test
    public void testPut() throws IOException {
        String url = "http://localhost:11111/test";
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id","111");
        HttpResult execute = HttpUtil.execute(url, new HashMap<>(), objectObjectHashMap, MethodEnum.PUT);
        System.out.println(execute.getResult());
    }

    @Test
    public void testDelete() throws IOException {
        String url = "http://localhost:11111/test";
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id","111");
        HttpResult execute = HttpUtil.execute(url, new HashMap<>(), objectObjectHashMap, MethodEnum.DELETE);
        System.out.println(execute.getResult());
    }

}
