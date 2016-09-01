package cn.thinkjoy.startup.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Handler;

import com.socks.okhttp.OkHttpProxy;
import com.socks.okhttp.utils.HostnameVerifier;
import com.socks.okhttp.utils.TrustManager;

import java.io.ByteArrayInputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.security.auth.x500.X500Principal;

import cn.thinkjoy.startup.bean.Role;
import cn.thinkjoy.startup.config.ConfigRead;
import cn.thinkjoy.startup.util.APPPreferenceUtil;
import cn.thinkjoy.startup.util.ActivityManager;
import cn.thinkjoy.startup.util.RoleSerializableUtil;
import okhttp3.OkHttpClient;

/**
 * Created by wangrui on 2016/6/19.
 */
public class MyApplication extends Application {

    private static Context appContext;
    public static MyApplication instance;
    public static String session = "";
    private Role role;
    private Bitmap screenShot;
    private static boolean isrunningMqtt;//判断是否第一次进入或者是否是切换角色进入
    private static ConfigRead config;//平台配置文件对象

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        instance = this;

        OkHttpClient.Builder builder = OkHttpProxy.getInstance().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        //ignore HTTPS Authentication
        builder.hostnameVerifier(new HostnameVerifier());
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new javax.net.ssl.TrustManager[]{new TrustManager()}, new SecureRandom());
            builder.sslSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        OkHttpProxy.setInstance(builder.build());

        APPPreferenceUtil.getInstance();

    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    //截屏
    public void setScreenShot(Bitmap screenShot) {
        this.screenShot = screenShot;
    }

    public Bitmap getScreenShot() {
        return screenShot;
    }


    public synchronized Role getRole() {
        if (role != null) {
            return role;
        } else {
            try {
                role = RoleSerializableUtil.getCurrentRole(appContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return role;
        }
    }

    public void setRole(Role mrole) {
        role = mrole;
    }

    public String getRunningActivityName() {
        return  ActivityManager.getAppManager().currentActivity().getLocalClassName();
    }

    private static Handler handler = new Handler() {
    };

    private static boolean onCompletedStop = true;

    //debug默认签名中含有的信息
    private final static X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");

    //判断是否是debug版本，用来数据库加密和log自动判断,true表示debug版本，false表示release版本
    public boolean isDebuggable(Context ctx) {
        boolean debuggable = false;
        try {
            PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signatures[] = pinfo.signatures;
            for (int i = 0; i < signatures.length; i++) {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
                X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
                // 判断是否含有debug默认的签名信息
                debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
                if (debuggable) {
                    break;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return debuggable;
    }

    /**
     * 得到平台配置文件
     *
     * @return
     */
    public static ConfigRead getConfig() {
        if (null == config) {
            config = ConfigRead.getInstance(appContext);
            config.parsingConfig();
        }
        return config;
    }

}
