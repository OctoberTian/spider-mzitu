package com.seepine.mzitu.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Seepine
 * @create 2020-05-18 11:09
 */
@Data
@Builder
public class Image implements Serializable {
    static final long serialVersionUID = 1L;
    /**
     * 图片下载地址(eg: https://i3.mmzztt.com/2020/05/08b01.jpg)
     */
    private String imageUrl;
    /**
     * 需要加的请求头(eg: Referer:https://mzitu.com/1213/3)
     */
    private String referer;
    /**
     * 图片保存的文件名(eg: 02.jpg)
     */
    private String fileName;
}
