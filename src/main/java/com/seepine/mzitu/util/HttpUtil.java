package com.seepine.mzitu.util;

import com.seepine.mzitu.constant.CommonConstant;
import com.seepine.mzitu.downloader.MyHttpClientGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientRequestContext;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Seepine
 * @date 2020-05-19 8:24
 */
@Slf4j
public class HttpUtil {
    private static final MyHttpClientGenerator httpClientGenerator = new MyHttpClientGenerator();
    private static final HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
    private static final Site site = CommonConstant.SITE;
    private static final ProxyProvider proxyProvider = SimpleProxyProvider.from(CommonConstant.PROXY_ARRAY);


    private static CloseableHttpClient getHttpClient() {
        return httpClientGenerator.getClient(CommonConstant.SITE);
    }

    public static void downloadFile(Request request, String destFileName) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        Proxy proxy = null;
        try {
            proxyProvider.getProxy(null);
        } catch (Exception ignored) {
        }
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, site, proxy);

        CloseableHttpResponse httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
        HttpEntity entity = httpResponse.getEntity();
        InputStream in = entity.getContent();
        File file = new File(destFileName);
        FileOutputStream fout = new FileOutputStream(file);
        int l;
        byte[] tmp = new byte[1024];
        while ((l = in.read(tmp)) != -1) {
            fout.write(tmp, 0, l);
        }
        fout.flush();
        fout.close();
        in.close();
        EntityUtils.consumeQuietly(httpResponse.getEntity());
    }
}
