package com.factory.manual.adapter;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.BaseResultBean;

import de.hdodenhof.circleimageview.CircleImageView;


public class ApplyAdater extends BaseQuickAdapter<BaseResultBean.DataListBean, BaseViewHolder> {
    public ApplyAdater() {
        super(R.layout.item_apply);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResultBean.DataListBean item) {
        CircleImageView iv_header = helper.getView(R.id.iv_header);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_date = helper.getView(R.id.tv_date);
        LinearLayout ll_iv = helper.getView(R.id.ll_iv);
        ImageView iv_1 = helper.getView(R.id.iv_1);
        ImageView iv_2 = helper.getView(R.id.iv_2);
        ImageView iv_3 = helper.getView(R.id.iv_3);
    }
}
