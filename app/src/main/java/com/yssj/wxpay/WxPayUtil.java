package com.yssj.wxpay;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.utils.LogYiFu;

import android.util.Xml;

public class WxPayUtil {

	// appid
	// 请同时修改 androidmanifest.xml里面，.PayActivityd里的属性<data
	// android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
//	public static final String APP_ID = "wx8c5fe3e40669c535";
//	// 商户号
//	public static final String MCH_ID = "1265692601";
//	// API密钥，在商户平台设置
//	public static final String API_KEY = "B9CB71EC2BAE087D0B9A37BDFABD328D";
//	//	API secret
//	public static final String APP_SECRET = "10d080a714d768427242e9b091d33959";
	
	
//	
//	//测试环境微信账号配置  (以后不分正式和测试)  (后台获取)
	public static final String APP_ID = MainMenuActivity.APP_ID;
	// 商户号
	public static final String MCH_ID = MainMenuActivity.MCH_ID;
	// API密钥，在商户平台设置
	public static final String API_KEY = MainMenuActivity.API_KEY;
	//	API secret
	public static final String APP_SECRET = MainMenuActivity.APP_SECRET;
//	
//	
//	wx.app.key=B9CB71EC2BAE087D0B9A37BDFABD328D
//			wx.app.appID=wxbb9728502635a425
//			wx.app.mchID=1284094601
////			wx.app.certPassword=1284094601
//			wx.app.AppSecret=d4624c36b6795d1d99dcf0547af5443d
//			wx.app.AppSecret=d4624c36b6795d1d99dcf0547af5443d
	
	
	
	//写死正式
	// appid
		// 请同时修改 androidmanifest.xml里面，.PayActivityd里的属性<data
		// android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
//		public static final String APP_ID = "wx8c5fe3e40669c535";
//		// 商户号
//		public static final String MCH_ID = "1265692601";
//		// API密钥，在商户平台设置
//		public static final String API_KEY = "B9CB71EC2BAE087D0B9A37BDFABD328D";
//		//	API secret
//		public static final String APP_SECRET = "10d080a714d768427242e9b091d33959";
//	////	
		
	//写死测试
//		//测试环境微信账号配置
//		public static final String APP_ID = "wxbb9728502635a425";
//		// 商户号
//		public static final String MCH_ID = "1284094601";
//		// API密钥，在商户平台设置
//		public static final String API_KEY = "B9CB71EC2BAE087D0B9A37BDFABD328D";
//		//	API secret
//		public static final String APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";
	//	
	//	
//		wx.app.key=B9CB71EC2BAE087D0B9A37BDFABD328D
//				wx.app.appID=wxbb9728502635a425
//				wx.app.mchID=1284094601
////				wx.app.certPassword=1284094601
//				wx.app.AppSecret=d4624c36b6795d1d99dcf0547af5443d
//				wx.app.AppSecret=d4624c36b6795d1d99dcf0547af5443d

	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 生成签名
	 */
	public static String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		LogYiFu.e("orion", packageSign);
		return packageSign;
	}

	/***
	 * 生成支付参数
	 * @param resultunifiedorder
	 * @return
	 */
	public static PayReq genPayReq(Map<String, String> resultunifiedorder) {
		PayReq req = new PayReq();
		req.appId = resultunifiedorder.get("appid");
		req.partnerId = resultunifiedorder.get("mch_id");
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		LogYiFu.e("orion", signParams.toString());
		return req;
	}

	public static String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	/**
	 * 时间戳
	 * @return
	 */
	public static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/***
	 * 生成签名信息
	 * @param params
	 * @return
	 */
	public static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);

		// this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		LogYiFu.e("orion", appSign);
		return appSign;
	}

	/***
	 * 调用支付
	 * @param msgApi
	 * @param req
	 */
	public static void sendPayReq(IWXAPI msgApi, PayReq req, String appId) {

		msgApi.registerApp(appId);
		msgApi.sendReq(req);
	}

	/***
	 * 生成订单信息
	 * @param outTradNo
	 * @return
	 */
	public static String genProductArgs(String outTradNo) {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
					.add(new BasicNameValuePair("appid", APP_ID));
			packageParams.add(new BasicNameValuePair("body", "weixin"));
			packageParams
					.add(new BasicNameValuePair("mch_id", MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url",
					"daihoukun.nat123.net/cloud-api/wxpay/resultInfo"));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					outTradNo));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", "0.01"));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			LogYiFu.e("微信支付", "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}
	}
	/***
	 * 生成XML格式
	 * @param params
	 * @return
	 */
	public static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");


			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");

		LogYiFu.e("orion",sb.toString());
		return sb.toString();
	}
	
	public static Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			LogYiFu.e("orion",e.toString());
		}
		return null;

	}
}
