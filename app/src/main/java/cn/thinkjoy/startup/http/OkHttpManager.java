package cn.thinkjoy.startup.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.widget.Toast;

import com.socks.okhttp.OkHttpProxy;
import com.socks.okhttp.callback.OkCallback;
import com.socks.okhttp.listener.DownloadListener;
import com.socks.okhttp.listener.UploadListener;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/**
 * Created by xjliu on 16/6/17.
 */
public class OkHttpManager {
    /**
     * 静态实例
     */
    private static OkHttpManager sOkHttpManager;

    /**
     * okhttpclient实例
     */
    private OkHttpClient mClient;

    /**
     * 因为我们请求数据一般都是子线程中请求，在这里我们使用了handler
     */
    private Handler mHandler;

    /**
     * 构造方法
     */
    private OkHttpManager() {

        mClient = new OkHttpClient();


        /**
         * 初始化handler
         */
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 单例模式  获取OkHttpManager实例
     *
     * @return
     */
    public static OkHttpManager getInstance() {

        if (sOkHttpManager == null) {
            sOkHttpManager = new OkHttpManager();
        }
        return sOkHttpManager;
    }

    //-------------------------同步的方式请求数据--------------------------

    /**
     * 对外提供的get方法,同步的方式
     *
     * @param url 传入的地址
     * @return
     */
    public Response getSync(Context context, String url) {
        //通过获取到的实例来调用内部方法
        return sOkHttpManager.inner_getSync(context,url);
    }

    /**
     * GET方式请求的内部逻辑处理方式，同步的方式
     *
     * @param url
     * @return
     */
    private Response inner_getSync(Context context, String url) {
        Response response = null;
        try {
            response = OkHttpProxy.get()
                    .url(url)
                    .tag(context)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 对外提供的同步获取String的方法
     *
     * @param url
     * @return
     */
    public String getSyncString(Context context,String url) {
        return sOkHttpManager.inner_getSyncString(context,url);
    }


    /**
     * 同步方法
     */
    private String inner_getSyncString(Context context,String url) {
        String result = null;
        try {
            /**
             * 把取得到的结果转为字符串，这里最好用string()
             */
            result = inner_getSync(context,url).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    //-------------------------异步的方式请求数据--------------------------
    public void getAsync(Context context,String url, OkCallback callBack) {
        OkHttpProxy.get()
                .url(url)
                .tag(context)
                .enqueue(callBack);
    }

    public void getAsync(Context context,String url, Callback callBack) {
        OkHttpProxy.get()
                .url(url)
                .tag(context)
                .enqueue(callBack);
    }


    public void getAsync(Context context,String url, Map<String, Objects> params, OkCallback callBack) {
        OkHttpProxy.get()
                .url(url)
                .tag(context)
                .setParams(params)
                .enqueue(callBack);
    }

    public void getAsync(Context context,String url, Map<String, Objects> params, Callback callBack) {
        OkHttpProxy.get()
                .url(url)
                .tag(context)
                .setParams(params)
                .enqueue(callBack);
    }


    //-------------------------提交表单--------------------------

    public void postAsync(Context context,String url, Map<String, Object> params, OkCallback callBack) {
        OkHttpProxy
                .post()
                .url(url)
                .tag(context)
                .setParams(params)
                .addHeader("header", "okhttp")
                .enqueue(callBack);


    }

    public void postAsync(Context context,String url, Map<String, Object> params, Callback callBack) {

        OkHttpProxy
                .post()
                .url(url)
                .tag(context)
                .setParams(params)
                .addHeader("header", "okhttp")
                .enqueue(callBack);
    }

    public void postAsync(Context context,String url, Map<String, Object> params, Map<String, Object> headers, OkCallback callBack) {
        OkHttpProxy
                .post()
                .url(url)
                .tag(context)
                .setParams(params)
                .setheaders(headers)
                .enqueue(callBack);
    }

    public void postAsync(Context context,String url, Map<String, Object> params, Map<String, String> headers, Callback callBack) {

        OkHttpProxy
                .post()
                .url(url)
                .tag(context)
                .setParams(params)
                .setheaders(headers)
                .enqueue(callBack);
    }


    //-------------------------文件下载--------------------------
    public static void downloadAsync(String url, DownloadListener downloadListener) {
        OkHttpProxy.download(url, downloadListener);
    }


    public void uploadFile(Context context, String url, File file, Map<String, String> param, UploadListener uploadListener) {
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Pair<String, File> pair = new Pair("file", file);
        OkHttpProxy
                .upload()
                .url(url)
                .file(pair)
                .tag(context)
                .setParams(param)
                .setWriteTimeOut(20)
                .start(uploadListener);
    }

    public void uploadFile(Context context, String url, File file, UploadListener uploadListener) {
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Pair<String, File> pair = new Pair("file", file);
        OkHttpProxy
                .upload()
                .url(url)
                .tag(context)
                .file(pair)
                .setWriteTimeOut(20)
                .start(uploadListener);
    }

    public void uploadFile(Context context, String url, File file, Map<String, String> param, String token, UploadListener uploadListener) {
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        param.put("token", token);
        Pair<String, File> pair = new Pair("file", file);
        OkHttpProxy
                .upload()
                .url(url)
                .tag(context)
                .file(pair)
                .setParams(param)
                .setWriteTimeOut(20)
                .start(uploadListener);
    }


    public void cancel(Context context)
    {
        OkHttpProxy.cancel(context);
    }
}
