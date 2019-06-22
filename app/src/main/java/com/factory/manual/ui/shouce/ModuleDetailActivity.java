package com.factory.manual.ui.shouce;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.adapter.PagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ModuleDetailActivity extends BaseActivity {

    @BindView(R.id.view_page)
    ViewPager view_page;
    @BindView(R.id.progress)
    ContentLoadingProgressBar progress;
    @BindView(R.id.tv_progress)
    TextView tv_progress;
    @BindView(R.id.btn_next)
    Button btn_next;

    private PagerAdapter adapter;

    private ArrayList<Fragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_detail;
    }

    @Override
    protected void initView() {
        initCommonTitle("模块详情");
        fragments = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            fragments.add(ModuleFragment.newInstance());
        }
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        view_page.setAdapter(adapter);

        progress.setMax(fragments.size());
        progress.setProgress(1);
        view_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tv_progress.setText((i + 1) + "/" + fragments.size());
                progress.setProgress(i + 1);

                if (i == fragments.size() - 1) {
                    btn_next.setText("完成");
                } else {
                    btn_next.setText("下一步");
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (btn_next.getText().toString()) {
                    case "下一步":
                        int item = view_page.getCurrentItem();
                        item++;
                        view_page.setCurrentItem(item);
                        break;
                    case "完成":
                        toastMsg("完成");
                        finish();
                        break;
                }

            }
        });
    }


}
