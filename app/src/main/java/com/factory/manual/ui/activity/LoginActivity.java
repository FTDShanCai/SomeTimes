package com.factory.manual.ui.activity;

import android.view.View;
import android.widget.EditText;

import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;

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
                login(et_account.getText().toString(), et_password.getText().toString());
                break;
        }
    }

    private void login(String account, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.userLogin);
        map.put("phone", account);
        map.put("password", password);
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this, "登录中.."))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        toastMsg("登录成功");
                        AppConfig.uid = response.getUid();
                        startActivity(MainActivity.class);
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });

    }
}
