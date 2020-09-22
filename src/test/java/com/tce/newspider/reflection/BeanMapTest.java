package com.tce.newspider.reflection;

import com.tce.newspider.spiderbean.SpiderBean;
import net.sf.cglib.beans.BeanMap;

import java.math.BigInteger;

public class BeanMapTest {
    public static void main(String[] args) {
        Pojo bean=new Pojo();//pojo，得有get set方法
        BeanMap map=BeanMap.create(bean);//bean对象转化成Map
        System.out.println(map);
        System.out.println(map.get("intValue"));
        System.out.println(map.keySet());
        System.out.println(map.values());

    }
}


class Pojo {

    private int intValue;
    private BigInteger bigInteger;



    public int getIntValue() {
        return intValue;
    }
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
    public BigInteger getBigInteger() {
        return bigInteger;
    }
    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }
}