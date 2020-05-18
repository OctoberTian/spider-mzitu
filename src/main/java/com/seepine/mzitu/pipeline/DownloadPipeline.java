package com.seepine.mzitu.pipeline;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.entity.Album;
import com.seepine.mzitu.entity.Image;
import com.seepine.mzitu.util.DownloadUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Seepine
 * @create 2020-05-17 11:32
 */
public class DownloadPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {

        Album album = new Album(resultItems.getRequest().getUrl(),resultItems.get("title").toString(),resultItems.get("time").toString(),resultItems.get("category").toString());


        List<Image> imageList=new ArrayList<Image>();

        String imageUrlFirst = resultItems.get("imageUrl").toString();
        String urlPrefix = imageUrlFirst.substring(0, imageUrlFirst.length() - 6);

        imageList.add(Image.builder()
                .imageUrl(imageUrlFirst)
                .referer(album.getUrl())
                .fileName(String.format("%02d", 1) + ".jpg")
                .build());

        for (int i = 2; i < Integer.parseInt(resultItems.get("total").toString()); i++) {
            imageList.add(Image.builder()
                    .imageUrl(urlPrefix + String.format("%02d", i) + ".jpg")
                    .referer(album.getUrl() + StrUtil.SLASH + i)
                    .fileName(String.format("%02d", i) + ".jpg")
                    .build());
        }
        album.setImageList(imageList);

        DownloadUtil.getInstance().put(album);
    }
}
