package com.seepine.mzitu.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Seepine
 * @date 2020-05-18 11:08
 */
@Data
public class Album implements Serializable {
    static final long serialVersionUID = 1L;
    /**
     * 图集地址(eg: https://www.mzitu.com/230639)
     */
    private String url;
    /**
     * 图集标题(eg: 性感又高级！眼镜娘秘书顾奈奈透明丝袜令人血脉喷张)
     */
    private String title;
    /**
     * 发布时间(eg :2020-05-17 21:58)
     */
    private String time;
    /**
     * 图集所属分类(eg: 性感妹子)
     */
    private String category;
    /**
     * 保存地址(eg: {user.dir}/images/{category}/{time}_{title}/)
     */
    private String path;
    /**
     * 图集中所有图片信息
     */
    private List<Image> imageList;

    public Album(String url, String title, String time, String category) {
        this.url = url;
        this.title = title;
        this.time = time;
        this.category = category;
        this.path = StrUtil.SLASH + "images" + StrUtil.SLASH + this.category + StrUtil.SLASH + this.time + StrUtil.UNDERLINE + this.title + StrUtil.SLASH;
        this.path = System.getProperty("user.dir") + this.path.replace(":", ".");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Album album = (Album) o;
        return Objects.equals(url, album.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "Album{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", category='" + category + '\'' +
                ", path='" + path + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}
