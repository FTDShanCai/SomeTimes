package com.factory.manual.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        tv_date.setText(sdf.format(calendar.getTime()));
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
                        AppConfig.parentid = response.getPositionId();
                        AppConfig.positionName = response.getPositionName();
                        AppConfig.name = response.getName();
                        AppConfig.isZG = response.getFlag();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivityForResult(intent, Contants.REQUSET_DEFAULT_CODE);
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contants.REQUSET_DEFAULT_CODE && resultCode == Contants.CODE_REFRESH) {
            finish();
        }
    }
}
