package com.factory.manual.ui.work;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.bean.SubmitBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.factory.manual.ui.activity.PeoplesActivity;
import com.factory.manual.ui.shouce.ModuleOneActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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

    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.iv_module)
    ImageView iv_module;
    @BindView(R.id.et_title)
    TextView et_title;
    @BindView(R.id.tv_module)
    TextView tv_module;
    @BindView(R.id.tv_peoples)
    TextView tv_peoples;

    private String bookId, date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_work;
    }

    @Override
    protected void initView() {
        initCommonTitle("发布任务");
        tv_submit.setOnClickListener(this);
        iv_module.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        iv_group.setOnClickListener(this);
    }


    private HashMap<String, String> users = new HashMap<>();

    private void submit() {
        String title = et_title.getText().toString();
        String address = et_address.getText().toString();
        String content = et_content.getText().toString();

        if (TextUtils.isEmpty(title)) {
            toastMsg("请输入标题");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            toastMsg("请输入地址");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            toastMsg("请输入内容");
            return;
        }

        if (users.size() == 0) {
            toastMsg("最少需要指定一名人员");
            return;
        }

        if (TextUtils.isEmpty(date)) {
            toastMsg("请选择完成时间");
            return;
        }

        if (TextUtils.isEmpty(bookId)) {
            toastMsg("请选择绑定图册");
            return;
        }
        ArrayList<String> list = new ArrayList<>(users.keySet());
        SubmitBean submitBean = new SubmitBean();
        submitBean.setCmd(CMD.bindUserBook);
        submitBean.setIds(list);
        submitBean.setTitle(title);
        submitBean.setAddress(address);
        submitBean.setTime(date);
        submitBean.setContent(content);
        submitBean.setUid(AppConfig.uid);
        submitBean.setBookId(bookId);

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
            case R.id.tv_date:
                showDateChoiceDialog();
                break;
            case R.id.iv_group:
                PeoplesActivity.enter(this, AppConfig.parentid, true);
                break;
        }
    }

    private DatePickerDialog pickerDialog;

    public void showDateChoiceDialog() {
        if (pickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            pickerDialog = new DatePickerDialog(this, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    date = i + "-" + i1 + "-" + i2;
                    tv_date.setText(i + "年" + i1 + "月" + i2 + "日");
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            pickerDialog.getDatePicker().setMinDate(calendar.getTime().getTime());
        }
        pickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contants.REQUSET_DEFAULT_CODE && resultCode == Contants.CODE_REFRESH && data != null) {//bookid
            bookId = data.getStringExtra(Contants.B_id);
            String bookName = data.getStringExtra(Contants.B_NAME);
            tv_module.setText(bookName);
        } else if (requestCode == Contants.REQUSET_DEFAULT_CODE_101 && resultCode == Contants.CODE_REFRESH && data != null) {//people
            String userid = data.getStringExtra(Contants.B_id);
            String name = data.getStringExtra(Contants.B_NAME);
            if (!TextUtils.isEmpty(userid) && !TextUtils.isEmpty(name) && !users.containsKey(userid)) {
                users.put(userid, name);
                StringBuilder sb = new StringBuilder();
                for (String key : users.keySet()) {
                    sb.append(users.get(key));
                    sb.append(",");
                }
                sb.deleteCharAt(sb.length());
                tv_peoples.setText(sb.toString());
            }
        }
    }
}

