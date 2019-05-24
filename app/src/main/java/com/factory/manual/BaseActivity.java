package com.factory.manual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.factory.manual.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initStateBar();
        initView();
    }

    protected void initStateBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    public void toastMsg(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public final void startActivity(Class<?> cls) {
        if (cls == null)
            return;
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public final void startActivity(Class<?> cls, int flags) {
        if (cls == null)
            return;
        Intent intent = new Intent(this, cls);
        intent.addFlags(flags);
        startActivity(intent);
    }
}
