package com.factory.manual.net;

import android.app.Activity;
import android.content.DialogInterface;

import com.factory.manual.util.ProgressDialogUtil;
import com.factory.manual.util.TipDialog.TipDialog;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RxProgress {
    public static <T> ObservableTransformer<T, T> compose(final Activity activity, String msg) {
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final TipDialog dialog = ProgressDialogUtil.getDialog(activityWeakReference.get(), msg);
        dialog.setCancelable(true);

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(final Disposable disposable) throws Exception {
                        dialog.show();
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                disposable.dispose();
                            }
                        });
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> compose(final Activity activity) {
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final TipDialog dialog = ProgressDialogUtil.getDialog(activityWeakReference.get(), "loading...");
        dialog.setCancelable(true);
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(final Disposable disposable) throws Exception {
                        dialog.show();
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                disposable.dispose();
                            }
                        });
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        };
    }

}
