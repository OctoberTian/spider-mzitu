package com.seepine.mzitu.custom;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.seepine.mzitu.util.FileUtil;

import java.io.*;
import java.util.LinkedList;

/**
 * @author Seepine
 * @date 2020-05-18 14:11
 */
public class CacheList<T extends Serializable> extends LinkedList<T> {

    private final static String DIR_PATH = "cache";
    private final static String SUFFIX = ".cache";
    private final static String SLASH = "/";
    Class<T> clazz;
    BufferedWriter writer = null;

    public CacheList(String nameSpace, Class<T> clazz) {
        this.clazz = clazz;
        String cacheFilePath = System.getProperty("user.dir") + SLASH + DIR_PATH;
        String cacheFile = cacheFilePath + SLASH + nameSpace + SUFFIX;
        try {
            FileUtil.createDir(cacheFilePath);
            FileUtil.createFile(cacheFile);
            BufferedReader reader = new BufferedReader(new FileReader(cacheFile));
            String line;
            while ((line = reader.readLine()) != null) {
                this.add(JSON.parseObject(line, clazz));
            }
            reader.close();
            writer = new BufferedWriter(new FileWriter(cacheFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T get(int index) {
        return super.get(index);
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            return false;
        }
        //做文件缓存
        String json = JSON.toJSONString(t);
        if (StrUtil.isBlank(json)) {
            return false;
        }
        try {
            if (writer != null) {
                writer.write(json);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.add(t);
    }
}
