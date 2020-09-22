package com.tce.newspider.spider.conversion;


import com.tce.newspider.spider.conversion.typehandle.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Conversion {

    /**
     * 简介：java.lang.SuppressWarnings是J2SE5.0中标准的Annotation之一。可以标注在类、字段、方法、参数、构造方法，以及局部变量上。
     * 作用：告诉编译器忽略指定的警告，不用在编译完成后出现警告信息。
     */
    @SuppressWarnings({"rawTypes"})
    private static final Map<Class<?>,TypeHandle> TYPE_HANDLERS=new HashMap<>();
    static{
        // int, float, long, double, java.util.Date, boolean, String
        TYPE_HANDLERS.put(Integer.class, new IntegerTypeHandle());
        TYPE_HANDLERS.put(int.class, new IntegerTypeHandle());
        TYPE_HANDLERS.put(Long.class, new LongTypeHandle());
        TYPE_HANDLERS.put(long.class, new LongTypeHandle());
        TYPE_HANDLERS.put(Float.class, new FloatTypeHandle());
        TYPE_HANDLERS.put(float.class, new FloatTypeHandle());
        TYPE_HANDLERS.put(Double.class, new DoubleTypeHandle());
        TYPE_HANDLERS.put(double.class, new DoubleTypeHandle());
        TYPE_HANDLERS.put(Boolean.class, new BooleanTypeHandle());
        TYPE_HANDLERS.put(boolean.class, new BooleanTypeHandle());
        TYPE_HANDLERS.put(Date.class, new DateTypeHandle());
        //TYPE_HANDLERS.put(BigDecimal.class, new BigDecimalTypeHandle());
    }

    @SuppressWarnings({ "rawtypes" })
    public static void register(Class<?> classType, TypeHandle typeHandle) {
        TYPE_HANDLERS.put(classType, typeHandle);
    }

    @SuppressWarnings({ "rawtypes" })
    public static Object getValue(Class<?> type, Object value) throws Exception {
        TypeHandle typeHandle = TYPE_HANDLERS.get(type);
        if (typeHandle != null && value != null) {
            return typeHandle.getValue(value);
        }
        return value;
    }
}
