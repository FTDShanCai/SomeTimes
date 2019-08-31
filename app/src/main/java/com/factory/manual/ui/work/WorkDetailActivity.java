package com.factory.manual.ui.work;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.RecordAdapter;
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

public class WorkDetailActivity extends BaseActivity {

    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_peoples)
    TextView tvPeoples;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_publish_date)
    TextView tvPublishDate;
    @BindView(R.id.tv_task_end_date)
    TextView tvTaskEndDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_now_count)
    TextView tvNowCount;
    @BindView(R.id.iv_book)
    ImageView ivBook;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.tv_task_title)
    TextView tv_task_title;
    private String id;

    private RecordAdapter adapter = new RecordAdapter();

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
        recycleView.setFocusable(false);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setFocusableInTouchMode(false);
        recycleView.requestFocus();
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.setAdapter(adapter);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("");
        }
        adapter.setNewData(strings);
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
                        setDetail(response);
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }

    private void setDetail(BaseResultBean bean) {
        tv_task_title.setText(bean.getTitle());
        tvState.setText(bean.getStatus());
//        tvPeoples.setText();
        tvAddress.setText(bean.getAddress());
        tvPublishDate.setText(bean.getTime());
        tvTaskEndDate.setText(bean.getEndTime());
        tvContent.setText(bean.getContent());
        progress.setMax(Integer.parseInt(bean.getNumber()));
        progress.setProgress(Integer.parseInt(bean.getNum()));
//        tvEndDate.setText();
    }

    
}
