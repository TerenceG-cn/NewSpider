package com.tce.newspider.spider.conversion.typehandle;

import com.tce.newspider.spider.conversion.TypeHandle;

public class BooleanTypeHandle implements TypeHandle<Boolean> {

	@Override
	public Boolean getValue(Object src) throws Exception {
		return Boolean.valueOf(src.toString().toLowerCase());
	}

}
