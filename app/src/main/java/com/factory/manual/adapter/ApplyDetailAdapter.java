package com.factory.manual.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.BaseResultBean;

public class ApplyDetailAdapter extends BaseQuickAdapter<BaseResultBean.DataListBean, BaseViewHolder> {
    public ApplyDetailAdapter() {
        super(R.layout.item_apply_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResultBean.DataListBean item) {
        View view_top = helper.getView(R.id.view_top);
        View view_bottom = helper.getView(R.id.view_bottom);
        view_top.setVisibility(View.INVISIBLE);
        view_bottom.setVisibility(View.INVISIBLE);
        if (helper.getAdapterPosition() == 0) {
            view_bottom.setVisibility(View.VISIBLE);
        } else if (helper.getAdapterPosition() == getData().size() - 1) {
            view_top.setVisibility(View.VISIBLE);
        } else {
            view_top.setVisibility(View.VISIBLE);
            view_bottom.setVisibility(View.VISIBLE);
        }
    }
}
