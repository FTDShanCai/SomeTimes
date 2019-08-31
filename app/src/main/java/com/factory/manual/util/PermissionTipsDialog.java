package com.factory.manual.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.SettingService;

/**
 * author: tmgg
 * created on: 2017/8/9 16:48
 * description:
 */
public class PermissionTipsDialog {
    private static PermissionTipsDialog mInstance;
    private final int NEVER_ASK_REQUEST = 400 ;

    public PermissionTipsDialog() {

    }

    public static PermissionTipsDialog getDefault() {
        if (null == mInstance) {
            mInstance = new PermissionTipsDialog();
        }
        return mInstance;
    }

    public void showRationalePermission(final Rationale rationale, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您已拒绝过我们申请的权限，请您同意授权，否则无法正常使用功能！")
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rationale.resume();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rationale.cancel();
            }
        }).create().show();
    }

    public void showNeverAskPermission(final Activity context) {
        if(null==context){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(context.isDestroyed()){
                return;
            }
        }
        final SettingService settingService = AndPermission.defineSettingDialog(context,NEVER_ASK_REQUEST );
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回。")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingService.execute();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settingService.cancel();
            }
        }).create().show();
    }
}
