package com.yssj.alipay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PayUtil {
	
	// 商户PID
		/*public static final String PARTNER = "2088911598493391";
		// 商户收款账号
		public static final String SELLER = "yssj@91kwd.com";
		// 商户私钥，pkcs8格式
		public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL7stj/CKUfmbQPo"
				+ "gHn9DZtEuxLwSAy9FHsao0USmqRrbvAkMg+DMMhh9dSqHUCuN1DExbgAvhHto9jn"
				+ "sznh27vcFfMikdaGBZqUB1Te4I3fMhirbeSPdbGVE7AMOqvJq/girZDhJ6ieMQXC"
				+ "5+YuUQQT+ovzKvkNUjpvkKKl+a7ZAgMBAAECgYAvbK8MgVcts/AKU3tuUcxKcDUj"
				+ "zCmpeGIY/hHmO2vMQZ9p6SPCNK0uaR7eN29SvLOizW3recu8ulHDtDIRw6eHwT8Y"
				+ "bRDwd9qBW58GnL1d8PfC6uw+/7f+YoHTkqrNIZvD1adDJJjyX9NSH662xyITuzpa"
				+ "L5NXbpYKYn8IVKWOvQJBAO++V2djL3pxH67L583EAdlGV39JzP21oMxCRqRCuVTS"
				+ "0DjcRjg/lG4cEW69t3sIN+V2+8pnULBBmO5xP1o41XsCQQDL3u8PPxvoIm7xHWLZ"
				+ "SQpLv4EILG2O94ddXETmGWIeZ8AVzRCKHTN0ShOvmNZnCEbelBWb/pkIM4LYrVzq"
				+ "kdq7AkAQ+yhxuELKp2yZEvROTM3ct/DGoVGVvuGu1hru05MRAQWioWeP4GEBE5fg"
				+ "giuW2VQsOqtHAN5kPaE5cmgMWe41AkEAxhtIKoSk1ZpAPETV/VcgjiL1e7/QZpDa"
				+ "FTrIKOCZm/otigHPBKcDjQk+v+/AyDYex8MWjJOGmZWUnIE6PSamaQJBAKDxW+KJ"
				+ "N3ihQx8NwU/wKKuPZe4hhtZNJ39VhzLUwhoYdrPXipoPT8qHnlVz73WSPesizGvG"
				+ "HTBLZWyeZy6iahI=";
		// 支付宝公钥
		public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+7LY/wilH5m0D6IB5/Q2bRLsS"
				+ "8EgMvRR7GqNFEpqka27wJDIPgzDIYfXUqh1ArjdQxMW4AL4R7aPY57M54du73BXz"
				+ "IpHWhgWalAdU3uCN3zIYq23kj3WxlROwDDqryav4Iq2Q4SeonjEFwufmLlEEE/qL"
				+ "8yr5DVI6b5Cipfmu2QIDAQAB";*/

		private static final int SDK_PAY_FLAG = 1;

		private static final int SDK_CHECK_FLAG = 2;
	
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public static String getOrderInfo(String subject, String body,String order_code, String price, String notify_path, String partner, String seller) {
		/*// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";*/
		
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + partner + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + seller + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + order_code + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\""
				+ notify_path
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 *  rsa_private:商户私钥          
	 */
	public static String sign(String content, String rsa_private) {
		return SignUtils.sign(content, rsa_private);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public static String getSignType(String type) {
		return "sign_type=\""+type+"\"";
	}
}
