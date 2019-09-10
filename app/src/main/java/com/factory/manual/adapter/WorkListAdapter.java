package com.factory.manual.adapter;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.ui.work.WorkUtil;
import com.factory.manual.util.GlideUtil;


public class WorkListAdapter extends BaseQuickAdapter<BaseResultBean.DataListBean, BaseViewHolder> {

    public WorkListAdapter() {
        super(R.layout.item_work_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResultBean.DataListBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_img = helper.getView(R.id.iv_img);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_state = helper.getView(R.id.tv_state);
        TextView tv_peoples = helper.getView(R.id.tv_peoples);
        TextView tv_date = helper.getView(R.id.tv_date);

        tv_title.setText(item.getName());
        GlideUtil.load(mContext, item.getImage(), iv_img);
        tv_content.setText(item.getContent());
        tv_state.setText(WorkUtil.getDetailState(item.getStatus()));

        tv_peoples.setText(item.getName());

    }
}
