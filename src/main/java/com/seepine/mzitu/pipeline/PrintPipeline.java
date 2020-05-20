package com.seepine.mzitu.pipeline;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Seepine
 * @date 2020-05-17 11:50
 */
@Slf4j
public class PrintPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        String stringBuilder =
                    "Album url："+resultItems.getRequest().getUrl() + StrUtil.CRLF
                    + "title：" + resultItems.get("title").toString() + StrUtil.CRLF
                    + "category：" + resultItems.get("category").toString() + StrUtil.CRLF
                    + "time：" + resultItems.get("time").toString() + StrUtil.CRLF
                    + "total：" + resultItems.get("total").toString() + StrUtil.CRLF;
            log.info(stringBuilder);
    }
}
