package com.seepine.mzitu;

import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.downloader.MyHttpClientDownloader;
import com.seepine.mzitu.pipeline.DownloadPipeline;
import com.seepine.mzitu.pipeline.PrintPipeline;
import com.seepine.mzitu.processor.CrawlPageProcessor;
import com.seepine.mzitu.util.HttpClientGeneratorUtil;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * @author Seepine
 * @date 2020-05-15 15:27
 */
public class Main {


    public static void main(String[] args) {


        Spider spider = Spider.create(new CrawlPageProcessor())
                .addPipeline(new PrintPipeline())
                .addPipeline(new DownloadPipeline())
                .setDownloader(HttpClientGeneratorUtil.getInstance().getMyHttpClientDownloader())
                .addUrl("https://www.mzitu.com/")
                .thread(CommonConstant.CRAWL_THREAD_NUM);
        spider.start();
    }


}
