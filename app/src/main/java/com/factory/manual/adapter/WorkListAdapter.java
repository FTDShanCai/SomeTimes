package com.factory.manual.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;


public class WorkListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public WorkListAdapter() {
        super(R.layout.item_work_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
