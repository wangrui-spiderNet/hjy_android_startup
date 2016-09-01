package com.socks.okhttp.listener;


import com.socks.okhttp.Model.Progress;

public interface ProgressListener {
    void onProgress(Progress progress);
}