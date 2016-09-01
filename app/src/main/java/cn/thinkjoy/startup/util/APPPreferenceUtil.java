package cn.thinkjoy.startup.util;

import android.content.Context;
import android.content.SharedPreferences;

import cn.thinkjoy.startup.base.MyApplication;


/**
 * Created by xjliu on 16/6/23.
 */
public class APPPreferenceUtil {

    private static APPPreferenceUtil instance = null;
    private SharedPreferences sp = null;
    private static final String name="application_shared_preference";

    private APPPreferenceUtil() {
        sp = MyApplication.getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private APPPreferenceUtil(Context mContext){
        sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public synchronized static APPPreferenceUtil getInstance(Context mContext) {
        if (instance == null) {
            instance = new APPPreferenceUtil(mContext);
        }
        return instance;
    }

    public APPPreferenceUtil(Context context, String file) {
        sp = context.getSharedPreferences(file, context.MODE_PRIVATE);
    }

    public void setPrefBoolean(final String key,
                               final boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public boolean getPrefBoolean(final String key,
                                  final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }


    public void setPrefString(final String key,
                              final String value) {
        sp.edit().putString(key, value).commit();

    }

    public String getPrefString(String key,
                                final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void setPrefInt(final String key,
                           final int value) {

        sp.edit().putInt(key, value).commit();
    }

    public int getPrefInt(final String key,
                          final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void setPrefFloat(final String key,
                             final float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public float getPrefFloat(final String key,
                              final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void setPreLong(final String key,
                           final long value) {
        sp.edit().putLong(key, value).commit();
    }

    public long getPrefLong(final String key,
                            final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }


    // 用户登录账号
    public void setAccount(String account) {
        sp.edit().putString("accountid", account).commit();
    }

    public String getAccount() {
        return sp.getString("accountid", "");
    }

    // 用户登录session
    public void setSession(String session) {
        sp.edit().putString("session", session).commit();
    }

    public String getSession() {
        return sp.getString("session", "");
    }

    // 用户的密码
    public void setPasswd(String passwd) {
        sp.edit().putString("password", passwd).commit();
    }

    public String getPasswd() {
        return sp.getString("password", "");
    }

    public void setId(String id) {
        sp.edit().putString("id", id).commit();
    }

    public String getId() {
        return sp.getString("id", "");
    }

    // 用户的昵称
    public String getName() {
        return sp.getString("name", "");
    }

    public void setName(String name) {
        sp.edit().putString("name", name).commit();
    }

    // 是否第一次运行本应用
    public void setIsFirst(boolean isFirst) {
        sp.edit().putBoolean("isFirst", isFirst).commit();
    }

    public boolean getisFirst() {
        return sp.getBoolean("isFirst", true);
    }



    public void clearPreference(
    ) {
        sp.edit().clear().commit();
    }
}
