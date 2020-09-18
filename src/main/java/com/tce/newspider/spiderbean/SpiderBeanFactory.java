package com.tce.newspider.spiderbean;


import com.geccocrawler.gecco.spider.SpiderBeanContext;
import com.tce.newspider.spiderbean.render.RenderFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;

import java.util.Map;

public class SpiderBeanFactory {
    private static final Log LOG = LogFactory.getLog(SpiderBeanFactory.class);
    private Map<String, Class<? extends SpiderBean>> spiderBeans;
    private Map<String, SpiderBeanContext> spiderBeanContexts;
    //private DownloaderFactory downloaderFactory;
    //private DownloaderAOPFactory downloaderAOPFactory;
    private RenderFactory renderFactory;
    //private PipelineFactory pipelineFactory;
    protected Reflections reflections;


}
