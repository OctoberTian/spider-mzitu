package com.seepine.mzitu.util;

import cn.hutool.core.thread.ThreadUtil;
import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.entity.Album;
import com.seepine.mzitu.entity.Image;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Request;

import java.util.concurrent.*;

/**
 * @author Seepine
 * @date 2020-05-18 12:53
 */
@Slf4j
public class DownloadUtil extends Thread {
    static DownloadUtil downloadUtil;

    ExecutorService threadPool = ThreadUtil.newExecutor(CommonConstant.DOWNLOAD_THREAD_NUM);

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
            threadPool.execute(() -> {
                log.info("thread " + Thread.currentThread().getId() + "启动任务");
                for (Image item : album.getImageList()) {
                    try {
                        String filePath = album.getPath() + item.getFileName();
                        if (!FileUtil.isValidFile(filePath)) {
                            FileUtil.createDir(album.getPath());
                            FileUtil.createFile(filePath);
                            Request request = new Request(item.getImageUrl());
                            request.addHeader("Referer", item.getReferer());
                            HttpUtil.downloadFile(request, filePath);
                            log.info("文件已下载：" + filePath);
                            ThreadUtil.sleep(CommonConstant.DOWNLOAD_MILLIS);
                        } else {
                            log.info("文件已下载：" + filePath);
                        }
                        CacheUtil.getInstance().push(album.getUrl());
                    } catch (Exception e) {
                        log.error("thread " + Thread.currentThread().getId() + ":" + e.getMessage());
                    }
                }
                log.info("thread " + Thread.currentThread().getId() + "结束任务");
            });
        }
    }
}
