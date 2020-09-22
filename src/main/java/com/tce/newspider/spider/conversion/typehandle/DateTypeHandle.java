package com.tce.newspider.spider.conversion.typehandle;


import com.tce.newspider.spider.conversion.TypeHandle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeHandle implements TypeHandle<Date> {
	
	@Override
	public Date getValue(Object src) throws Exception {
		return getValue(src, "yyyy-MM-dd HH:mm:ss");
	}

	public Date getValue(Object src, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(src.toString());
	}
}
