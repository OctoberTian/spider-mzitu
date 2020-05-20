package com.seepine.mzitu.pipeline;

import cn.hutool.core.util.StrUtil;
import com.seepine.mzitu.entity.Album;
import com.seepine.mzitu.entity.Image;
import com.seepine.mzitu.util.CacheUtil;
import com.seepine.mzitu.util.DownloadUtil;
import lombok.SneakyThrows;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seepine
 * @date 2020-05-17 11:32
 */
public class DownloadPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (CacheUtil.getInstance().contains(resultItems.getRequest().getUrl())) {
            return;
        }
        Album album = new Album(resultItems.getRequest().getUrl(), resultItems.get("title").toString(), resultItems.get("time").toString(), resultItems.get("category").toString());

        List<Image> imageList = new ArrayList<>();

        String imageUrlFirst = resultItems.get("imageUrl").toString();
        String urlPrefix = imageUrlFirst.substring(0, imageUrlFirst.length() - 6);

        String[] urlArr = resultItems.getRequest().getUrl().split("/");
        if(urlArr.length==5){
            imageList.add(Image.builder()
                    .imageUrl(imageUrlFirst)
                    .referer(album.getUrl())
                    .fileName(String.format("%02d", Integer.parseInt(urlArr[urlArr.length-1])) + ".jpg")
                    .build());
        }else{
            imageList.add(Image.builder()
                    .imageUrl(imageUrlFirst)
                    .referer(album.getUrl())
                    .fileName(String.format("%02d", 1) + ".jpg")
                    .build());
        }


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
