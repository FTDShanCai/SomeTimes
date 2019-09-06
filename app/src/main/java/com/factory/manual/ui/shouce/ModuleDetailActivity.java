package com.factory.manual.ui.shouce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.PagerAdapter;
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

public class ModuleDetailActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.view_page)
    ViewPager view_page;
    @BindView(R.id.progress)
    ContentLoadingProgressBar progress;
    @BindView(R.id.tv_progress)
    TextView tv_progress;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.iv_web)
    ImageView iv_web;


    private PagerAdapter adapter;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<BaseResultBean.DataListBean> list = new ArrayList<>();

    private boolean isWork = false;
    private int workCount = -1;
    private String taskId = "";

    public static void enter(Context context, String id) {
        Intent intent = new Intent(context, ModuleDetailActivity.class);
        intent.putExtra(Contants.B_id, id);
        context.startActivity(intent);
    }

    public static void enter(Activity context, String id, int count, String taskId) {
        Intent intent = new Intent(context, ModuleDetailActivity.class);
        intent.putExtra(Contants.B_id, id);
        intent.putExtra(Contants.B_Count, count);
        intent.putExtra(Contants.B_State, true);
        intent.putExtra(Contants.B_Task_Id, taskId);
        context.startActivityForResult(intent, Contants.REQUSET_DEFAULT_CODE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_detail;
    }

    @Override
    protected void initView() {
        bookid = getIntent().getStringExtra(Contants.B_id);
        workCount = getIntent().getIntExtra(Contants.B_Count, -1);
        isWork = getIntent().getBooleanExtra(Contants.B_State, false);
        taskId = getIntent().getStringExtra(Contants.B_Task_Id);

        initCommonTitle("模块详情");
        btn_next.setVisibility(View.GONE);

        view_page.addOnPageChangeListener(this);
        btn_next.setOnClickListener(this);
        iv_web.setOnClickListener(this);
        getDetail();
    }

    String bookid;

    private void getDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.getBookById);
        map.put("id", bookid);
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        list.clear();
                        if (response.getDataList() != null) {
                            list.addAll(response.getDataList());
                        }
                        if (list == null || list.size() == 0) {
                            toastMsg("模块详情暂无数据");
                            return;
                        }
                        fragments.clear();
                        int i = 0;
                        for (BaseResultBean.DataListBean bean : list) {
                            fragments.add(ModuleFragment.newInstance(bean, i));
                            i++;
                        }
                        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
                        view_page.setAdapter(adapter);

                        tv_content.setVisibility(View.VISIBLE);
                        tv_progress.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.VISIBLE);

                        progress.setMax(fragments.size());
                        progress.setProgress(1);
                        tv_progress.setText("1/" + fragments.size());
                        checkPage(0);
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }

    private void checkPage(int page) {
        if (isWork && page == workCount) {
            btn_next.setVisibility(View.VISIBLE);
        } else {
            btn_next.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(getCurrentData().getUrl())) {
            iv_web.setVisibility(View.GONE);
        } else {
            iv_web.setVisibility(View.VISIBLE);
        }
        tv_content.setText(list.get(page).getTitle());
        if (page == fragments.size() - 1) {
            btn_next.setText("完成");
        } else {
            btn_next.setText("下一步");
        }
    }

    public boolean isWork() {
        return isWork;
    }

    public int getWorkCount() {
        return workCount;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                OnContent();
                break;
            case R.id.btn_next:
                onBtnCLick();
                break;
            case R.id.iv_web:

                break;
        }
    }

    private BaseResultBean.DataListBean getCurrentData() {
        int page = view_page.getCurrentItem();
        return list.get(page);
    }

    private void OnContent() {
        int page = view_page.getCurrentItem();
        BaseResultBean.DataListBean bean = list.get(page);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(bean.getTitle())
                .setMessage(bean.getContent())
                .setNeutralButton("确定", null).create();
        dialog.show();
    }

    private void onBtnCLick() {
        switch (btn_next.getText().toString()) {
            case "下一步":
                next();
                break;
            case "完成":
                taskFinish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        tv_progress.setText((i + 1) + "/" + fragments.size());
        progress.setProgress(i + 1);
        checkPage(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void next() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.next);
        map.put("id", taskId);
        map.put("uid", AppConfig.uid);
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        int item = view_page.getCurrentItem();
                        item++;
                        view_page.setCurrentItem(item);
                        setResult(Contants.CODE_REFRESH);
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }


    private void taskFinish() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.next);
        map.put("id", taskId);
        map.put("uid", AppConfig.uid);
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
