package com.tce.newspider.spiderbean.render.html;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;

import com.geccocrawler.gecco.spider.render.html.AjaxFieldRender;
import com.geccocrawler.gecco.spider.render.html.HtmlFieldRender;
import com.geccocrawler.gecco.spider.render.html.ImageFieldRender;
import com.geccocrawler.gecco.spider.render.html.JSVarFieldRender;
import com.tce.newspider.spiderbean.render.AbstractRender;
import net.sf.cglib.beans.BeanMap;

/**
 * 将下载下来的html映射到bean中
 * 
 * @author huchengyi
 *
 */
public class HtmlRender extends AbstractRender {
	
	private HtmlFieldRender htmlFieldRender;

	private AjaxFieldRender ajaxFieldRender;

	private JSVarFieldRender jsVarFieldRender;

	private ImageFieldRender imageFieldRender;
	
	public HtmlRender() {
		super();
		this.htmlFieldRender = new HtmlFieldRender();
		this.ajaxFieldRender = new AjaxFieldRender();
		this.jsVarFieldRender = new JSVarFieldRender();
		this.imageFieldRender = new ImageFieldRender();
	}

	@Override
	public void fieldRender(com.tce.newspider.http.HttpRequest request, com.tce.newspider.http.HttpResponse response, BeanMap beanMap, com.tce.newspider.spiderbean.SpiderBean bean) {
	}


	public void fieldRender(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		htmlFieldRender.render(request, response, beanMap, bean);
		ajaxFieldRender.render(request, response, beanMap, bean);
		jsVarFieldRender.render(request, response, beanMap, bean);
		imageFieldRender.render(request, response, beanMap, bean);
	}

}
