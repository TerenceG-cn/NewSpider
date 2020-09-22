package com.tce.newspider.annotation;


import java.lang.annotation.*;

/**
 * 字段渲染注解
 */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldRenderName {

    String value();

}
