package cn.thinkjoy.startup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseActivity;
import cn.thinkjoy.startup.base.MyApplication;
import cn.thinkjoy.startup.bean.Role;
import cn.thinkjoy.startup.ui.main.MainActivity;
import cn.thinkjoy.startup.ui.usercenter.LoginActivity;
import cn.thinkjoy.startup.util.APPPreferenceUtil;
import cn.thinkjoy.startup.util.ConstantSet;
import cn.thinkjoy.startup.util.NetUtils;
import cn.thinkjoy.startup.util.RoleSerializableUtil;


public class SplashActivity extends BaseActivity {
    private boolean isNetworkAvailable;
    private boolean isFirstLogin;
    private final int SPLASH_DISPLAY_LENGHT = 200; // 延迟三秒
    private Role role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        hideTitle();
    }

    @Override
    protected void findWidgets() {
    }

    @Override
    protected void initComponent() {
        isNetworkAvailable = NetUtils.isNetworkAvailable(mContext);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable) {
                    isFirstLogin = APPPreferenceUtil.getInstance(SplashActivity.this).getPrefBoolean(
                            mContext.getResources().getString(R.string.app_version), true);

                    if (isFirstLogin) {
                        try {//防止删除了应用本地文件没删除
                            RoleSerializableUtil.serializePerson(mContext, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(SplashActivity.this, WelcomeToActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            role = RoleSerializableUtil.getCurrentRole(mContext);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        boolean isLogin = APPPreferenceUtil.getInstance(SplashActivity.this).getPrefBoolean(ConstantSet.ISLOGIN, false);
                        if (isLogin) {
                            if (role == null) {
//                                startActivity(new Intent(mContext, SelectRoleActivity.class));
                            } else {
                                role = MyApplication.getInstance().getRole();  //在次赋值是为了不用区分省份
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        finish();
                    }
                } else {
                    NetUtils.setNetwork(mContext);
                }
            }
        }, SPLASH_DISPLAY_LENGHT);

    }

    @Override
    protected void getIntentData() {

    }
}
