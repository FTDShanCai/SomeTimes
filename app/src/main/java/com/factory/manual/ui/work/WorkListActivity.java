package com.factory.manual.ui.work;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.adapter.PagerAdapter;
import com.factory.manual.ui.fragment.WorkListFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class WorkListActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private PagerAdapter adapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private String[] title = {"全部", "我发布的", "进行中", "暂停中", "审批中", "已完成", "已超期", "部门(进行中)", "部门(已完成)"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_list;
    }

    @Override
    protected void initView() {
        initCommonTitle("工作列表");

        for (String type : title) {
            fragments.add(WorkListFragment.newInstance(type));
        }
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments, title);
        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);
    }
}
