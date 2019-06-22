package com.factory.manual.ui.work;

import com.factory.manual.BaseActivity;
import com.factory.manual.R;

public class WorkDetailActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_detail;
    }

    @Override
    protected void initView() {
        initCommonTitle("工作详情");
    }
}
