package com.factory.manual.ui.work;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.bean.SubmitBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.factory.manual.ui.shouce.ModuleOneActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;

import butterknife.BindView;

public class SubmitWorkActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_group)
    ImageView iv_group;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.et_address)
    EditText et_address;

    private String bookId;

    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.iv_module)
    ImageView iv_module;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_work;
    }

    @Override
    protected void initView() {
        initCommonTitle("发布任务");
        tv_submit.setOnClickListener(this);
        iv_module.setOnClickListener(this);
    }

    private ArrayList<String> users = new ArrayList<>();

    private void submit() {
        String address = et_address.getText().toString();
        String content = et_content.getText().toString();
        users.add("b4002d2ccfea4137841754bdeec1c85d");
        SubmitBean submitBean = new SubmitBean();
        submitBean.setCmd(CMD.bindUserBook);
        submitBean.setIds(users);
        submitBean.setTitle("啦啦啦");
        submitBean.setAddress(address);
        submitBean.setTime("2019-8-29");
        submitBean.setContent(content);
        submitBean.setUid(AppConfig.uid);
        submitBean.setBookId("9a1cdcfba6a641719edae9604a6049de");

        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(submitBean))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        toastMsg("操作成功");
                        finish();
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                submit();
                break;
            case R.id.iv_module:
                ModuleOneActivity.enter(this, true);
                break;
        }
    }


}
