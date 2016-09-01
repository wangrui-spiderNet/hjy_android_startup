package com.socks.okhttp.listener;

import android.os.Handler;

import com.socks.okhttp.Model.Progress;
import com.socks.okhttp.handler.ProgressHandler;
import com.socks.okhttp.handler.UIHandler;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public abstract class UploadListener implements ProgressListener, Callback, UIProgressListener {

    private final Handler mHandler = new UIHandler(this);
    private boolean isFirst = true;

    public void onResponse(final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(response);
            }
        });
    }

    public void onFailure(Request request, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(e);
            }
        });
    }

    public abstract void onSuccess(Response response);

    public abstract void onFailure(Exception e);

    @Override
    public void onProgress(Progress progress) {

        if (!isFirst) {
            isFirst = true;
            mHandler.obtainMessage(ProgressHandler.START, progress)
                    .sendToTarget();
        }

        mHandler.obtainMessage(ProgressHandler.UPDATE,
                progress)
                .sendToTarget();

        if (progress.isFinish()) {
            mHandler.obtainMessage(ProgressHandler.FINISH,
                    progress)
                    .sendToTarget();
        }
    }

    public abstract void onUIProgress(Progress progress);

    public void onUIStart() {
    }

    public void onUIFinish() {
    }
}
