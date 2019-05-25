package com.factory.manual.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.factory.manual.R;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class GlideUtil {
    public static void load(Context context, String url, ImageView view) {
        RequestOptions options = new RequestOptions();
        options = options.placeholder(R.mipmap.bg_load)
                .error(R.mipmap.bg_load)
                .centerCrop();
        Glide.with(context).applyDefaultRequestOptions(options).load(url).into(view);
    }

}
