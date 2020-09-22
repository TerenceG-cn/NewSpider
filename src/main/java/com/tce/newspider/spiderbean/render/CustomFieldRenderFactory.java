package com.tce.newspider.spiderbean.render;

import com.tce.newspider.annotation.FieldRenderName;
import com.tce.newspider.spiderbean.render.html.CustomFieldRender;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomFieldRenderFactory{
        private Map<String, CustomFieldRender> map;

        public CustomFieldRenderFactory(Reflections reflections) {//todo
            this.map = new HashMap<>();
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(FieldRenderName.class);
            for(Class<?> clazz : classes) {
                FieldRenderName fieldRenderName = clazz.getAnnotation(FieldRenderName.class);
                try {
                    map.put(fieldRenderName.value(), (CustomFieldRender)clazz.newInstance());
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public CustomFieldRender getCustomFieldRender(String name) {
            return map.get(name);
        }
}
