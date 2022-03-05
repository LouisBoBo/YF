
package com.yssj;

public interface YUrlZ {

	/****
	 * 拼团夺宝详情------参与记录列表
	 */
	String GROUP_DETAILS_TAKE_RECORD = YUrl.YSS_URL_ANDROID + "rollTrea/pationData";
	/****
	 * 拼团夺宝详情------拼团详情列表
	 */
	String GROUP_DETAILS_GROUP_LIST = YUrl.YSS_URL_ANDROID + "rollTrea/getMyRoll";
	/****
	 * 我的钱包-------自动提现提交姓名和
	 */
	String AURO_WITHDRAW = YUrl.YSS_URL_ANDROID + "wallet/addWxOpenid";

	/****
	 * 获取二级类目名字
	 */
	String getType2Supplabel = YUrl.YSS_URL_ANDROID + "shop/queryShopType2";
}
