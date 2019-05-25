package com.factory.manual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.factory.manual.util.StatusBarUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {

    protected ImmersionBar mImmersionBar;
    private InputMethodManager mInputManager;
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
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .keyboardEnable(false)
                .navigationBarEnable(false);
        mImmersionBar.init();
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        super.onDestroy();
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



    /**
     * 设置公共title  common_title布局
     *
     * @param title
     */
    public void initCommonTitle(String title) {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        if (toolbar == null)
            return;
        toolbar.setTitle("");
        ImageView iv_back = toolbar.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> onBackPressed());
        TextView tv_title = toolbar.findViewById(R.id.tv_title);
        tv_title.setText(title);
        setSupportActionBar(toolbar);
    }

    /**
     * 设置公共SearchTitle  common_search_title布局
     *
     * @param
     */
    public void initCommonSearchTitle(String hint, OnCommonSearchListener listener) {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        if (toolbar == null)
            return;
        toolbar.setTitle("");
        ImageView iv_back = toolbar.findViewById(R.id.iv_back);
        EditText et_search = toolbar.findViewById(R.id.et_search);
        ImageView iv_delete = toolbar.findViewById(R.id.iv_delete);
        TextView tv_search = toolbar.findViewById(R.id.tv_search);
        View view_line = toolbar.findViewById(R.id.view_line);

        iv_back.setOnClickListener(v -> {
            hideSoftInput(et_search);
            onBackPressed();
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (listener != null) listener.onSearch(et_search.getText().toString());
                }
                return false;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_search.getText().toString())) {
                    iv_delete.setVisibility(View.GONE);
                    tv_search.setVisibility(View.GONE);
                    view_line.setVisibility(View.GONE);
                } else {
                    iv_delete.setVisibility(View.VISIBLE);
                    tv_search.setVisibility(View.VISIBLE);
                    view_line.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tv_search.setOnClickListener(v -> {
            if (listener != null) {
                hideSoftInput(et_search);
                listener.onSearch(et_search.getText().toString());
            }
        });
        iv_delete.setOnClickListener(v -> {
            et_search.getText().clear();
        });
        et_search.setText("");
        et_search.setHint(hint);
        setSupportActionBar(toolbar);
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput(View editText) {
        if (null == mInputManager) {
            mInputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (null != editText) {
            mInputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            editText.clearFocus();
        }
    }

    private void showSoftInput(View editText) {
        if (null == mInputManager) {
            mInputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (null != editText)
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mInputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 200);
    }

    public interface OnCommonSearchListener {
        void onSearch(String query);
    }

}
