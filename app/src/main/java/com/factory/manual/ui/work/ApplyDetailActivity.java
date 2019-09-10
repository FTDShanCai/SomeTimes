package com.factory.manual.ui.work;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.ApplyDetailAdapter;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class ApplyDetailActivity extends BaseActivity implements View.OnClickListener {
    private String id;

    public static void enter(Activity activity, String id) {
        Intent intent = new Intent(activity, ApplyDetailActivity.class);
        intent.putExtra(Contants.B_id, id);
        activity.startActivityForResult(intent, Contants.REQUSET_DEFAULT_CODE);
    }

    @BindView(R.id.tv_refuse)
    TextView tv_refuse;
    @BindView(R.id.tv_apply)
    TextView tv_apply;
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

        if (getIntent() != null) {
            id = getIntent().getStringExtra(Contants.B_id);
        }

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

        tv_refuse.setOnClickListener(this);
        tv_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                apply("1");
                break;
            case R.id.tv_refuse:
                apply("2");
                break;
        }
    }

    private void apply(String state) { //1:同意2：拒绝  审批
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.check);
        map.put("id", id);
        map.put("uid", AppConfig.uid);
        map.put("status", state);
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        setResult(Contants.CODE_REFRESH);
                        finish();
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }
}
