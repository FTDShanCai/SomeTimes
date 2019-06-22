package com.factory.manual.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.People;

public class PeopleAdapter extends BaseQuickAdapter<People, BaseViewHolder> {
    public PeopleAdapter() {
        super(R.layout.item_people_group);
    }

    @Override
    protected void convert(BaseViewHolder helper, People item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_lable = helper.getView(R.id.tv_lable);
        TextView tv_group = helper.getView(R.id.tv_group);
        tv_name.setText(item.getPeopleName());
        tv_group.setText(item.getGroupName());
        String name = item.getPeopleName();
        if (!TextUtils.isEmpty(name) && name.length() >= 2) {
            tv_lable.setText(item.getPeopleName().substring(name.length() - 2, name.length()));
        } else {
            tv_lable.setText(name);
        }

    }
}
