package com.seepine.mzitu.util;

import cn.hutool.core.thread.ThreadUtil;
import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.entity.Album;
import com.seepine.mzitu.entity.Image;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Request;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Seepine
 * @date 2020-05-18 12:53
 */
@Slf4j
public class DownloadUtil extends Thread {
    static DownloadUtil downloadUtil;

    ExecutorService threadPool = ThreadUtil.newExecutor(CommonConstant.DOWNLOAD_THREAD_NUM);
    AtomicInteger albumCount = new AtomicInteger(0);
    AtomicInteger imageCount = new AtomicInteger(0);

    public static DownloadUtil getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    public DownloadUtil() {
    }

    public void put(Album album) {
        if (album != null) {
            if (CommonConstant.IS_ASYNC) {
                threadPool.execute(() -> {
                    download(album);
                });
            } else {
                download(album);
            }
        }
    }

    public void download(Album album) {
        if (album != null) {
            int count = 0;
            for (Image item : album.getImageList()) {
                try {
                    String filePath = album.getPath() + item.getFileName();
                    if (FileUtil.isNotValidImage(filePath)) {
                        FileUtil.createDir(album.getPath());
                        FileUtil.createFile(filePath);
                        Request request = new Request(item.getImageUrl());
                        request.addHeader("Referer", item.getReferer());
                        HttpClientGeneratorUtil.getInstance().getMyHttpClientDownloader().downloadFile(request, filePath);
                        log.info("文件下载完成：" + filePath);
                        imageCount.getAndIncrement();
                        ThreadUtil.sleep(CommonConstant.DOWNLOAD_MILLIS);
                    } else {
                        log.info("文件已下载：" + filePath);
                    }
                    count++;
                } catch (Exception e) {
                    log.error("thread " + Thread.currentThread().getId() + ":" + e.getMessage());
                }
            }
            if (count == album.getImageList().size()) {
                CacheUtil.getInstance().push(album.getUrl());
                albumCount.getAndIncrement();
            }
            log.info("本次累计爬取图集：" + albumCount.get() + ",累计下载图片：" + imageCount.get());
        }
    }
}
