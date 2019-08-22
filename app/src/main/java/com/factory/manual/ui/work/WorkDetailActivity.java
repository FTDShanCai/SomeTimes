package com.factory.manual.ui.work;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;

public class WorkDetailActivity extends BaseActivity {

    private String id;

    public static void enter(Fragment fragment, String id) {
        Intent intent = new Intent(fragment.getActivity(), WorkDetailActivity.class);
        intent.putExtra(Contants.B_id, id);
        fragment.startActivityForResult(intent, Contants.REQUSET_DEFAULT_CODE);
    }

    public static void enter(Activity activity, String id) {
        Intent intent = new Intent(activity, WorkDetailActivity.class);
        intent.putExtra(Contants.B_id, id);
        activity.startActivityForResult(intent, Contants.REQUSET_DEFAULT_CODE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_detail;
    }

    @Override
    protected void initView() {
        initCommonTitle("工作详情");
        getIntentData();
        getData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(Contants.B_id);
        }
    }

    private void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.getWorkById);
        map.put("id", id);
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }
}
