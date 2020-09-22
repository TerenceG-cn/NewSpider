package com.tce.newspider.spider.conversion.typehandle;


import com.tce.newspider.spider.conversion.TypeHandle;

public class DoubleTypeHandle implements TypeHandle<Double> {

	@Override
	public Double getValue(Object src) throws Exception {
		return Double.valueOf(src.toString());
	}

}
