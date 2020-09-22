package com.tce.newspider.spiderbean.render;

import com.tce.newspider.annotation.FieldRenderName;
import com.tce.newspider.annotation.Href;
import com.tce.newspider.http.*;
import com.tce.newspider.reflection.ReflectUtils;
import com.tce.newspider.spider.SpiderThreadLocal;
import com.tce.newspider.spiderbean.SpiderBean;
import com.tce.newspider.spiderbean.render.html.CustomFieldRender;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;


/**
 * render抽象方法，主要包括注入基本的属性和自定义属性注入。将特定的html、json、xml注入放入实现类
 *
 * @author huchengyi
 */
public abstract class AbstractRender implements Render {

    private static Log log = LogFactory.getLog(AbstractRender.class);

    /**
     * request请求字段的注入
     */
    private RequestFieldRender requestFieldRender;

    /**
     * request参数的注入
     */
    private RequestParameterFieldRender requestParameterFieldRender;

    /**
     * 自定义注入
     */
    private CustomFieldRenderFactory customFieldRenderFactory;

    public AbstractRender() {
        requestFieldRender = new RequestFieldRender();
        requestParameterFieldRender = new RequestParameterFieldRender();
    }

    @Override
    public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response) {
        try {
            SpiderBean bean = clazz.newInstance();//只能反射无参的构造器，需要构造器可见
            BeanMap beanMap = BeanMap.create(bean);//bean对象转化成Map
            requestFieldRender.render(request, response, beanMap, bean);//注入请求 字段
            requestParameterFieldRender.render(request, response, beanMap, bean);//注入请求参数 字段
            fieldRender(request, response, beanMap, bean);//字段注入 抽象方法
            //自定义注入
            Set<Field> customFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(FieldRenderName.class));
            for (Field customField : customFields) {
                FieldRenderName fieldRender = customField.getAnnotation(FieldRenderName.class);
                String name = fieldRender.value();
                CustomFieldRender customFieldRender = customFieldRenderFactory.getCustomFieldRender(name);
                if (customFieldRender != null) {
                    customFieldRender.render(request, response, beanMap, bean, customField);
                }
            }
            requests(request, bean);
            return bean;
        } catch (InstantiationException | IllegalAccessException | FieldRenderException e) {
            log.error("instance SpiderBean error", e);
            return null;
        }
    }

    /**
     * 需要继续抓取的请求
     */
    public void requests(HttpRequest request, SpiderBean bean) {
        BeanMap beanMap = BeanMap.create(bean);
        Set<Field> hrefFields = ReflectionUtils.getAllFields(bean.getClass(),
                ReflectionUtils.withAnnotation(Href.class));
        for (Field hrefField : hrefFields) {
            Href href = hrefField.getAnnotation(Href.class);
            //是否需要点击链接
            if (href.isClick()) {
                Object o = beanMap.get(hrefField.getName());
                if (o == null) {
                    continue;
                }
                boolean isList = ReflectUtils.haveSuperType(o.getClass(), List.class);// 是List类型 todo 研究一下工具类
                if (isList) {
                    List<String> list = (List<String>) o;
                    for (String url : list) {
                        if (StringUtils.isNotEmpty(url)) {
                            SpiderThreadLocal.get().getSpiderScheduler().in(request.subRequest(url));
                        }
                    }
                } else {
                    String url = (String) o;
                    if (StringUtils.isNotEmpty(url)) {
                        SpiderThreadLocal.get().getSpiderScheduler().in(request.subRequest(url));
                    }
                }
            }
        }
    }

    public abstract void fieldRender(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean);

    public void setCustomFieldRenderFactory(CustomFieldRenderFactory customFieldRenderFactory) {
        this.customFieldRenderFactory = customFieldRenderFactory;
    }
}