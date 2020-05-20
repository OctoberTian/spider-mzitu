package com.seepine.mzitu.util;

import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.custom.CacheList;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Seepine
 * @date 2020-05-18 21:26
 */
@Slf4j
public class CacheUtil {
    static CacheUtil cacheUtil;
    final List<String> alreadyList;

    public static CacheUtil getInstance() {
        if (cacheUtil == null) {
            cacheUtil = new CacheUtil();
        }
        return cacheUtil;
    }

    public CacheUtil() {
        alreadyList = new CacheList<>(CommonConstant.ALREADY_CACHE_NAME, String.class);
    }

    public void push(String url) {
        synchronized (alreadyList) {
            if (!alreadyList.contains(url)) {
                alreadyList.add(url);
                log.info("已全部爬取完成：" + url);
            }
        }
    }

    public List<String> getAll() {
        synchronized (alreadyList) {
            return this.alreadyList;
        }
    }

    public boolean contains(String url) {
        synchronized (alreadyList) {
            return alreadyList.contains(url);
        }
    }

}
