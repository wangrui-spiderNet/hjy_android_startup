package cn.thinkjoy.startup.ui.usercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.okhttp.callback.OkCallback;
import com.socks.okhttp.parser.OkJsonParser;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseActivity;
import cn.thinkjoy.startup.base.MyApplication;
import cn.thinkjoy.startup.bean.LoginBean;
import cn.thinkjoy.startup.http.api.UserCenterApi;
import cn.thinkjoy.startup.ui.main.MainActivity;
import cn.thinkjoy.startup.util.APPPreferenceUtil;
import cn.thinkjoy.startup.util.ConstantSet;
import cn.thinkjoy.startup.util.RoleSerializableUtil;
import cn.thinkjoy.startup.util.ToastUtil;
import cn.thinkjoy.startup.widget.SearchEditText;

public class LoginActivity extends BaseActivity implements View.OnClickListener, SearchEditText.ISeekPwdListener {

    private ImageView mIvLogo;
    private EditText mEtAccout;
    private SearchEditText mEtPwd;
    private ImageView mIvSeePwd;
    private TextView mTvForgetPwd;
    private Button mBtnLogin;
    private String account = "";
    private String password = "";
    private boolean isShow = false;//密码明文显示标记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideTitle();
    }

    @Override
    protected void findWidgets() {
        mIvLogo = findView(R.id.iv_logo);
        mIvSeePwd = findView(R.id.iv_show_password);
        mEtAccout = findView(R.id.login_editText_personName);
        mEtPwd = findView(R.id.login_editText_password);
        mTvForgetPwd = findView(R.id.tv_forgetpwd);
        mBtnLogin = findView(R.id.btn_login);
    }

    @Override
    protected void initComponent() {
        account = APPPreferenceUtil.getInstance().getAccount();
        password = APPPreferenceUtil.getInstance().getPasswd();
        mEtAccout.setText(account);
        mEtPwd.setText(password);
        initEtPwd();

    }

    @Override
    protected void getIntentData() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnLogin.setOnClickListener(this);
        mIvSeePwd.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);

        mEtPwd.setISeekPwdListener(this);
    }

    /**
     * 密码输入框禁用复制,粘贴功能
     */
    private void initEtPwd() {
        mEtPwd.setLongClickable(false);
        mEtPwd.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mEtPwd.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }
            });
        }
    }

    public void showLoading(String message) {

        showDialog("正在登陆...");
    }

    public void hideLoading() {

        closeDialog();
    }

    public void showErrorMessage(String message) {
        ToastUtil.showToast(mContext, message);
    }

    public void success(LoginBean loginBean) {
        if (loginBean != null && loginBean.getState() == 1) {
            APPPreferenceUtil.getInstance().setAccount(account);
            APPPreferenceUtil.getInstance().setPasswd(password);
            APPPreferenceUtil.getInstance().setPrefBoolean(ConstantSet.IS_REFRESH_CONTACT, true);//登录要刷新通讯录
            try {
                RoleSerializableUtil.serializePerson(mContext, loginBean);
                LoginBean bean = RoleSerializableUtil.deserializePerson(mContext);
                APPPreferenceUtil.getInstance().setPrefBoolean(ConstantSet.ISLOGIN, true);

                if (bean.getItems().size() > 1) {
//                    startActivity(new Intent(mContext, SelectRoleActivity.class));
                } else {

                    loginBean.getItems().get(0).setIsDefault(1);
                    MyApplication.getInstance().setRole(bean.getItems().get(0));
                    RoleSerializableUtil.serializePerson(mContext, loginBean);
                    startActivity(new Intent(mContext, MainActivity.class));
                }
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            APPPreferenceUtil.getInstance().setSession(loginBean.getSession());
        } else {
            ToastUtil.showToast(mContext, loginBean.getMsg());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_forgetpwd:
                startActivity(new Intent(mContext, ForgetpwdActivity.class));
                break;

            case R.id.iv_show_password:

                if (!isShow) {
                    //设置为明文显示
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShow = true;
                    mIvSeePwd.setBackgroundResource(R.drawable.btn_show_pwd);
                    mEtPwd.setSelection(mEtPwd.getText().toString().length());
                } else {
                    //设置为密文显示
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShow = false;
                    mIvSeePwd.setBackgroundResource(R.drawable.btn_notshow_pwd);
                    mEtPwd.setSelection(mEtPwd.getText().toString().length());//光标在密码的后边
                }
                break;
            case R.id.btn_login:
//                account = mEtAccout.getText().toString().trim();
//                password = mEtPwd.getText().toString().trim();
//                if (TextUtils.isEmpty(account)) {
//                    ToastUtil.showToast(mContext, "登录账号不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(password)) {
//                    ToastUtil.showToast(mContext, "密码不能为空");
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    ToastUtil.showToast(mContext, "请输入6-16位登录密码");
//                    return;
//                }

//                doLogin(mContext,account, password);
                startActivity(new Intent(mContext, MainActivity.class));

            default:
                break;
        }
    }

    public void doLogin(Context mContext, String accout, String pwd) {
        UserCenterApi.getInstance().loginMobile(mContext, accout, pwd,
                new OkCallback<LoginBean>(new OkJsonParser<LoginBean>() {
                }) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoading(MyApplication.getAppContext().getString(R.string.load_ing));
                    }

                    @Override
                    public void onSuccess(int code, LoginBean loginBean) {
                        hideLoading();
                        success(loginBean);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        hideLoading();
                    }
                });
    }

    @Override
    public void doseekpwd(boolean show) {
        mIvSeePwd.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
