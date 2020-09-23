package com.tce.newspider.listener;

import com.tce.newspider.EventListener;
import com.tce.newspider.SpiderEngine;

/**
 * 简单的引擎时间兼容实现类，可以继承该类覆盖需要的方法
 * 
 * @author LiuJunGuang
 */
public abstract class SimpleEventListener implements EventListener {

	/*
     * (non-Javadoc)
	 * 
	 * @see com.geccocrawler.gecco.listener.EventListener#onStart(com.geccocrawler.gecco.GeccoEngine)
	 */
	@Override
	public void onStart(SpiderEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geccocrawler.gecco.listener.EventListener#onPause(com.geccocrawler.gecco.GeccoEngine)
	 */
	@Override
	public void onPause(SpiderEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geccocrawler.gecco.listener.EventListener#onRestart(com.geccocrawler.gecco.GeccoEngine)
	 */
	@Override
	public void onRestart(SpiderEngine ge) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geccocrawler.gecco.listener.EventListener#onStop(com.geccocrawler.gecco.GeccoEngine)
	 */
	@Override
	public void onStop(SpiderEngine ge) {
	}

}
