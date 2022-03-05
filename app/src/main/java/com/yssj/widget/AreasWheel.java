package com.yssj.widget;


import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.data.DBService;
import com.yssj.wheelview.CityWheelAdapter;
import com.yssj.wheelview.OnWheelChangedListener;
import com.yssj.wheelview.ProvinceWheelAdapter;
import com.yssj.wheelview.WheelView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


/**
  * 类描述:	地址选择器的自定义布局   
  * 项目名称:  DateSelector   
  * 类名称:   AreasWheel 
  * 创建人:    xhl  
  * 创建时间:  2015-2-5 上午10:11:53     
  * 版本:      v1.0
 */
public class AreasWheel extends LinearLayout {
	private WheelView wv_province;
	private WheelView wv_city;
	public int screenheight;
	private Context context;
	private OnWheelChangedListener provinceChangedListener;
	private CityWheelAdapter cityWheelAdapter;
	private ProvinceWheelAdapter provinceWheelAdapter;
	/**
	 * 城市列表
	 *//*
	private final int[] ARRAY_CITY = new int[] { R.array.beijin_province_item,
			R.array.heibei_province_item, R.array.shandong_province_item,
			R.array.shanghai_province_item, R.array.guangdong_province_item,
			R.array.anhui_province_item, R.array.fujian_province_item,
			R.array.gansu_province_item, R.array.guangxi_province_item,
			R.array.guizhou_province_item, R.array.hainan_province_item,
			R.array.henan_province_item, R.array.heilongjiang_province_item,
			R.array.hubei_province_item, R.array.hunan_province_item,
			R.array.jilin_province_item, R.array.jiangsu_province_item,
			R.array.jiangxi_province_item, R.array.liaoning_province_item,
			R.array.neimenggu_province_item, R.array.ningxia_province_item,
			R.array.qinghai_province_item, R.array.shanxi1_province_item,
			R.array.shanxi2_province_item, R.array.sichuan_province_item,
			R.array.tianjin_province_item, R.array.xizang_province_item,
			R.array.xinjiang_province_item, R.array.yunnan_province_item,
			R.array.zhejiang_province_item, R.array.chongqing_province_item,
			R.array.taiwan_province_item, R.array.hongkong_province_item,
			R.array.aomen_province_item };*/
	
	private List<HashMap<String, String>> provinceList;
	private List<HashMap<String, String>> defaultCityList;
	
	private DBService db;
	
	

	public AreasWheel(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView();
	}

	public AreasWheel(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public AreasWheel(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	private void initView() {
		db = new DBService(context);
		//初始化省份数据
		String sql = "select * from areatbl where NoteType =1 and id not in(32,33,34,35)";
		provinceList = db.query(sql); 
		//初始化 默认城市数据
		sql = "select * from areatbl where AreaKey ="+provinceList.get(0).get("id");
		defaultCityList = db.query(sql);
		
		
		LayoutInflater.from(context).inflate(
				R.layout.province_city_selector_layout, this, true);
		wv_province = (WheelView) findViewById(R.id.wv_province);
		wv_city = (WheelView) findViewById(R.id.wv_city);

		provinceWheelAdapter = new ProvinceWheelAdapter(context, provinceList);
		wv_province.setAdapter(provinceWheelAdapter);
		wv_province.setCyclic(false);
		wv_province.setVisibleItems(5);
		wv_province.setCurrentItem(0);

		cityWheelAdapter = new CityWheelAdapter(context,defaultCityList);
		wv_city.setAdapter(cityWheelAdapter);
		wv_city.setCyclic(false);
		wv_city.setVisibleItems(5);
		wv_city.setCurrentItem(0);

		provinceChangedListener = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				String sql = "select * from areatbl where AreaKey ="+provinceList.get(newValue).get("id");
				List<HashMap<String, String>> listCitys = db.query(sql);
				cityWheelAdapter.setCityList(listCitys);
				wv_city.setAdapter(cityWheelAdapter);
				wv_city.setCurrentItem(0);
			}
		};
		wv_province.addChangingListener(provinceChangedListener);
	}

	/**
	 * 获取省市字符串
	 * 
	 * @return
	 */
	public String getArea() {
		return wv_province.getCurrentItemValue() + " "
				+ wv_city.getCurrentItemValue();
	}
	/**
	 * 获取省份的Id
	 * @return
	 */
	public String getProvinceId(){
		return wv_province.getCurrentItemId();
	}
	/**
	 * 获取城市的Id
	 * @return
	 */
	public String getCityId(){
		return wv_city.getCurrentItemId();
	}

}
