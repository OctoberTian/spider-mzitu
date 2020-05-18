package com.seepine.mzitu.constant;

/**
 * @author Seepine
 * @date 2020-05-17 19:35
 */
public interface CommonConstant {
    /**
     * 等待爬取信息缓存命名空间
     */
    String AWAIT_CACHE_NAME = "await";
    /**
     * 已爬取url缓存命名空间
     */
    String ALREADY_CACHE_NAME = "already";
    /**
     * 图片下载延迟毫秒
     */
    long DOWNLOAD_MILLIS = 1000;
    /**
     * 页面爬取延迟毫秒
     */
    int CRAWL_MILLIS = 5000;
    /**
     * 页面爬取线程数
     */
    int CRAWL_THREAD_NUM = 1;
}
