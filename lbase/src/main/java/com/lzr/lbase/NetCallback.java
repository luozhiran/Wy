package com.lzr.lbase;


import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.plugin.okhttp_lib.okhttp.ItgOk;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class NetCallback implements Callback {


    private final static Executor switchThread = SwitchThread.defaultCallbackExecutor();

    @Override
    public void onFailure(Call call, final IOException e) {
        switchThread.execute(() -> Toast.makeText(ItgOk.instance().getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show());

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        switchThread.execute(() -> onResponse(response));
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
