package com.factory.manual.adapter;

import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.HomeItem;
import com.factory.manual.util.ScreenUtils;

import java.util.List;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
    public HomeAdapter(@Nullable List<HomeItem> data) {
        super(R.layout.item_shop_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        LinearLayout ll_text = helper.getView(R.id.ll_text);
        int left = (int) (ScreenUtils.getScreenWidth(mContext) * 0.21f);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ll_text.getLayoutParams();
        layoutParams.setMargins(left, 0, 0, 0);

        helper.setImageResource(R.id.iv_icon, item.icon);
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_content, item.content);
    }
}
