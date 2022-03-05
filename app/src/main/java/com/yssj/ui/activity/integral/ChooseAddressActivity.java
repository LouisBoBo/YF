package com.yssj.ui.activity.integral;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.IntegralShop;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class ChooseAddressActivity extends BasicActivity {

	private TextView tv_name, tv_phone, tv_receiver_addr;

	private DBService db = new DBService(this);

	private TextView tv_sum, tv_pro_name, tv_pro_descri;
	private ImageView img_pro_pic;

	private IntegralShop shop;

	private String size, color, price;
	private int shop_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	private void initData() {
		new SAsyncTask<Void, Void, List<DeliveryAddress>>(this, R.string.wait) {

			@Override
			protected List<DeliveryAddress> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getDeliverAddr(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<DeliveryAddress> result) {
				// TODO Auto-generated method stub
				if (null != result) {
					initView(result);
				}
				super.onPostExecute(context, result);
			}

		}.execute();

		shop = (IntegralShop) getIntent().getSerializableExtra("shop");
		size = getIntent().getStringExtra("size");
		color = getIntent().getStringExtra("color");
		shop_num = getIntent().getIntExtra("shop_num", 0);
		price = getIntent().getStringExtra("price");

	}

	private void initView(List<DeliveryAddress> result) {
		setContentView(R.layout.choose_address);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_receiver_addr = (TextView) findViewById(R.id.tv_receiver_addr);
		DeliveryAddress dAddress = result.get(0);
		tv_name.setText("收件人：" + dAddress.getConsignee());
		tv_phone.setText(dAddress.getPhone());
		String province = db.queryAreaNameById(dAddress.getProvince());
		String city = db.queryAreaNameById(dAddress.getCity());
		String county = db.queryAreaNameById(dAddress.getArea());
		String street = "";
		if (0 != dAddress.getStreet()) {
			street = db.queryAreaNameById(dAddress.getStreet());
		}
		tv_receiver_addr.setText(province + city + county + street
				+ dAddress.getAddress());

		tv_sum = (TextView) findViewById(R.id.tv_sum);
		tv_pro_name = (TextView) findViewById(R.id.tv_pro_name);
		tv_pro_descri = (TextView) findViewById(R.id.tv_pro_descri);
		img_pro_pic = (ImageView) findViewById(R.id.img_pro_pic);

		tv_sum.setText(price + "\nX" + shop_num);
		tv_pro_name.setText(shop.getShop_name());
		tv_pro_descri.setText(shop.getContent() + size + color);
//		SetImageLoader.initImageLoader(this, img_pro_pic,shop.getDef_pic(),"");
		PicassoUtils.initImage(this, shop.getDef_pic(), img_pro_pic);
	}

}
