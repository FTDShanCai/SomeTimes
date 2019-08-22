package com.factory.manual.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.BaseResultBean;

public class PeopleAdapter extends BaseQuickAdapter<BaseResultBean.DataListBean, BaseViewHolder> {
    public PeopleAdapter() {
        super(R.layout.item_people_group);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResultBean.DataListBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_lable = helper.getView(R.id.tv_lable);
        TextView tv_group = helper.getView(R.id.tv_group);
        tv_name.setText(item.getNickName());
        tv_group.setText(item.getPosition());
        String name = item.getNickName();
        if (!TextUtils.isEmpty(name) && name.length() >= 2) {
            tv_lable.setText(name.substring(name.length() - 2));
        } else {
            tv_lable.setText(name);
        }
    }
}
