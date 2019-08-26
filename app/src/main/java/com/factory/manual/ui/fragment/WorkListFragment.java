package com.factory.manual.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.factory.manual.AppConfig;
import com.factory.manual.BaseFragment;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.WorkListAdapter;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxSchedulers;
import com.factory.manual.ui.work.WorkDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class WorkListFragment extends BaseFragment {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.recycle_view)
    RecyclerView recycler_view;

    private WorkListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getString(Contants.B_State);
            type = bundle.getString(Contants.B_TYPE);
        }
        adapter = new WorkListAdapter();
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BaseResultBean.DataListBean bean = (BaseResultBean.DataListBean) adapter.getData().get(position);
                WorkDetailActivity.enter(WorkListFragment.this, bean.getId());
            }
        });

    }

    @SuppressLint("CheckResult")
    @Override
    protected void lazyLoad() {
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.compose())
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        refresh_layout.autoRefresh();
                    }
                });
    }

    public static WorkListFragment newInstance(String status, String type) {
        Bundle args = new Bundle();
        args.putString(Contants.B_State, status);
        args.putString(Contants.B_TYPE, type);
        WorkListFragment fragment = new WorkListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void refreshorLoadMoreComplete(boolean isRefresh) {
        if (isRefresh) {
            refresh_layout.finishRefresh();
        } else {
            refresh_layout.finishLoadMore();
        }
    }

    private String status;
    private String type = "1";
    private int page = 1;
    private int pageCount = 10;

    public void getList(final boolean isRefresh) {
        if (isRefresh) {
            page = 1;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.getWorkList);
        map.put("uid", AppConfig.uid);
        map.put("status", status);//1:进行中2:暂停中3:审批中4:已完成5:已超期  空全部
        map.put("type", type);//1 操作 2、部分 3、我发布

        RetrofitUtil.getInstance().getApi().getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
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
                        toast(msg);
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contants.REQUSET_DEFAULT_CODE && resultCode == Contants.CODE_REFRESH) {
            if (adapter.getData().size() != 0) {
                recycler_view.scrollToPosition(0);
            }
            refresh_layout.autoRefresh();
        }
    }
}
