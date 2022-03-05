//package com.yssj.ui.activity.testfile;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.pubu.PubuFragment;
//import com.yssj.ui.activity.main.SignGroupShopActivity;
//import com.yssj.ui.base.BaseFragment;
//import com.yssj.utils.SharedPreferencesUtil;
//
//public class TestFragment extends BaseFragment {
//    private static Context mContext;
//    private static String mJumpFrom;
//
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_test_test, container, false);
//
//
//        TextView textView = view.findViewById(R.id.testview);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, TestActivity.class);
//                startActivity(intent);
//            }
//        });
//        return view;
//
//    }
//
//    public static TestFragment newInstances(Context context, String jumpFrom) {
//        mJumpFrom = jumpFrom;
//        mContext = context;
//
//        TestFragment fragment = new TestFragment();
//        return fragment;
//    }
//
//    @Override
//    public View initView() {
//
//
//
//        return null;
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}
