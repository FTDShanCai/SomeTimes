package com.factory.manual.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.util.GlideUtil;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class ModuleAdapter extends BaseQuickAdapter<BaseResultBean.DataListBean, BaseViewHolder> {
    public ModuleAdapter() {
        super(R.layout.item_module);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseResultBean.DataListBean item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        TextView tv_name = helper.getView(R.id.tv_name);
        GlideUtil.load(mContext, item.getImage(), iv_img);
        tv_name.setText(item.getName());
    }
}
