package com.seepine.mzitu.util;

import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.custom.CacheList;

import java.util.List;

/**
 * @author Seepine
 * @date 2020-05-18 21:26
 */
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
            alreadyList.add(url);
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
