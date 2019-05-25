package com.factory.manual.ui.shouce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.factory.manual.BaseFragment;
import com.factory.manual.R;
import com.factory.manual.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class ModuleFragment extends BaseFragment {
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    private ImageAdapter adapter;
    private List<String> list = new ArrayList<>();

    public static ModuleFragment newInstance() {
        Bundle args = new Bundle();

        ModuleFragment fragment = new ModuleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        adapter = new ImageAdapter(list);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycle_view.setAdapter(adapter);
    }

    @Override
    protected void lazyLoad() {
        if (list.size() == 0) {
            list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1969255128,1774348070&fm=27&gp=0.jpg");
            adapter.notifyDataSetChanged();
        }
    }
}
