package com.factory.manual.ui.work;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.adapter.ApplyDetailAdapter;
import com.factory.manual.bean.BaseResultBean;

import java.util.ArrayList;

import butterknife.BindView;

public class ApplyDetailActivity extends BaseActivity {

    public static void enter(Activity activity) {
        Intent intent = new Intent(activity, ApplyDetailActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.recycle_view)
    RecyclerView recycler_view;

    ApplyDetailAdapter adapter = new ApplyDetailAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_detail;
    }

    @Override
    protected void initView() {
        initCommonTitle("审批流程");

        recycler_view.setFocusableInTouchMode(false);
        recycler_view.requestFocus();
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);

        ArrayList<BaseResultBean.DataListBean> dataListBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataListBeans.add(new BaseResultBean.DataListBean());
        }

        adapter.setNewData(dataListBeans);
    }
}
