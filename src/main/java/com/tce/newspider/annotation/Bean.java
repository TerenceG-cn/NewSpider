package com.tce.newspider.annotation;

import java.lang.annotation.*;

@Inherited//使用此注解声明出来的自定义注解
@Target(ElementType.FIELD)//说明了Annotation所修饰的对象范围 FIELD 字段
@Retention(RetentionPolicy.RUNTIME)//注解生命周期 Runtime
public @interface Bean {
	
}
