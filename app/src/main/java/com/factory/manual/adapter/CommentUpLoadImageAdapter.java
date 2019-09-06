package com.factory.manual.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.factory.manual.R;
import com.factory.manual.bean.UpLoadImage;
import com.factory.manual.util.AppUtil;
import com.factory.manual.util.GlideUtil;

import java.io.File;
import java.util.List;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class CommentUpLoadImageAdapter extends BaseQuickAdapter<UpLoadImage, BaseViewHolder> {

    private int img_width = 0;

    public CommentUpLoadImageAdapter(@Nullable List<UpLoadImage> data) {
        super(R.layout.item_comment_upload_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpLoadImage item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        ImageView iv_delete = helper.getView(R.id.iv_delete);
        if (img_width == 0) {
            int screenWidth = AppUtil.getScreenWidth(mContext);
            img_width = (screenWidth - AppUtil.dip2px(mContext, 20)) / 3 - AppUtil.dip2px(mContext, 15);
        }

        iv_img.getLayoutParams().width = img_width;
        iv_img.getLayoutParams().height = img_width;

        if (item.isAdd()) {
            iv_delete.setVisibility(View.GONE);
            iv_img.setImageResource(R.drawable.social_add);
        } else {
            iv_delete.setVisibility(View.VISIBLE);
            iv_img.setImageResource(R.drawable.default_img_200);
            if (!TextUtils.isEmpty(item.getPath())) {
                Glide.with(mContext).load(new File(item.getPath())).into(iv_img);
            } else {
                GlideUtil.load(mContext, item.getUploadurl(), iv_img);
            }
        }


        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getData().size() >= 3 && !getData().get(getData().size() - 1).isAdd()) {
                    getData().remove(helper.getAdapterPosition());
                    UpLoadImage image = new UpLoadImage();
                    image.setAdd(true);
                    getData().add(image);
                    notifyDataSetChanged();
                } else {
                    remove(helper.getAdapterPosition());
                }
            }
        });
    }

    public String getUploadImgs() {
        List<UpLoadImage> loadImages = getData();
        if (loadImages == null || loadImages.size() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (UpLoadImage image :
                    loadImages) {
                sb.append(image.getUploadurl());
                sb.append("|");
            }
            return sb.toString();
        }
    }
}
