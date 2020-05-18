package com.seepine.mzitu.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Seepine
 * @date 2020-05-17 11:50
 */
public class PrintPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        System.out.println(resultItems.get("title").toString());
        System.out.println(resultItems.get("time").toString());
        System.out.println(resultItems.get("category").toString());
        String urlFirst = resultItems.get("imageUrl").toString();
        String urlPrefix=urlFirst.substring(0,urlFirst.length()-6);
        System.out.println(urlPrefix);
        for(int i = 1; i<Integer.parseInt(resultItems.get("total").toString()); i++){
            System.out.print(urlPrefix+String.format("%02d",i)+".jpg,");
        }
        System.out.println();
        System.out.println();
    }
}
