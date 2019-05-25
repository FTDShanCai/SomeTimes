package com.factory.manual.ui.shouce;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.factory.manual.BaseActivity;
import com.factory.manual.R;
import com.factory.manual.adapter.ModuleAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ModuleOneActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    RecyclerView recycler_view;

    private ModuleAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_one;
    }

    @Override
    protected void initView() {
        initCommonSearchTitle("输入要搜索的模块名", new OnCommonSearchListener() {
            @Override
            public void onSearch(String query) {

            }
        });

        initUrls();
    }

    private void initUrls() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://img0.imgtn.bdimg.com/it/u=358881870,397468584&fm=26&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=2266216882,512704580&fm=26&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1133652693,65474979&fm=26&gp=0.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=2783538960,4051187491&fm=26&gp=0.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=449146835,3295568758&fm=26&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=4203385229,990273276&fm=26&gp=0.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=2743774310,1658831171&fm=26&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3028523000,3594601147&fm=26&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=4214756401,714791584&fm=26&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2138120650,195856557&fm=26&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2425019206,736217391&fm=26&gp=0.jpg");

        adapter = new ModuleAdapter(urls);
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_view.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(ModuleDetailActivity.class);
            }
        });
    }

}
