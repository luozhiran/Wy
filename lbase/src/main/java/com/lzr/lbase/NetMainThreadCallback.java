package com.lzr.lbase;


import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.plugin.okhttp_lib.okhttp.ItgOk;
import com.plugin.widget.dialog.KProgressHUD;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class NetMainThreadCallback implements Callback {

    KProgressHUD hud;

    public NetMainThreadCallback(@Nullable KProgressHUD hub) {
        this.hud = hub;
        hud.show();
    }

    private final static Executor switchThread = SwitchThread.defaultCallbackExecutor();

    @Override
    public void onFailure(Call call, final IOException e) {
        switchThread.execute(() -> {
                    Toast.makeText(ItgOk.instance().getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    if (hud != null) {
                        hud.dismiss();
                    }
                }
        );

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200) {
            switchThread.execute(() -> {
                onResponse(response);
                if (hud != null)
                    hud.dismiss();
            });
        } else {
            switchThread.execute(() -> {
                Toast.makeText(ItgOk.instance().getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                if (hud != null)
                    hud.dismiss();
            });
        }

    }

    public abstract void onResponse(Response response);


    static class SwitchThread {

        static public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }


        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable r) {
                handler.post(r);
            }
        }
    }
}
