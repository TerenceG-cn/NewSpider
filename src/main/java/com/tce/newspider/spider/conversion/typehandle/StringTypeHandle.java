package com.tce.newspider.spider.conversion.typehandle;

import com.tce.newspider.spider.conversion.TypeHandle;

public class StringTypeHandle implements TypeHandle<String> {

	@Override
	public String getValue(Object src) {
		return src.toString();
	}

}
