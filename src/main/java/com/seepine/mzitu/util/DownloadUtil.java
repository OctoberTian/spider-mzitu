package com.seepine.mzitu.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.custom.CacheList;
import com.seepine.mzitu.entity.Album;
import com.seepine.mzitu.entity.Image;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author Seepine
 * @date 2020-05-18 12:53
 */
public class DownloadUtil extends Thread {
    static DownloadUtil downloadUtil;
    /**
     * 带缓存的list
     */
    final List<Album> albumList = new CacheList<>(CommonConstant.AWAIT_CACHE_NAME, Album.class);
    final List<String> alreadyList = new CacheList<>(CommonConstant.ALREADY_CACHE_NAME, String.class);
    final Object runLock;
    boolean isRun;

    public static DownloadUtil getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    public DownloadUtil() {
        runLock = new Object();
        isRun = false;
    }


    @SneakyThrows
    @Override
    public void run() {
        synchronized (runLock) {
            if (isRun) {
                return;
            }
            isRun = true;
        }
        while (isRun) {
            Album album = null;
            synchronized (albumList) {
                if (!albumList.isEmpty()) {
                    album = albumList.get(0);
                } else {
                    synchronized (runLock) {
                        isRun = false;
                    }
                }
            }
            if (album != null) {
                for (Image item : album.getImageList()) {
                    HttpResponse response = HttpUtil.createGet(item.getImageUrl()).header("Referer", item.getReferer()).execute();
                    System.out.println(album.getPath() + item.getFileName());
                    response.writeBody(album.getPath() + item.getFileName());
                    Thread.sleep(1000);
                }
                synchronized (albumList) {
                    albumList.remove(0);
                }
                synchronized (alreadyList) {
                    alreadyList.add(album.getUrl());
                }
            }
        }
    }

    public void put(Album album) {
        if (!albumList.contains(album)) {
            synchronized (albumList) {
                albumList.add(album);
            }
            if (!isRun) {
                this.start();
            }
        }
    }

    public List<String> get() {
        synchronized (alreadyList) {
            return this.alreadyList;
        }
    }
}
