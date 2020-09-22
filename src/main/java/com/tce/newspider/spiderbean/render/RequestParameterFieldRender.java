package com.tce.newspider.spiderbean.render;


import com.tce.newspider.annotation.RequestParameter;
import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import com.tce.newspider.spider.conversion.Conversion;
import com.tce.newspider.spiderbean.SpiderBean;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestParameterFieldRender implements FieldRender{
    @Override
    @SuppressWarnings({"unchecked" })
    public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) throws FieldRenderException{
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        Set<Field> requestParameterFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(RequestParameter.class));
        for(Field field : requestParameterFields) {
            RequestParameter requestParameter = field.getAnnotation(RequestParameter.class);
            String key = requestParameter.value();
            if(StringUtils.isEmpty(key)) {
                key = field.getName();
            }
            String parameterValue = request.getParameter(key);
//            if(request instanceof HttpPostRequest && StringUtils.isEmpty(src)) {
//                HttpPostRequest postRequest = (HttpPostRequest)request;
//                src = postRequest.getField(key);
//            }
            try {
                Object value = Conversion.getValue(field.getType(), parameterValue);
                fieldMap.put(field.getName(), value);
            } catch(Exception ex) {
                throw new FieldRenderException(field, parameterValue, ex);
            }
        }
        beanMap.putAll(fieldMap);
    }
}
