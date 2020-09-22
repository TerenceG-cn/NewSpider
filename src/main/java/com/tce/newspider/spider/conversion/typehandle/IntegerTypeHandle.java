package com.tce.newspider.spider.conversion.typehandle;

import com.tce.newspider.spider.conversion.TypeHandle;

public class IntegerTypeHandle implements TypeHandle<Integer> {

    @Override
    public Integer getValue(Object src) throws Exception {
        return Integer.valueOf(src.toString());
    }

}
