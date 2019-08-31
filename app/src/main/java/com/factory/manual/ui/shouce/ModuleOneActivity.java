package com.factory.manual.ui.shouce;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.ModuleAdapter;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxSchedulers;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class ModuleOneActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycler_view;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;

    private boolean isSelect;

    private ModuleAdapter adapter;

    public static void enter(Activity context, boolean isGetZiModule) {
        Intent intent = new Intent(context, ModuleOneActivity.class);
        intent.putExtra("isSelect", isGetZiModule);
        context.startActivityForResult(intent, Contants.REQUSET_DEFAULT_CODE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_one;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        if (intent != null) {
            isSelect = intent.getBooleanExtra("isSelect", false);
        }
        initCommonSearchTitle("输入要搜索的模块名", new OnCommonSearchListener() {
            @Override
            public void onSearch(String query) {

            }
        });
        refresh_layout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getList(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getList(true);
            }
        });
        adapter = new ModuleAdapter();
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BaseResultBean.DataListBean bean = (BaseResultBean.DataListBean) adapter.getData().get(position);
                ModuleTwoActivity.enter(ModuleOneActivity.this, bean.getId(), isSelect);
            }
        });
        refresh_layout.setEnableLoadMore(false);
        refresh_layout.autoRefresh();
    }

    public void refreshorLoadMoreComplete(boolean isRefresh) {
        if (isRefresh) {
            refresh_layout.finishRefresh();
        } else {
            refresh_layout.finishLoadMore();
        }
    }

    private int page = 1;
    private int pageCount = 10;

    public void getList(final boolean isRefresh) {
        if (isRefresh) {
            page = 1;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.getCategoryList);
        map.put("nowPage", page + "");
        map.put("pageCount", pageCount + "");

        RetrofitUtil.getInstance().getApi().getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        refreshorLoadMoreComplete(isRefresh);
                        ArrayList<BaseResultBean.DataListBean> list = response.getDataList();
                        if (list == null) list = new ArrayList<>();
                        if (isRefresh) {
                            adapter.setNewData(response.getDataList());
                        } else {
                            adapter.addData(list);
                        }
                        if (page >= response.getTotalPage()) {
                            refresh_layout.setEnableLoadMore(false);
                        } else {
                            refresh_layout.setEnableLoadMore(true);
                            page++;
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        refreshorLoadMoreComplete(isRefresh);
                        toastMsg(msg);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contants.REQUSET_DEFAULT_CODE && resultCode == Contants.CODE_REFRESH && data != null) {
            String id = data.getStringExtra(Contants.B_id);
            if (TextUtils.isEmpty(id) && isSelect) {
                setResult(Contants.CODE_REFRESH, data);
                finish();
            }
        }
    }
}
