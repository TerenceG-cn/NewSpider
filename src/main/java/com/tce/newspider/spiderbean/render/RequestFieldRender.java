package com.tce.newspider.spiderbean.render;

import com.tce.newspider.annotation.Request;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import com.tce.newspider.spiderbean.SpiderBean;
import net.sf.cglib.beans.BeanMap;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class RequestFieldRender implements FieldRender {

    @Override
    @SuppressWarnings({"unchecked"})
    public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
        Set<Field> requestFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(Request.class));
        for (Field field : requestFields) {
            beanMap.put(field.getName(), request);
        }
    }

}
