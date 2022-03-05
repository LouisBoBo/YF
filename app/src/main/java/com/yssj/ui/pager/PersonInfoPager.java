//package com.yssj.ui.pager;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//import android.content.Context;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.data.DBService;
//import com.yssj.data.YDBHelper;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.base.BasePager;
//
//public class PersonInfoPager extends BasePager {
//
//	private String user_id;
//
//	private TextView tv_area, tv_birthday, tv_hobby, tv_person_sign;
//
//	private YDBHelper helper;
//
//	public PersonInfoPager(Context context, String user_id) {
//		super(context);
//		this.user_id = user_id;
//	}
//
//	@Override
//	public View initView() {
//		view = View
//				.inflate(context, R.layout.activity_circle_person_info, null);
//
//		tv_area = (TextView) view.findViewById(R.id.tv_area);
//		tv_birthday = (TextView) view.findViewById(R.id.tv_birthday);
//		tv_hobby = (TextView) view.findViewById(R.id.tv_hobby);
//		tv_person_sign = (TextView) view.findViewById(R.id.tv_person_sign);
//
//		return view;
//	}
//
//	@Override
//	public void initData() {
//		helper = new YDBHelper(context);
//		initPersonInfoData();
//	}
//
//	private void initPersonInfoData() {
//		new SAsyncTask<String, Void, Map<String, Object>>(
//				(FragmentActivity) context, R.string.wait) {
//			@Override
//			protected Map<String, Object> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return YJApplication.instance.isLoginSucess() ? ComModel2
//						.getCircleHomePager(context, user_id) : ComModel2
//						.getCircleHomePager2(context, user_id);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					Map<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					// tv_area.setText((String)result.get("person_sign"));
//					if(((String) result.get("birthday")).equals("null")){
//						tv_birthday.setText("");
//					}else{
//						 long birthday = Long.parseLong((String) result.get("birthday"));
//						 SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
//						 String date = smf.format(new Date(birthday));
//					tv_birthday.setText(date);
//					}
//					if(((String) result.get("person_sign")).equals("null")){
//						tv_person_sign.setText("");
//					}else{
//						tv_person_sign.setText((String) result.get("person_sign"));
//					}
//					
//					if (!(result.get("province").equals("")
//							|| result.get("province").equals("null")
//							|| result.get("province").equals("null")
//							|| result.get("province") == null
//							|| result.get("city").equals("") || result
//								.get("city") == null)) {
//						String city = DBService.getIntance().queryAreaNameById(
//								Integer.parseInt((String) result
//										.get("province")))
//								+ " "
//								+ DBService.getIntance().queryAreaNameById(
//										Integer.parseInt((String) result
//												.get("city")));
//						// MyLogYiFu.e("设置后",city.toString());
//
//						tv_area.setText(city);
//					}
//					String idStr = result.get("hobby").toString();
//					if(idStr.equals("null")){
//						tv_hobby.setText("");
//					}else{
//					String ids[] = idStr.split(",");
//					String queryAttr_name = "";
//					for (int i = 0; i < ids.length; i++) {
//						queryAttr_name += helper.queryHobbyName(ids[i]) + ",";
//					}
//					tv_hobby.setText(queryAttr_name.substring(0,
//							queryAttr_name.length() - 1));
//					}
//				}
//			}
//		}.execute(user_id);
//	}
//
//}
