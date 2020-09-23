package com.tce.newspider.spiderbean;




import com.tce.newspider.downloader.DownloaderFactory;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.spiderbean.utils.UrlMatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpiderBeanFactory {
    private static final Log LOG = LogFactory.getLog(SpiderBeanFactory.class);
    private DownloaderFactory downloaderFactory;
    /**
     * 匹配的SpriderBean matchUrl:SpiderBean
     */
    private Map<String, Class<? extends SpiderBean>> spiderBeans;

    public SpiderBeanFactory(){
        this.spiderBeans = new ConcurrentHashMap<String, Class<? extends SpiderBean>>();
    }
//
//    public SpiderBeanFactory(String classPath) {
//        this(classPath, null);
//    }
//
//    public SpiderBeanFactory(String classPath, PipelineFactory pipelineFactory) {
//        if (StringUtils.isNotEmpty(classPath)) {
//            reflections = new Reflections(
//                    ConfigurationBuilder.build("com.geccocrawler.gecco", classPath, GeccoClassLoader.get())
//                            .setMetadataAdapter(new GeccoJavaReflectionAdapter())
//                            .setExpandSuperTypes(false));
//            // reflections = new Reflections("com.geccocrawler.gecco", classPath);
//        } else {
//            reflections = new Reflections(ConfigurationBuilder.build("com.geccocrawler.gecco", GeccoClassLoader.get())
//                    .setMetadataAdapter(new GeccoJavaReflectionAdapter())
//                    .setExpandSuperTypes(false));
//            // reflections = new Reflections("com.geccocrawler.gecco");
//        }
//        dynamic();
//
//        this.downloaderFactory = new MonitorDownloaderFactory(reflections);
//        this.downloaderAOPFactory = new DownloaderAOPFactory(reflections);
//        this.renderFactory = new MonitorRenderFactory(reflections);
//        if (pipelineFactory != null) {
//            this.pipelineFactory = pipelineFactory;
//        } else {
//            this.pipelineFactory = new DefaultPipelineFactory(reflections);
//        }
//        this.spiderBeans = new ConcurrentHashMap<String, Class<? extends SpiderBean>>();
//        this.spiderBeanContexts = new ConcurrentHashMap<String, SpiderBeanContext>();
//        loadSpiderBean(reflections);
//    }


    public DownloaderFactory getDownloaderFactory() {
        return downloaderFactory;
    }

    /**
     * 匹配SpiderBean
     * @param request
     * @return
     */
    public Class<? extends SpiderBean> matchSpider(HttpRequest request) {
        String url = request.getUrl();
        Class<? extends SpiderBean> commonSpider = null;// 通用爬虫
        for (Map.Entry<String, Class<? extends SpiderBean>> entrys : spiderBeans.entrySet()) {
            Class<? extends SpiderBean> spider = entrys.getValue();
            String urlPattern = entrys.getKey();
            Map<String, String> params = UrlMatcher.match(url, urlPattern);
            if (params != null) {
                request.setParameters(params);
                return spider;
            } else {
                if (urlPattern.equals("*")) {
                    commonSpider = spider;
                }
            }
        }
        if (commonSpider != null) {// 如果包含通用爬虫，返回通用爬虫
            return commonSpider;
        }
        return null;
    }
}
