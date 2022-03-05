//package com.yssj.ui.fragment.orderinfo;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.components.Legend.LegendForm;
//import com.github.mikephil.charting.components.Legend.LegendPosition;
//import com.github.mikephil.charting.components.XAxis.XAxisPosition;
//import com.github.mikephil.charting.components.YAxis.AxisDependency;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.github.mikephil.charting.utils.Highlight;
//import com.github.mikephil.charting.utils.ValueFormatter;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//
//public class DailyRebateFragment extends Fragment implements
//		OnChartValueSelectedListener {
//
//	private LineChart mChart;
//	private TextView tv_yesterday_money, tv_yesterday_sum, tv_today, tv_amount,
//			tv_today_sum, tv_yesterday;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View v = inflater.inflate(R.layout.daily_rebate_fragment, container,
//				false);
//		initView(v);
//		return v;
//	}
//
//	private void initView(View v) {
//		tv_yesterday_money = (TextView) v.findViewById(R.id.tv_yesterday_money);
//		tv_yesterday_sum = (TextView) v.findViewById(R.id.tv_yesterday_sum);
//		tv_today = (TextView) v.findViewById(R.id.tv_today);
//		tv_amount = (TextView) v.findViewById(R.id.tv_amount);
//		tv_today_sum = (TextView) v.findViewById(R.id.tv_today_sum);
//
//		tv_yesterday = (TextView) v.findViewById(R.id.tv_yesterday);
//
//		mChart = (LineChart) v.findViewById(R.id.daily_amount_chart);
//		mChart.setOnChartValueSelectedListener(this);
//		// enable value highlighting
//		mChart.setHighlightEnabled(true);
//
//		// enable touch gestures
//		mChart.setTouchEnabled(false);
//
//		mChart.setDescription("");
//
//		mChart.setNoDataText(".");
//		mChart.setDragDecelerationFrictionCoef(10f);
//
//		// enable scaling and dragging
//		mChart.setDragEnabled(false);
//		mChart.setScaleEnabled(false);
//		mChart.setDrawGridBackground(false);
//		mChart.setHighlightPerDragEnabled(true);
//
//		// if disabled, scaling can be done on x- and y-axis separately
//		mChart.setPinchZoom(false);
//
//		// set an alternative background color
//		mChart.setBackgroundColor(Color.WHITE);
//
//		getData();
//
//	}
//
//	private void getData() {
//		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>(
//				getActivity(), 0) {
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.getDailyRebate(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result, Exception e) {
//
//				if(null == e){
//				if (null != result && result.size() > 0) {
//					setData(result, context);
//					
//					tv_today.setText((CharSequence) result.get(
//							result.size() - 1).get("date"));
//					tv_amount.setText("¥"
//							+ new DecimalFormat("#0.00").format(Double
//									.valueOf((String) result.get(
//											result.size() - 1).get("sum"))));
//					tv_today_sum.setText(result.get(result.size() - 1).get(
//							"count")
//							+ "单");
//					if (result.size() >= 2) {
//						tv_yesterday_money.setText("¥"
//								+ new DecimalFormat("#0.00").format(Double
//										.valueOf((String) result.get(
//												result.size() - 2).get("sum"))));
//						tv_yesterday_sum.setText(result.get(result.size() - 2).get(
//								"count")
//								+ "单");
//						tv_yesterday.setText((CharSequence) result.get(
//								result.size() - 2).get("date"));
//					}
//				}
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//
//	private void setData(List<HashMap<String, Object>> result, Context context) {
//
//		ArrayList<String> xVals = new ArrayList<String>();
//		for (int i = 0; i < result.size(); i++) {
//			xVals.add((String) result.get(i).get("date"));
//		}
//		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//
//		for (int i = 0; i < result.size(); i++) {
//			yVals1.add(new Entry(Float.parseFloat((String) result.get(i).get(
//					"sum")), i));
//		}
//
//		// create a dataset and give it a type
//		LineDataSet set1 = new LineDataSet(yVals1, "每日返现");
//		set1.setAxisDependency(AxisDependency.LEFT);
//		set1.setColor(ColorTemplate.getHoloBlue());
//		set1.setCircleColor(Color.GRAY);
//		set1.setLineWidth(2f);
//		set1.setCircleSize(3f);
//		set1.setFillAlpha(65);
//		set1.setFillColor(ColorTemplate.getHoloBlue());
//		set1.setHighLightColor(Color.rgb(244, 117, 117));
//		set1.setDrawCircleHole(false);
//		set1.setDrawCircles(false);
//		// set1.setVisible(false);
//		// set1.setCircleHoleColor(Color.WHITE);
//
//		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
//		dataSets.add(set1); // add the datasets
//
//		// create a data object with the datasets
//		LineData data = new LineData(xVals, dataSets);
//		data.setValueTextColor(Color.GRAY);
//		data.setValueTextSize(9f);
//
//		// set data
//		mChart.setData(data);
//
//		mChart.animateX(2500);
//		Typeface tf = Typeface.createFromAsset(context.getAssets(),
//				"OpenSans-Regular.ttf");
//
//		// get the legend (only possible after setting data)
//		Legend l = mChart.getLegend();
//
//		// modify the legend ...
//		// l.setPosition(LegendPosition.LEFT_OF_CHART);
//		l.setForm(LegendForm.LINE);
//		l.setTypeface(tf);
//		l.setTextSize(11f);
//		l.setTextColor(Color.GRAY);
//		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
//		// l.setYOffset(11f);
//
//		XAxis xAxis = mChart.getXAxis();
//		xAxis.setTypeface(tf);
//		xAxis.setTextSize(9f);
//		xAxis.setTextColor(Color.GRAY);
//		xAxis.setDrawGridLines(false);
//		xAxis.setDrawAxisLine(false);
//		xAxis.setSpaceBetweenLabels(1);
//		xAxis.setPosition(XAxisPosition.BOTTOM);
//
//		YAxis leftAxis = mChart.getAxisLeft();
//		leftAxis.setTypeface(tf);
//		leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//		// leftAxis.setAxisMaxValue(200f);
//		leftAxis.setDrawGridLines(true);
//		leftAxis.setValueFormatter(new ValueFormatter() {
//
//			@Override
//			public String getFormattedValue(float value) {
//				// TODO Auto-generated method stub
//				DecimalFormat decimalFormat = new DecimalFormat("#");
//				String s = decimalFormat.format(value);
//				return s;
//			}
//		});
//
//		YAxis rightAxis = mChart.getAxisRight();
//		rightAxis.setTypeface(tf);
//		rightAxis.setTextColor(Color.RED);
//		// rightAxis.setAxisMaxValue(900);
//		rightAxis.setStartAtZero(false);
//		// rightAxis.setAxisMinValue(-200);
//		rightAxis.setDrawGridLines(false);
//		rightAxis.setEnabled(false);
//	}
//
//	@Override
//	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onNothingSelected() {
//		// TODO Auto-generated method stub
//
//	}
//}
