package com.factory.manual.util;

import android.content.Context;

import com.factory.manual.util.TipDialog.TipDialog;


public class ProgressDialogUtil {

    public static TipDialog getDialog(Context context, String loadingStr) {
        TipDialog.Builder builder = new TipDialog.Builder(context).setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord(loadingStr);
        TipDialog dialog = builder.create();
        return dialog;
    }
}
