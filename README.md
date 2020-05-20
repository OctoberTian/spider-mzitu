# mzitu
## <妹子图> 美图爬虫/多线程下载/Java/基于WebMagic

写真图片爬取第三弹。
[https://www.mzitu.com/](https://www.mzitu.com/)  
图片版权归原网站/原作者所有，使用时请注意，若本项目对网站造成影响，请联系本人删除。   
## 更新日志
### 2020.5.20
1. 增加对老图集url规则的解析
2. 优化对图片文件有效性的判断

### 2020.5.19
1. 修改图片下载为线程池，判断已下载且有效图片不重复下载
2. 增加线程池配置，仿造httpClient让下载图片也支持代理池
3. 修复输入流写入文件不完整

### 2020.5.18
1. 完成爬取功能，抽取参数为CommonConstant
2. 优化爬取流程，增加分类、时间等信息爬取，并以此优化保存路径

## 使用前准备
clone代码到本地

## 使用
安装依赖后运行Main即可

## 说明
本项目爬取线程数1，爬取延时5秒；下载图片线程数1，下载延时1秒。

## 成果
爬取结果展示在项目目录下，并会按照分类划分，图集以文件名开头方便排序。
![](https://pic.downk.cc/item/5ec37351c2a9a83be5b09e48.png)
 
## 帮助
本项目简易封装了CacheList基于文件IO的本地缓存，可用来缓存爬取的信息、过滤已下载完成的图集，
另参数配置已抽取，可在com.seepine.mzitu.constant.CommonConstant修改
```java
public interface CommonConstant {
    /**
     * 已爬取url缓存命名空间
     */
    String ALREADY_CACHE_NAME = "already";
    /**
     * 页面爬取延迟毫秒
     */
    int CRAWL_MILLIS = 5000;
    /**
     * 图片下载延迟毫秒
     */
    long DOWNLOAD_MILLIS = 1000;
    /**
     * 页面爬取线程数
     */
    int CRAWL_THREAD_NUM = 1;
    /**
     * 下载图片线程数(ps: max <= 2*cpuNum)
     */
    int DOWNLOAD_THREAD_NUM = 1;
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