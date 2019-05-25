package com.factory.manual.ui.activity;

import android.view.View;

import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initStateBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(false)   //状态栏字体是深色，不写默认为亮色
                .keyboardEnable(false)
                .navigationBarEnable(false);
        mImmersionBar.init();
    }

    @OnClick(R.id.tv_login)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                startActivity(MainActivity.class);
                break;
        }
    }
}
