package com.tce.newspider.spider.conversion.typehandle;

import com.tce.newspider.spider.conversion.TypeHandle;

public class LongTypeHandle implements TypeHandle<Long> {
	
	@Override
	public Long getValue(Object src) throws Exception {
		return Long.valueOf(src.toString());
	}

}
