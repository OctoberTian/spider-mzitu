# mzitu
## <妹子图> 美图爬虫/多线程下载/Java/基于WebMagic

写真图片爬取第三弹。
[https://www.mzitu.com/](https://www.mzitu.com/)  
图片版权归原网站/原作者所有，使用时请注意，若本项目对网站造成影响，请联系本人删除。   

## 使用前准备
clone代码到本地

## 使用
直接运行Main即可

## 说明
本项目下载线程默认2线程，页面爬取的延时5秒，图片下载延时1秒

## 成果
 ![image](result.jpg)
 
## 帮助
本项目简易封装了CacheList基于文件IO的本地缓存，可用来缓存爬取的信息、过滤已下载完成的图集，
另参数配置已抽取，可在com.seepine.mzitu.constant.CommonConstant修改
```java
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

```

## 注意
请勿将爬取延时修改过低，容易被封ip，也可以避免给网站造成不必要的压力，影响网站正常运营。

## 免责声明
本项目仅为学习爬虫基础入门，请勿讲此项目用于非法盈利、非法宣称等违法用途。
同时spider-mzitu未对任何组织、团队，书面授权商业使用。如若出现任何商业后果、纠纷，与本人无关，特此声明
````
    Author: huanghs (seepine@163.com)
    Copyright (c) 2020-2020
````