package com.seepine.mzitu.processor;

import cn.hutool.core.util.StrUtil;
import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Seepine
 * @date 2020-05-18 17:29
 */
@Slf4j
public class CrawlPageProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        //这是图集的url时
        if (page.getUrl().regex("https://www\\.mzitu\\.com/\\d+").match()) {
            //获取图集有多少图片
            List<String> list = page.getHtml().xpath("//div[@class='pagenavi']/a/span/text()").all();
            if (list != null && list.size() > 2) {
                String imageUrl = page.getHtml().xpath("//div[@class='main-image']//a/img/@src").toString();
                int pageTotal = Integer.parseInt(list.get(list.size() - 2));
                //当图片没规律的,只需要爬取本页的图片即可
                if(!imageUrl.endsWith("01.jpg")){
                    //当是第一页，将后续几页添加爬取
                    if(!page.getUrl().regex("https://www\\.mzitu\\.com/\\d+/\\d+").match()){
                        List<String> targetListUrl=new ArrayList<>();
                        for(int i=2;i<=pageTotal;i++){
                            targetListUrl.add(page.getUrl().toString()+"/"+i);
                        }
                        page.addTargetRequests(targetListUrl);
                    }
                    pageTotal=1;
                }
                String title = page.getHtml().xpath("//h2[@class='main-title']/text()").toString();
                if(StrUtil.isBlank(title)){
                    page.setSkip(true);
                }
                if (page.getUrl().regex("https://www\\.mzitu\\.com/\\d+/\\d+").match()){
                    title = title.substring(0,title.lastIndexOf("（"));
                }
                page.putField("total", pageTotal);
                page.putField("title", title);
                page.putField("imageUrl", imageUrl);
                page.putField("time", page.getHtml().xpath("//div[@class='main-meta']/span[2]/text()").toString().substring(4));
                page.putField("category", page.getHtml().xpath("//div[@class='main-meta']//a/text()").toString());
            }
            //获取错误时，跳过
            else{
                page.setSkip(true);
            }
        }
        //不是图集url时，跳过
        else{
            page.setSkip(true);
        }
        //可能需要爬取
        HashSet<String> set = new HashSet<>();
        set.addAll(page.getHtml().links().regex("https://www\\.mzitu\\.com/\\d+").all());
        set.addAll(page.getHtml().links().regex("https://www\\.mzitu\\.com/page/\\d+/").all());
        //已爬取的
        List<String> alreadySet = CacheUtil.getInstance().getAll();
        //还未爬取的
        set.removeAll(alreadySet);
        page.addTargetRequests(new ArrayList<>(set));
    }

    @Override
    public Site getSite() {
        return CommonConstant.SITE;
    }

}
