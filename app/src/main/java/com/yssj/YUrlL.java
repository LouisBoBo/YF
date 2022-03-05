
package com.yssj;

public interface YUrlL {

	/****
	 * 拼团夺宝开团参团
	 */
	String INDIANAGROUPSROLLTREA = YUrl.YSS_URL_ANDROID + "rollTrea/participation";

	/****
	 * 抽奖余额红包
	 */
	String DORAFFLE = YUrl.YSS_URL_ANDROID + "order/doRaffle";

	/****
	 * 获取当天还有多少次抽奖机会
	 */
	String ORDERRAFFLENUM = YUrl.YSS_URL_ANDROID + "order/getOrderRaffleNum";

	/****
	 * 获取是否绑定提现微信
	 */
	String GETWXOPENIDISBIND = YUrl.YSS_URL_ANDROID + "wallet/getWxOpenid";
	/**
	 * 转盘 抽奖总次数
	 */
	String GET_RAFFLE_NUM = YUrl.YSS_URL_ANDROID + "wallet/getRaffleNum";
	/**
	 * 好友提成系统 分享次数统计
	 */
	String FC_ADDSHARECOUNT = YUrl.YSS_URL_ANDROID + "share/addShareCount";
	/**
	 * 好友提成系统 分享次数统计 获得奖励弹窗数据
	 */
	 String GETTCTODAYCOUNT = YUrl.YSS_URL_ANDROID + "wallet/getTcToDayCount";

	/**
	 * 好友提成系统 分享次数统计 获得奖励弹窗数据
	 */
	 String GETZEROCOUNT = YUrl.YSS_URL_ANDROID + "wallet/getZeroCount";

}
