package com.factory.manual.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.factory.manual.BaseFragment;
import com.factory.manual.R;
import com.factory.manual.adapter.WorkListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

public class WorkListFragment extends BaseFragment {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.recycle_view)
    RecyclerView recycler_view;

    private WorkListAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        adapter = new WorkListAdapter();
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        adapter.setNewData(list);
    }

    @Override
    protected void lazyLoad() {

    }

    public static WorkListFragment newInstance(String type) {
        Bundle args = new Bundle();
        WorkListFragment fragment = new WorkListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
