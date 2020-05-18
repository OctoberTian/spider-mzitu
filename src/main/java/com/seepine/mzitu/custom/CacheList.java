package com.seepine.mzitu.custom;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.LinkedList;

/**
 * @author Seepine
 * @create 2020-05-18 14:11
 */
public class CacheList<T extends Serializable> extends LinkedList<T> {

    private final static String DIR_PATH = "cache";
    private final static String SUFFIX = ".cache";
    private final static String SLASH = "/";
    private final String cacheFile;
    Class<T> clazz;

    public CacheList(String nameSpace,Class<T> clazz) {
        this.clazz = clazz;
        String cacheFilePath = System.getProperty("user.dir") + SLASH + DIR_PATH + SLASH;
        System.out.println(cacheFilePath);
        cacheFile = cacheFilePath + nameSpace + SUFFIX;
        try {
            getFile(cacheFilePath, cacheFile);
            BufferedReader reader = new BufferedReader(new FileReader(cacheFile));
            String line;
            while ((line = reader.readLine()) != null) {
                this.add(JSON.parseObject(line, clazz));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile(String cacheFilePath, String cacheFile) throws IOException {
        File filePath = new File(cacheFilePath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File file = new File(cacheFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @Override
    public T get(int index) {
        return super.get(index);
    }

    @Override
    public boolean add(T t) {
        //做文件缓存
        String json = JSON.toJSONString(t);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(cacheFile, true));
            writer.write(json);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.add(t);
    }

    @Override
    public T remove(int index) {
        T t = super.remove(index);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(cacheFile));
            for (T value : this) {
                writer.write(JSON.toJSONString(value));
                writer.newLine();
            }
            writer.flush();
            writer.close();
            System.out.println("移除成功");
        }catch (Exception ignored){

        }
        return t;
    }
}
