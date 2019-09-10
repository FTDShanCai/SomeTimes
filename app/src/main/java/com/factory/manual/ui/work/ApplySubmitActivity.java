package com.factory.manual.ui.work;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.factory.manual.AppConfig;
import com.factory.manual.BaseActivity;
import com.factory.manual.BuildConfig;
import com.factory.manual.Contants;
import com.factory.manual.R;
import com.factory.manual.adapter.CommentUpLoadImageAdapter;
import com.factory.manual.api.CMD;
import com.factory.manual.bean.BaseResultBean;
import com.factory.manual.bean.UpLoadImage;
import com.factory.manual.net.NetObserver;
import com.factory.manual.net.RetrofitUtil;
import com.factory.manual.net.RxProgress;
import com.factory.manual.net.RxSchedulers;
import com.factory.manual.util.PermissionTipsDialog;
import com.factory.manual.util.PermissionsConfig;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class ApplySubmitActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.et_reason)
    EditText et_reason;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    private List<UpLoadImage> imageList = new ArrayList<>();
    CommentUpLoadImageAdapter adapter;
    private BottomSheetDialog sheetDialog;
    private String id;

    private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
    private final int REQUEST_CODE_PICK_IMAGE = 200;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_submit;
    }

    @Override
    protected void initView() {
        initCommonTitle("审批提交");

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(Contants.B_id);
        }

        adapter = new CommentUpLoadImageAdapter(imageList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        UpLoadImage upLoadImage = new UpLoadImage();
        upLoadImage.setAdd(true);
        adapter.addData(upLoadImage);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Object o = adapter.getData().get(position);
                if (o instanceof UpLoadImage) {
                    UpLoadImage image = (UpLoadImage) o;
                    if (image.isAdd()) {
                        showBottomSheet();
                    }
                }
            }
        });
        tv_submit.setOnClickListener(this);
    }


    private void showBottomSheet() {

        if (sheetDialog == null) {
            sheetDialog = new BottomSheetDialog(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_bottom_choice_pic, null);
            TextView shop_order_pingjia_tv_paizhao = view.findViewById(R.id.shop_order_pingjia_tv_paizhao);
            TextView shop_order_pingjia_tv_xiangce = view.findViewById(R.id.shop_order_pingjia_tv_xiangce);
            TextView shop_order_pingjia_tv_cancel = view.findViewById(R.id.shop_order_pingjia_tv_cancel);
            shop_order_pingjia_tv_paizhao.setOnClickListener(this);
            shop_order_pingjia_tv_xiangce.setOnClickListener(this);
            shop_order_pingjia_tv_cancel.setOnClickListener(this);
            sheetDialog.setContentView(view);
        }
        sheetDialog.show();
    }

    private File cameraFile;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shop_order_pingjia_tv_cancel:
                if (sheetDialog.isShowing()) {
                    sheetDialog.dismiss();
                }
                break;
            case R.id.shop_order_pingjia_tv_paizhao:
                if (sheetDialog.isShowing()) {
                    sheetDialog.dismiss();
                }
                AndPermission.with(this).requestCode(0x0123).permission(PermissionsConfig.CAMERA, PermissionsConfig.STORAGE)
                        .callback(new PermissionListener() {
                            @Override
                            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                                if (0x0123 == requestCode) {
                                    Uri imageUri = null;
                                    cameraFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        imageUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileProvider", cameraFile);
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                    } else {
                                        imageUri = Uri.fromFile(cameraFile);
                                    }
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
                                    startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
                                }
                            }

                            @Override
                            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                                // 是否有不再提示并拒绝的权限。
                                if (AndPermission.hasAlwaysDeniedPermission(getApplicationContext(), deniedPermissions)) {
                                    PermissionTipsDialog.getDefault().showNeverAskPermission(ApplySubmitActivity.this);
                                } else {
                                    toastMsg("请允许打开拍照权限");
                                }
                            }
                        }).start();
                break;
            case R.id.shop_order_pingjia_tv_xiangce:
                if (sheetDialog.isShowing()) {
                    sheetDialog.dismiss();
                }

                AndPermission.with(this).requestCode(0x0123).permission(PermissionsConfig.STORAGE)
                        .callback(new PermissionListener() {
                            @Override
                            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                                if (0x0123 == requestCode) {
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");// 相片类型
                                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                                }
                            }

                            @Override
                            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                                // 是否有不再提示并拒绝的权限。
                                if (AndPermission.hasAlwaysDeniedPermission(getApplicationContext(), deniedPermissions)) {
                                    PermissionTipsDialog.getDefault().showNeverAskPermission(ApplySubmitActivity.this);
                                } else {
                                    toastMsg("请允许打开相关权限");
                                }
                            }
                        }).start();

                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    private void submit() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cmd", CMD.report);
        map.put("id", id);
        map.put("uid", AppConfig.uid);
        map.put("image", adapter.getUploadImgs());
        RetrofitUtil.getInstance().getApi()
                .getData(gson.toJson(map))
                .compose(RxSchedulers.compose())
                .compose(RxProgress.compose(this))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new NetObserver<BaseResultBean>() {
                    @Override
                    public void onSuccess(BaseResultBean response) {
                        toastMsg("提交成功");
                        setResult(Contants.CODE_REFRESH);
                        finish();
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMsg(msg);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        options.size = 1024 * 150;
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            Tiny.getInstance().source(uri).asFile().withOptions(options).compress(new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile) {
                    //return the compressed file path
                    if (isSuccess) {
                        File file = new File(outfile);
                        uploadImg(file);
                    }
                }
            });
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA && resultCode == Activity.RESULT_OK) {
            Tiny.getInstance().source(cameraFile).asFile().withOptions(options).compress(new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile) {
                    if (isSuccess) {
                        File file = new File(outfile);
                        uploadImg(file);
                    } else {
                        File file = new File(cameraFile.getPath());
                        uploadImg(file);
                    }
                }
            });
        }
    }

    private void uploadImg(File cameraFile) {

//        addimg

        UpLoadImage image = new UpLoadImage();
        image.setPath(cameraFile.getPath());
        image.setUploadurl("1");
        if (adapter.getData().size() >= 3) {
            imageList.remove(adapter.getData().size() - 1);
            imageList.add(image);
            this.adapter.notifyDataSetChanged();
        } else {
            this.adapter.addData(adapter.getData().size() - 1, image);
        }
    }
}
