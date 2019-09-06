package com.factory.manual.ui.shouce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.factory.manual.BaseFragment;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.ImageAdapter;
import com.factory.manual.bean.BaseResultBean;

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
    private int index;

    public static ModuleFragment newInstance(BaseResultBean.DataListBean bean, int index) {
        Bundle args = new Bundle();
        args.putSerializable(Contants.B_BEAN, bean);
        args.putSerializable(Contants.B_Index, index);
        ModuleFragment fragment = new ModuleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }

    private BaseResultBean.DataListBean bean;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bean = (BaseResultBean.DataListBean) getArguments().get(Contants.B_BEAN);
        index = getArguments().getInt(Contants.B_Index);
        adapter = new ImageAdapter(list);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycle_view.setAdapter(adapter);
    }

    @Override
    protected void lazyLoad() {
        if (adapter.getData().size() == 0 && bean.getImages() != null && bean.getImages().size() != 0) {
            list.addAll(bean.getImages());
            adapter.notifyDataSetChanged();
        }
    }
}
