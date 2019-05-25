package com.factory.manual;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.ButterKnife;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public abstract class BaseFragment extends Fragment {


    protected abstract @LayoutRes
    int getLayoutId();


    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void lazyLoad();

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;


    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        initViews(savedInstanceState);
        loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootView != null) {
            ButterKnife.bind(this, rootView);
            return rootView;
        }

        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            isUIVisible = true;
            loadData();
        } else {
            isUIVisible = false;
        }
    }

    private void loadData() {
        if (isViewCreated && isUIVisible) {
            lazyLoad();
            isViewCreated = true;
            isUIVisible = true;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initVariable() {
        isViewCreated = false;
        isUIVisible = false;
        rootView = null;
    }

    public void toast(final String text) {
        if (TextUtils.isEmpty(text))
            return;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
