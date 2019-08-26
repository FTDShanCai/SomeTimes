package com.factory.manual.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.GroupAdapter;
import com.factory.manual.adapter.PeopleAdapter;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import butterknife.BindView;

public class PeoplesActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycle_group)
    RecyclerView recycler_group;
    @BindView(R.id.recycle_peoples)
    RecyclerView recycler_peoples;

    private PeopleAdapter peopleAdapter = new PeopleAdapter();
    private GroupAdapter groupAdapter = new GroupAdapter();

    private LinkedList<String> key = new LinkedList<>();
    private HashMap<String, String> map = new HashMap<>();

    private String parentId = "default";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_peoples;
    }

    public static void enter(Context context, String parentId) {
        if (TextUtils.isEmpty(parentId)) {
            parentId = "default";
        }
        Intent intent = new Intent(context, PeoplesActivity.class);
        intent.putExtra(Contants.B_id, parentId);
        context.startActivity(intent);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            parentId = intent.getStringExtra(Contants.B_id);
        }
    }

    @Override
    public void onBackPressed() {
        if (key.size() == 0) {
            super.onBackPressed();
        } else {
            getData(true);
        }
    }

    @Override
    protected void initView() {
        getIntentData();
        initCommonTitle("组织架构");
        recycler_group.setFocusableInTouchMode(false);
        recycler_group.requestFocus();
        recycler_group.setNestedScrollingEnabled(false);
        recycler_group.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recycler_peoples.setFocusableInTouchMode(false);
        recycler_peoples.requestFocus();
        recycler_peoples.setNestedScrollingEnabled(false);
        recycler_peoples.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recycler_peoples.setAdapter(peopleAdapter);
        recycler_group.setAdapter(groupAdapter);

        peopleAdapter.setOnItemClickListener(this);
        groupAdapter.setOnItemClickListener(this);
        getData(false);
    }

    private void getData(boolean isBack) {

        if (isBack) {
            parentId = key.getLast();
        }
        if (!TextUtils.isEmpty(parentId) && !TextUtils.isEmpty(map.get(parentId))) {
            parseData(map.get(parentId), isBack);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.getPositionList);
        if ("default".equals(parentId)) {
            map.put("parentId", "");
        } else {
            map.put("parentId", parentId);
        }
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        parseData(response, isBack);
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }

    private void parseData(String json, boolean isBack) {
        BaseResultBean bean = gson.fromJson(json, BaseResultBean.class);
        parseData(bean, isBack);
    }


    private void parseData(BaseResultBean bean, boolean isBack) {
        if (bean == null) return;
        if (!isBack) {
            if (!TextUtils.isEmpty(parentId)) {
                map.put(parentId, gson.toJson(bean));
                key.add(parentId);
            }
        } else {
            if (key.size() != 0) {
                key.removeLast();
            }
        }

        ArrayList<BaseResultBean.DataListBean> dataListBeans = bean.getDataList();
        ArrayList<BaseResultBean.List> lists = bean.getList();
        peopleAdapter.setNewData(dataListBeans);
        groupAdapter.setNewData(lists);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Object data = adapter.getData().get(position);
        if (data instanceof BaseResultBean.DataListBean) {
            BaseResultBean.DataListBean people = (BaseResultBean.DataListBean) data;
            toastMsg(people.getNickName());
        }

        if (data instanceof BaseResultBean.List) {
            BaseResultBean.List group = (BaseResultBean.List) data;
            parentId = group.getId();
            getData(false);
        }
    }
}
