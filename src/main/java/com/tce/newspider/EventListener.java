package com.tce.newspider;


/**
 * 爬虫引擎生命周期监听器
 * 
 * @author LiuJunGuang
 */
public interface EventListener {

	/**
     * 开始启动时，回调
	 * 
	 * @param ge GeccoEngine
	 */
	public void onStart(SpiderEngine ge);

	/**
	 * 暂停时，回调
	 * 
	 * @param ge GeccoEngine
	 */
	public void onPause(SpiderEngine ge);

	/**
	 * 恢复抓取时，回调
	 * 
	 * @param ge GeccoEngine
	 */
	public void onRestart(SpiderEngine ge);

	/**
	 * 引擎停止时，回调
	 * 
	 * @param ge GeccoEngine
	 */
	public void onStop(SpiderEngine ge);
}
