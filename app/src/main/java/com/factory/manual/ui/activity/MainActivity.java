package com.factory.manual.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.adapter.HomeAdapter;
import com.factory.manual.bean.HomeItem;
import com.factory.manual.ui.shouce.ModuleOneActivity;
import com.factory.manual.ui.work.SubmitWorkActivity;
import com.factory.manual.ui.work.WorkListActivity;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @BindView(R.id.tv_peoples)
    TextView tv_peoples;
    @BindView(R.id.tv_submit_work)
    TextView tv_submit_work;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initDatas();

        tv_peoples.setOnClickListener(this);
        tv_submit_work.setOnClickListener(this);
    }

    @Override
    protected void initStateBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(false)
                .keyboardEnable(false)
                .navigationBarEnable(false);
        mImmersionBar.init();
    }

    private void initDatas() {
        ArrayList<HomeItem> list = new ArrayList<>();
        list.add(new HomeItem("知识手册", R.mipmap.ic_shop_home_ddgl, HomeItem.Type.知识手册, "查询日常操作流程"));
        list.add(new HomeItem("任务管理", R.mipmap.ic_shop_home_jsgl, HomeItem.Type.任务管理, "当前部门分配任务"));
        providerShopItems(list);
    }


    private void providerShopItems(ArrayList<HomeItem> list) {
        HomeAdapter adapter = new HomeAdapter(list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getItem(position) instanceof HomeItem) {
                    HomeItem item = (HomeItem) adapter.getItem(position);

                    switch (item.type) {
                        case 知识手册:
                            startActivity(ModuleOneActivity.class);
                            break;
                        case 任务管理:
                            startActivity(WorkListActivity.class);
                            break;
                    }
                }
            }
        });
        recycle_view.setLayoutManager(new GridLayoutManager(this, 2));
        recycle_view.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_peoples:
                PeoplesActivity.enter(this, "");
                break;
            case R.id.tv_submit_work:
                if (TextUtils.isEmpty(AppConfig.parentid)) {
                    toastMsg("您还没有发布任务的权限");
                    return;
                }
                startActivity(SubmitWorkActivity.class);
                break;
        }
    }
}
