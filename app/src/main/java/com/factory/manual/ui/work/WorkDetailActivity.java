package com.factory.manual.ui.work;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.factory.manual.ui.shouce.ModuleDetailActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class WorkDetailActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.tv_go_work)
    TextView tv_go_work;
    private String id;
    private BaseResultBean baseResultBean;
    private RecordAdapter adapter = new RecordAdapter();
    private View empty_view;

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
        ivBook.setOnClickListener(this);
        tv_go_work.setOnClickListener(this);
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

        tvNowCount.setText("当前进度(" + bean.getNum() + "/" + bean.getNumber() + ")");
//        tvEndDate.setText();
        adapter.setNewData(bean.getDataList());
        if (adapter.getData().size() == 0) {
            adapter.setEmptyView(getEmptyView());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go_work:
            case R.id.iv_book:
                goWork();
                break;
        }
    }

    private String getBookId() {
        if (baseResultBean != null)
            return baseResultBean.getName();
        return "9a1cdcfba6a641719edae9604a6049de";
    }

    private int getTaskCount() {
        if (baseResultBean != null)
            return Integer.parseInt(baseResultBean.getNum());
        return 0;
    }

    private void goWork() {
        String bookId = getBookId();
        int taskCount = getTaskCount();
        if (taskCount == -1 || TextUtils.isEmpty(bookId))
            return;

        ModuleDetailActivity.enter(this, bookId, taskCount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_work_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Contants.REQUSET_DEFAULT_CODE && resultCode == Contants.CODE_REFRESH) {
            getData();
            setResult(Contants.CODE_REFRESH);
        }
    }

    private View getEmptyView() {
        if (empty_view == null) {
            empty_view = getLayoutInflater().inflate(R.layout.layout_work_empty, null);
        }
        return empty_view;
    }
}
