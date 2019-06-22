package com.factory.manual.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.adapter.GroupAdapter;
import com.factory.manual.adapter.PeopleAdapter;
import com.factory.manual.bean.Group;
import com.factory.manual.bean.People;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class PeoplesActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycle_group)
    RecyclerView recycler_group;
    @BindView(R.id.recycle_peoples)
    RecyclerView recycler_peoples;

    private People people;

    private PeopleAdapter peopleAdapter = new PeopleAdapter();
    private GroupAdapter groupAdapter = new GroupAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_peoples;
    }

    @Override
    protected void initView() {
        initCommonTitle("组织架构");
        initDatas();

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

        peopleAdapter.setNewData(people.getPeoples());

        ArrayList<Group> groups = new ArrayList<>();
        for (People p : people.getPeoples()) {
            Group group = new Group();
            group.setGroupName(p.getGroupName());
            groups.add(group);
        }
        groupAdapter.setNewData(groups);
    }

    private void initDatas() {

        people = new People("河南奇德网络有限公司", "奇德");

        People people1_1 = new People("供应中心", "刘聪");
        People people1_1_1 = new People("油品部", "常青");
        People people1_1_2 = new People("金融保险部", "王海玲");
        People people1_1_3 = new People("旅游资源部", "陈凯亮");
        people1_1.setPeoples(initList(people1_1_1, people1_1_2, people1_1_3));

        People people1_2 = new People("营销中心", "杨小书");
        People people1_2_1 = new People("十八地市分公司", "杨晓");
        People people1_2_2 = new People("营销管理中心", "刘德华");
        people1_2.setPeoples(initList(people1_2_1, people1_2_2));

        People people1_3 = new People("互联网中心", "张刘林");
        People people1_3_1 = new People("产品部", "路飞1");
        People people1_3_2 = new People("产品部", "路飞2");
        People people1_3_3 = new People("产品部", "路飞3");
        People people1_3_4 = new People("产品部", "路飞4");
        People people1_3_5 = new People("产品部", "路飞5");

        People people1_3_6 = new People("设计部", "索隆1");
        People people1_3_7 = new People("设计部", "索隆2");
        People people1_3_8 = new People("设计部", "索隆3");
        People people1_3_9 = new People("设计部", "索隆4");
        People people1_3_10 = new People("设计部", "索隆5");

        People people1_3_11 = new People("运营中心", "山治1");
        People people1_3_12 = new People("运营中心", "山治2");
        People people1_3_13 = new People("运营中心", "山治3");

        people1_3.setPeoples(initList(people1_3_1, people1_3_2, people1_3_3, people1_3_4, people1_3_5, people1_3_6, people1_3_7
                , people1_3_8, people1_3_9, people1_3_10, people1_3_11, people1_3_12, people1_3_13));

        people.setPeoples(initList(people1_1, people1_2, people1_3));
    }

    private ArrayList<People> initList(People... datas) {
        ArrayList<People> peoples = new ArrayList<>();
        peoples.addAll(Arrays.asList(datas));
        return peoples;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Object data = adapter.getData().get(position);
        if (data instanceof People) {
//            People people = (People) data;
        }

        if (data instanceof Group) {
//            Group group = (Group) data;
//            if (group.getPeoples() != null && group.getPeoples().size() != 0)
//                peopleAdapter.setNewData(group.getPeoples());
        }

    }
}
