package com.factory.manual.ui.work;

import com.factory.manual.BaseActivity;
import com.factory.manual.R;

public class SubmitWorkActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_work;
    }

    @Override
    protected void initView() {
        initCommonTitle("发布任务");
    }
}
