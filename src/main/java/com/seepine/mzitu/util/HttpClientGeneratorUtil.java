package com.seepine.mzitu.util;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.downloader.MyHttpClientDownloader;
import com.seepine.mzitu.entity.ProxyData;
import com.seepine.mzitu.entity.R;
import org.jsoup.select.Collector;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Seepine
 */
public class HttpClientGeneratorUtil {
    private static HttpClientGeneratorUtil httpClientGeneratorUtil;
    MyHttpClientDownloader myHttpClientDownloader;
    ScheduledExecutorService service;
    //替换成http://www.zhilianhttp.com/Index/getapi.html生成的api链接，json格式
    private static String API_URL = "";

    private HttpClientGeneratorUtil() {
        myHttpClientDownloader = new MyHttpClientDownloader();
        if (CommonConstant.OPEN_AUTO_PROXY && !API_URL.equals("")) {
            service = Executors.newSingleThreadScheduledExecutor();
            this.getProxy();
            service.schedule(this::getProxy, 59, TimeUnit.SECONDS);
        } else {
            myHttpClientDownloader.setProxyProvider(SimpleProxyProvider.from(CommonConstant.PROXY_ARRAY));
        }
    }

    public void getProxy() {
        String json = HttpUtil.get(API_URL);
        R res = JSONUtil.toBean(json, R.class);
        System.out.println(res);
        if (res.getCode() == 0) {
            List<ProxyData> resList = JSONUtil.toList(JSONUtil.parseArray(res.getData().toString()), ProxyData.class);
            System.out.println("刷新了代理池");
            System.out.println(resList);
            Proxy[] proxyArr = new Proxy[resList.size()];
            resList.stream().map(item -> {
                String[] arr = item.getIP().split(":");
                return new Proxy(arr[0], Integer.parseInt(arr[1]));
            }).collect(Collectors.toList()).toArray(proxyArr);
            myHttpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxyArr));
        } else {
            this.getProxy();
        }
    }

    public static synchronized HttpClientGeneratorUtil getInstance() {
        if (httpClientGeneratorUtil == null) {
            httpClientGeneratorUtil = new HttpClientGeneratorUtil();

        }
        return httpClientGeneratorUtil;
    }

    public MyHttpClientDownloader getMyHttpClientDownloader() {
        return this.myHttpClientDownloader;
    }
}
