package com.factory.manual.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.util.GlideUtil;

public class RecordAdapter extends BaseQuickAdapter<BaseResultBean.DataListBean, BaseViewHolder> {


    public RecordAdapter() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResultBean.DataListBean item) {
        TextView tvContent = helper.getView(R.id.tv_content);
        ImageView iv1 = helper.getView(R.id.iv_1);
        ImageView iv2 = helper.getView(R.id.iv_2);
        ImageView iv3 = helper.getView(R.id.iv_3);
        ConstraintLayout cl = helper.getView(R.id.cl);
        TextView tvDate = helper.getView(R.id.tv_date);
//        getChildState
//        WorkUtil.getChildState(item.getStatus())
        tvContent.setText(item.getName() + item.getContent());
        tvDate.setText(item.getTime());
        switch ( item.getImages().size()) {
            case 0:
                cl.setVisibility(View.GONE);
                break;
            case 1:
                cl.setVisibility(View.VISIBLE);
                iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.INVISIBLE);
                iv3.setVisibility(View.INVISIBLE);
                GlideUtil.load(mContext,item.getImages().get(0),iv1);
                break;
            case 2:
                cl.setVisibility(View.VISIBLE);
                iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.INVISIBLE);
                GlideUtil.load(mContext,item.getImages().get(0),iv1);
                GlideUtil.load(mContext,item.getImages().get(1),iv2);
                break;
            case 3:
                cl.setVisibility(View.VISIBLE);
                iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
                GlideUtil.load(mContext,item.getImages().get(0),iv1);
                GlideUtil.load(mContext,item.getImages().get(1),iv2);
                GlideUtil.load(mContext,item.getImages().get(2),iv3);
                break;
        }

    }
}
