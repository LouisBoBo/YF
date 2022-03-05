package com.yssj.ui.fragment.contributions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ContributionsDialog;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.mypullto.PullToRefreshBase;
import com.yssj.mypullto.PullToRefreshStaggeredGridView;
import com.yssj.mypullto.StaggeredGridView;
import com.yssj.spl.DoublePuBuCommmonFragment;
import com.yssj.spl.PuBuAdapter;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContributionsFragment extends Fragment implements View.OnClickListener{
    private static Context mContext;
    private List<HashMap<String, Object>> mListDatas;
    private static String mJumpFrom;
    private TextView apply_contributions_tv;
    private TextView private_contributions_tv;


    public static ContributionsFragment newInstances(Context context, String jumpFrom) {
        mJumpFrom = jumpFrom;
        mContext = context;

        ContributionsFragment fragment = new ContributionsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contributions, container, false);

        apply_contributions_tv = v.findViewById(R.id.apply_contribution);
        private_contributions_tv = v.findViewById(R.id.private_contribution);

        apply_contributions_tv.setOnClickListener(this);
        private_contributions_tv.setOnClickListener(this);
        return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View view) {
        if(view == apply_contributions_tv){

            // 跳至提交供款
//            SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "commitcontributions");
//            startActivity(new Intent(mContext, CommonActivity.class));
//            ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

            Intent intent = new Intent(mContext, CommitContributionsActivity.class);
            startActivity(intent);
        }else if(view == private_contributions_tv){

            ContributionsDialog dialog = new ContributionsDialog(mContext);
            dialog.show();
        }
    }
}
