package com.factory.manual.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.Group;


public class GroupAdapter extends BaseQuickAdapter<Group, BaseViewHolder> {
    public GroupAdapter() {
        super(R.layout.item_group);
    }

    @Override
    protected void convert(BaseViewHolder helper, Group item) {
        TextView tv_group_name = helper.getView(R.id.tv_group_name);
        TextView tv_count = helper.getView(R.id.tv_count);
        tv_group_name.setText(item.getGroupName());
        int count = item.getPeoples() == null ? 0 : item.getPeoples().size();
        tv_count.setText("(" + count + ")");
    }
}
