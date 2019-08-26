package com.factory.manual.ui.shouce;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.factory.manual.BaseActivity;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.PagerAdapter;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.HashMap;

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
    @BindView(R.id.tv_content)
    TextView tv_content;

    private PagerAdapter adapter;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<BaseResultBean.DataListBean> list = new ArrayList<>();

    public static void enter(Context context, String id) {
        Intent intent = new Intent(context, ModuleDetailActivity.class);
        intent.putExtra(Contants.B_id, id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_detail;
    }

    @Override
    protected void initView() {
        bookid = getIntent().getStringExtra(Contants.B_id);
        initCommonTitle("模块详情");
        view_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tv_progress.setText((i + 1) + "/" + fragments.size());
                progress.setProgress(i + 1);
                checkPage(i);
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

        getDetail();
    }

    String bookid;

    private void getDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.getBookById);
        map.put("id", bookid);
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        list.clear();
                        if (response.getDataList() != null) {
                            list.addAll(response.getDataList());
                        }
                        if (list == null || list.size() == 0) {
                            toastMsg("模块详情暂无数据");
                            return;
                        }
                        fragments.clear();
                        for (BaseResultBean.DataListBean bean : list) {
                            fragments.add(ModuleFragment.newInstance(bean));
                        }
                        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
                        view_page.setAdapter(adapter);
                        progress.setMax(fragments.size());
                        progress.setProgress(1);
                        tv_progress.setText("1/" + fragments.size());
                        checkPage(0);
                    }

                    @Override
                    public void onFail(String msg) {
                    }
                });
    }

    private void checkPage(int page) {
        tv_content.setText(list.get(page).getTitle());
        if (page == fragments.size() - 1) {
            btn_next.setText("完成");
        } else {
            btn_next.setText("下一步");
        }
    }
}
