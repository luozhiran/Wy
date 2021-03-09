package com.lzr.lbase;


import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plugin.okhttp_lib.okhttp.ItgOk;
import com.plugin.widget.dialog.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class NetMainThreadCallback<T> implements Callback {

    KProgressHUD hud;
    private Object LoginInfoData;
    private Class<?> clz;

    public NetMainThreadCallback(@Nullable KProgressHUD hub, Class<?> clz) {
        this.hud = hub;
        this.clz = clz;
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
                try {
                    String str = response.body().string();
                    JSONObject object = new JSONObject(str);
                    int code = object.optInt("code");
                    String msg = object.optString("msg");
                    String data = object.optString("data");
                    if (code == 200) {
                        T t = JSON.parseObject(data, (Type) clz);
                        onResponse(t,msg);
                    } else {
                        onFailure(code, msg);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    onFailure(call, new IOException(e.getMessage()));
                } finally {
                    if (hud != null)
                        hud.dismiss();
                }
            });
        } else {
            switchThread.execute(() -> {
                Toast.makeText(ItgOk.instance().getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                if (hud != null)
                    hud.dismiss();
            });
        }

    }

    public abstract void onFailure(int code, String error);

    public abstract void onResponse(T t,String msg);


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
