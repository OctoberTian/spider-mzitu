package com.seepine.mzitu.constant;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;

/**
 * @author Seepine
 * @date 2020-05-17 19:35
 */
public interface CommonConstant {
    /**
     * 已爬取url缓存命名空间
     */
    String ALREADY_CACHE_NAME = "already";
    /**
     * 页面爬取延迟毫秒
     */
    int CRAWL_MILLIS = 500;
    /**
     * 页面爬取线程数
     */
    int CRAWL_THREAD_NUM = 1;
    /**
     * 是否异步下载,true异步，false同步
     */
    boolean IS_ASYNC = false;
    /**
     * 图片下载延迟毫秒
     */
    long DOWNLOAD_MILLIS = 600;
    /**
     * 下载图片线程数(ps: max <= 2*cpuNum)
     */
    int DOWNLOAD_THREAD_NUM = 2;
    /**
     * 爬取Site配置
     */
    Site SITE = Site.me()
            .setDomain("https://www.mzitu.com")
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
            .addHeader("Referer", "https://www.mzitu.com")
            .setRetryTimes(3).setSleepTime(CommonConstant.CRAWL_MILLIS);
    /**
     * 代理池配置
     */
    Proxy[] PROXY_ARRAY = {
            //new Proxy("ip", port)
            //new Proxy("ip", port,"username","password")
    };
    boolean OPEN_AUTO_PROXY = false;
}
