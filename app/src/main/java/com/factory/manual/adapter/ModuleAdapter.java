package com.factory.manual.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.util.GlideUtil;

import java.util.List;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class ModuleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ModuleAdapter(@Nullable List<String> data) {
        super(R.layout.item_module, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        GlideUtil.load(mContext, item, iv_img);
    }
}
