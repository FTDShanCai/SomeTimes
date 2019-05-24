package com.factory.manual.ui.activity;

import android.view.View;

import com.factory.manual.BaseActivity;
import com.factory.manual.R;

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

    }

    @OnClick(R.id.btn_login)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(MainActivity.class);
                break;
        }
    }
}
