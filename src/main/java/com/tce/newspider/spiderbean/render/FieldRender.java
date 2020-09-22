package com.tce.newspider.spiderbean.render;

import com.tce.newspider.http.HttpRequest;
import com.tce.newspider.http.HttpResponse;
import com.tce.newspider.spiderbean.SpiderBean;
import net.sf.cglib.beans.BeanMap;

/*字段渲染接口*/
public interface FieldRender {
	
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) throws FieldRenderException;

}
