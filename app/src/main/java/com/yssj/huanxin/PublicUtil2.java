//package com.yssj.huanxin;
//
//import java.util.Map;
//import java.util.Set;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.easemob.EMCallBack;
//import com.easemob.EMError;
//import com.easemob.chat.EMChatManager;
//import com.easemob.chat.EMGroupManager;
//import com.easemob.exceptions.EaseMobException;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
//import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
//import com.umeng.socialize.exception.SocializeException;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//import com.yssj.Constants;
//import com.yssj.YConstance.Pref;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.MineLikeActivity;
//import com.yssj.ui.activity.setting.BindPhoneActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//import com.yssj.wxpay.WxPayUtil;
//
//public class PublicUtil2 {
//	public static void loginHxServer(final Context mContext, String username, String password){
//		EMChatManager.getInstance().login(username+"",password,new EMCallBack() {//回调
//		@Override
//		public void onSuccess() {
//			((Activity) mContext).runOnUiThread(new Runnable() {
//				public void run() {
//					EMGroupManager.getInstance().loadAllGroups();
//					EMChatManager.getInstance().loadAllConversations();
////					ToastUtil.showLongText(mContext, "注册成功");
//					LogYiFu.d("main", "登陆聊天服务器成功！");		
//					//成功之后 跳到我的喜爱
//					Intent intent = new Intent(mContext,
//							MineLikeActivity.class);
//					mContext.startActivity(intent);
//					((Activity)mContext).finish();
//				}
//			});
//		}
//
//		@Override
//		public void onProgress(int progress, String status) {
//
//		}
//
//		@Override
//		public void onError(int code, String message) {
//			LogYiFu.d("main", "登陆聊天服务器失败！");
//		}
//	});}
//	
//	public static void loginHxServer1(final Context mContext, String username, String password){
//		EMChatManager.getInstance().login(username+"",password,new EMCallBack() {//回调
//		@Override
//		public void onSuccess() {
//			((Activity) mContext).runOnUiThread(new Runnable() {
//				public void run() {
//					EMGroupManager.getInstance().loadAllGroups();
//					EMChatManager.getInstance().loadAllConversations();
//					LogYiFu.d("main", "登陆聊天服务器成功！");		
//					//成功之后 跳到我的喜爱
//
//					Intent intent = new Intent(mContext,
//							MainMenuActivity.class);
//					mContext.startActivity(intent);
//					((Activity)mContext).finish();
//				}
//			});
//		}
//
//		@Override
//		public void onProgress(int progress, String status) {
//
//		}
//
//		@Override
//		public void onError(int code, String message) {
//			LogYiFu.d("main", "登陆聊天服务器失败！");
//		}
//	});}
//	
//	public static void registerHuanxin(final Activity activity,
//			final UserInfo result, final String pwd) {
//		activity.getSharedPreferences(Pref.pd, Context.MODE_PRIVATE).edit().putString(Pref.pd, pwd).commit();
//		new Thread(new Runnable() {
//
//			public void run() {
//				try {
//					// 调用sdk注册方法
//					EMChatManager.getInstance().createAccountOnServer(
//							result.getUser_id() + "", MD5Tools.MD5(pwd));
//					SharedPreferences sp = activity.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE);
//					Editor edit = sp.edit();
//					edit.putInt("paycount", 0);
//					edit.commit();
//					activity.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							
//							YCache.setCacheUser(activity, result);// 登陆成功后设置缓存
//							ComModel.saveLoginFlag(activity);
////							activity.getSharedPreferences("isFirstH5Login", Context.MODE_PRIVATE).edit().putBoolean("isFirstH5Login", true).commit();
////							activity.getSharedPreferences("ShopDetailLogin", Context.MODE_PRIVATE).edit().putBoolean("ShopDetailLogin", false).commit();
////							activity.startActivity(intent);
//							openId(SHARE_MEDIA.WEIXIN, 0,activity);
//							activity.setResult(-1);
//							ShopDetailsActivity.everyDayTask1_2=0;
//							ShopDetailsActivity.shopTask=0;
//							((Activity)activity).finish();
//						}
//					});
////					loginHxServer(activity,result.getUser_id()+"", MD5Tools.MD5(pwd));
//				} catch (final EaseMobException e) {
//					activity.runOnUiThread(new Runnable() {
//						public void run() {
//							int errorCode = e.getErrorCode();
//							if (errorCode == EMError.NONETWORK_ERROR) {
//								ToastUtil
//										.showShortText(activity, "网络异常，请检查网络！");
//							} else if (errorCode == EMError.USER_ALREADY_EXISTS) {
////								
//								ComModel.saveLoginFlag(activity);
//								
//								activity.setResult(-1);
//								
//								((Activity)activity).finish();
//							} else if (errorCode == EMError.UNAUTHORIZED) {
//								ToastUtil.showShortText(activity, "注册失败，无权限！");
//							} else {
//								ToastUtil.showShortText(activity, "注册失败，无权限！");
//							}
//						}
//					});
//				}
//			}
//
//		}).start();
//	}
//	
//	public static void openId(final SHARE_MEDIA platform, final int type,final Activity activity) {
//
//		final UMSocialService mController=UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
//		
//		mController.doOauthVerify(activity, platform,
//				new UMAuthListener() {
//
//					@Override
//					public void onStart(SHARE_MEDIA platform) {
//						
//					}
//
//					@Override
//					public void onError(SocializeException e,
//							SHARE_MEDIA platform) {
//					}
//
//					@Override
//					public void onComplete(Bundle value, SHARE_MEDIA platform) {
//						
//						mController.getPlatformInfo(activity, platform, new UMDataListener() {
//
//							@Override
//							public void onStart() {
//
//							}
//
//							@Override
//							public void onComplete(int status, Map<String, Object> info) {
//								if (info != null) {
//									if (type == 0) {// 微信
//										if (status == 200 && info != null) {
//											final String uuid=info.get("unionid").toString();
//											final int sex = Integer.parseInt( info.get("sex").toString());
//											if(TextUtils.isEmpty(uuid)){
//												return;
//											}
//											activity.getSharedPreferences("weixin", Context.MODE_PRIVATE).edit().putString("uuid", "").commit();
//											
//											if(sex==1){
//												customDialog(activity);
//												return;
//											}
////											new Thread(){
////												public void run() {
////													try {
////														ComModel2.registerOpenId(uuid, activity);
////													} catch (Exception e) {
////														
////													}
////												};
////											}.start();
//										}
//									}
//								}
//							}
//						});
//					}
//
//					@Override
//					public void onCancel(SHARE_MEDIA platform) {
//						
//					}
//				});
//
//	}
//	
//	
//	public static void registerHuanxin1(final Activity activity,
//			final UserInfo result, final String pwd) {
//		activity.getSharedPreferences(Pref.pd, Context.MODE_PRIVATE).edit().putString(Pref.pd, pwd).commit();
//		new Thread(new Runnable() {
//
//			public void run() {
//				try {
//					// 调用sdk注册方法
//					EMChatManager.getInstance().createAccountOnServer(
//							result.getUser_id() + "", MD5Tools.MD5(pwd));
//					SharedPreferences sp = activity.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE);
//					Editor edit = sp.edit();
//					edit.putInt("paycount", 0);
//					edit.commit();
//					activity.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							
//							Intent intent = new Intent(activity,
//									MineLikeActivity.class);
//							Bundle bundle = new Bundle();
//							bundle.putSerializable("userinfo", result);
//							intent.putExtras(bundle);
//							activity.startActivity(intent);
//							((Activity)activity).finish();
//							
//						}
//					});
////					loginHxServer(activity,result.getUser_id()+"", MD5Tools.MD5(pwd));
//				} catch (final EaseMobException e) {
//					activity.runOnUiThread(new Runnable() {
//						public void run() {
//							int errorCode = e.getErrorCode();
//							if (errorCode == EMError.NONETWORK_ERROR) {
//								ToastUtil
//										.showShortText(activity, "网络异常，请检查网络！");
//							} else if (errorCode == EMError.USER_ALREADY_EXISTS) {
////								ToastUtil.showShortText(activity, "用户已存在！");
////								loginHxServer1(activity,result.getUser_id()+"", MD5Tools.MD5(pwd));
//								Intent intent = new Intent(activity,
//										MineLikeActivity.class);
//								Bundle bundle = new Bundle();
//								bundle.putSerializable("userinfo", result);
//								intent.putExtras(bundle);
//								activity.startActivity(intent);
//								((Activity)activity).finish();
//							} else if (errorCode == EMError.UNAUTHORIZED) {
//								ToastUtil.showShortText(activity, "注册失败，无权限！");
//							} else {
//								ToastUtil.showShortText(activity, "注册失败，无权限！");
//							}
//						}
//					});
//				}
//			}
//
//		}).start();
//	}
//	
//	public static void Uuid(final SHARE_MEDIA platform, final int type,
//			final Activity activity) {
//
//		final UMSocialService mController = UMServiceFactory
//				.getUMSocialService(Constants.DESCRIPTOR);
//		UMWXHandler wxHandler = new UMWXHandler(activity, WxPayUtil.APP_ID,
//				WxPayUtil.APP_SECRET);
//		wxHandler.addToSocialSDK();
//		mController.doOauthVerify(activity, platform, new UMAuthListener() {
//
//			@Override
//			public void onStart(SHARE_MEDIA platform) {
//
//			}
//
//			@Override
//			public void onError(SocializeException e, SHARE_MEDIA platform) {
//			}
//
//			@Override
//			public void onComplete(Bundle value, SHARE_MEDIA platform) {
//
//				mController.getPlatformInfo(activity, platform,
//						new UMDataListener() {
//
//							@Override
//							public void onStart() {
//
//							}
//
//							@Override
//							public void onComplete(int status,
//									Map<String, Object> info) {
//								if (info != null) {
//									if (type == 0) {// 微信
//										if (status == 200 && info != null) {
//											MyLogYiFu.e("微信授权", info.toString());
//											final String uid = info.get(
//													"openid").toString();
//											YCache.getCacheUser(activity)
//													.setUuid(uid);
//											final String pic = info.get(
//													"headimgurl").toString();
//											final String nickname = info.get(
//													"nickname").toString();
//											final String unionid = info.get(
//													"unionid").toString();
//											if (TextUtils.isEmpty(uid)) {
//												return;
//											}
//											new Thread() {
//												public void run() {
//													try {
//														ComModel2
//																.updateUuid(activity,
//																		unionid
//																		);
//													} catch (Exception e) {
//
//													}
//												};
//											}.start();
//										}
//									}
//								}
//							}
//						});
//			}
//
//			@Override
//			public void onCancel(SHARE_MEDIA platform) {
//
//			}
//		});
//
//	}
//	private static AlertDialog dialog;
//	private static void customDialog(final Activity activity) {
//		AlertDialog.Builder builder = new Builder(activity);
//		// 自定义一个布局文件
//		View view = View.inflate(activity, R.layout.payback_esc_apply_dialog,
//				null);
//		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
//		tv_des.setText("此处为菇凉们的衣橱，男士避让，谢谢！");
////		dialog.setCancelable(false);
//		Button ok = (Button) view.findViewById(R.id.ok);
//		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
//		Button cancel = (Button) view.findViewById(R.id.cancel);
//		cancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 把这个对话框取消掉
//				dialog.dismiss();
//				activity.finish();
////				AppManager.getAppManager().finishAllActivity();
//				
//
//			}
//		});
//
//		ok.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				activity.finish();
//				AppManager.getAppManager().finishAllActivity();
//			}
//		});
//
//		dialog = builder.create();
//		dialog.setView(view, 0, 0, 0, 0);
//		dialog.setCanceledOnTouchOutside(false);  
//		dialog.show();
//	}
//}
