package com.tce.newspider.spiderbean;



import com.tce.newspider.downloader.DownloaderFactory;
import com.tce.newspider.spiderbean.render.RenderFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;

import java.util.Map;

public class SpiderBeanFactory {
    private static final Log LOG = LogFactory.getLog(SpiderBeanFactory.class);
    private DownloaderFactory downloaderFactory;


    public DownloaderFactory getDownloaderFactory() {
        return downloaderFactory;
    }
}
