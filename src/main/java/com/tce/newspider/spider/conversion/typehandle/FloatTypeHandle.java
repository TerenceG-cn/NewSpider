package com.tce.newspider.spider.conversion.typehandle;


import com.tce.newspider.spider.conversion.TypeHandle;

public class FloatTypeHandle implements TypeHandle<Float> {
	
	@Override
	public Float getValue(Object src) throws Exception {
		return Float.valueOf(src.toString());
	}

}
