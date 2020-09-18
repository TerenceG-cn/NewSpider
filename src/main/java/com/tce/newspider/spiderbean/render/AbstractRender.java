package com.tce.newspider.spiderbean.render;


import com.tce.newspider.spiderbean.SpiderBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * render抽象方法，主要包括注入基本的属性和自定义属性注入。将特定的html、json、xml注入放入实现类
 *
 * @author huchengyi
 *
 */
public abstract class AbstractRender implements Render {

    private static Log log = LogFactory.getLog(com.geccocrawler.gecco.spider.render.AbstractRender.class);

    /**
     * request请求的注入
     */
    //private RequestFieldRender requestFieldRender;

    /**
     * request参数的注入
     */
    //private RequestParameterFieldRender requestParameterFieldRender;

    /**
     * 自定义注入
     */
    //private CustomFieldRenderFactory customFieldRenderFactory;

    @Override
    public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response){
        return null;
    }

    public void requests(HttpRequest request, SpiderBean bean){

    }

}