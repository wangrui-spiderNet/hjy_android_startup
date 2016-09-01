package cn.thinkjoy.startup.ui.usercenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.okhttp.callback.OkCallback;
import com.socks.okhttp.parser.OkJsonParser;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseActivity;
import cn.thinkjoy.startup.base.MyApplication;
import cn.thinkjoy.startup.bean.SampleBean;
import cn.thinkjoy.startup.http.api.UserCenterApi;
import cn.thinkjoy.startup.util.ConstantSet;
import cn.thinkjoy.startup.util.IntentUtils;
import cn.thinkjoy.startup.util.StringUtils;
import cn.thinkjoy.startup.util.ToastUtil;
import cn.thinkjoy.startup.widget.SearchEditText;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 忘记密码
 */
public class ForgetpwdActivity extends BaseActivity implements View.OnClickListener ,SearchEditText.ISeekPwdListener{
    private EditText mEtAccout;
    private EditText mEtAuthcode;
    private SearchEditText mEtPwd;
    private ImageView mIvSeePwd;
    private Button mBtnCommit;
    private Button mBtnAuthcode;
    private String account = "";
    private String password = "";
    private String authCode = "";
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private TextView title;
    private Handler mHandler = null;
    private static int count = 59;
    private boolean isShow = false;//密码明文显示标记
    private static int delay = 1000;  //1s
    private static int period = 1000;  //1s
    private static final int UPDATE_TEXTVIEW = 0;
    private static final int UPDATE_STOP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
    }

    @Override
    protected void findWidgets() {
        mIvSeePwd = findView(R.id.iv_show_password);

        mEtAccout = findView(R.id.login_editText_phone);

        mEtAuthcode = findView(R.id.login_editText_authcode);

        mEtPwd = findView(R.id.login_editText_password);

        mBtnAuthcode = findView(R.id.btn_authcode);

        mBtnCommit = findView(R.id.btn_commit);

        title = findView(R.id.tv_common_title);
        mEtPwd.setISeekPwdListener(this);

    }

    @Override
    protected void initComponent() {

        title.setText("找回密码");
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXTVIEW:
                        updateTextView();
                        break;
                    case UPDATE_STOP:
                        mBtnAuthcode.setText("获取验证码");
                        mBtnAuthcode.setClickable(true);
                        break;
                    default:
                        break;
                }
            }
        };
    }
    @Override
    protected void getIntentData() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnCommit.setOnClickListener(this);
        mIvSeePwd.setOnClickListener(this);
        mBtnAuthcode.setOnClickListener(this);
        mEtPwd.setISeekPwdListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_authcode:
                account = mEtAccout.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    ToastUtil.showToast(mContext, "登录账号不能为空");
                    return;
                }
                if (account.length() < 11) {
                    ToastUtil.showToast(mContext, "请输入11位登录账号");
                    return;
                }
                if(!StringUtils.matchPhoneNumber(account)){
                    ToastUtil.showToast(mContext, "请输入正确的手机号码");
                    return;
                }

                doGetAuthCode(mContext, account);
                startTimer();
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
            case R.id.btn_commit:
                authCode = mEtAuthcode.getText().toString().trim();
                account = mEtAccout.getText().toString().trim();
                password = mEtPwd.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    ToastUtil.showToast(mContext, "登录账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(authCode)) {
                    ToastUtil.showToast(mContext, "验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.showToast(mContext, "密码不能为空");
                    return;
                }
                if (account.length() < 11) {
                    ToastUtil.showToast(mContext, "请输入11位登录账号");
                    return;
                }
                if (password.length() < 6) {
                    ToastUtil.showToast(mContext, "请输入6-16位登录密码");
                    return;
                }
                doForgetPwd(mContext,account,authCode, password,0);
                break;
        }
    }


    private void updateTextView() {
        mBtnAuthcode.setText(String.valueOf(count)  + "s后重新获取");
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    sendMessage(UPDATE_TEXTVIEW);
                    if (count == 0) {
                        mBtnAuthcode.setClickable(true);
                        sendMessage(UPDATE_STOP);
                        stopTimer();
                    }

                    count--;
                }
            };
        }

        if (mTimer != null && mTimerTask != null) {
            mBtnAuthcode.setClickable(false);
            mTimer.schedule(mTimerTask, delay, period);
        }
    }

    private void stopTimer() {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        count = 59;
    }

    public void sendMessage(int id) {
        if (mHandler != null) {
            Message message = Message.obtain(mHandler, id);
            mHandler.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    public void successForAuthcode(SampleBean bean) {
        ToastUtil.showToast(mContext,bean.getMsg());
    }

    public void successForCommit(SampleBean bean) {
        ToastUtil.showToast(mContext, bean.getMsg());
        IntentUtils.startAty(this, LoginActivity.class);
    }

    public void showLoading(String mesage) {
        showDialog(mesage);
    }

    public void hideLoading() {
        closeDialog();
    }

    public void showErrorMessage(String message) {
        ToastUtil.showToast(mContext, message);
        mBtnAuthcode.setClickable(true);

        stopTimer();
    }

    @Override
    public void doseekpwd(boolean show) {
        mIvSeePwd.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void doGetAuthCode(Context context, String accout) {
        UserCenterApi.getInstance().getAuthCode(context, accout, new OkCallback<SampleBean>(new OkJsonParser<SampleBean>(){}) {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onStart() {
                super.onStart();
                showLoading("正在发送...");

            }

            @Override
            public void onSuccess(int code, SampleBean bean) {
                hideLoading();
                if(bean.getResultState()== ConstantSet.XXT_CMD_STATE_SUCCESS){
                    successForAuthcode(bean);
                }else {
                    showErrorMessage(bean.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                hideLoading();
            }
        });
    }

    public void doForgetPwd(Context context, String accout, String authcode, String pwd, int type) {
        UserCenterApi.getInstance().doForgetPwd(context,accout, authcode,  pwd, type,
                new OkCallback<SampleBean>(new OkJsonParser<SampleBean>(){}) {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoading(MyApplication.getAppContext().getString(R.string.load_ing));
                    }

                    @Override
                    public void onSuccess(int code, SampleBean bean) {
                        hideLoading();
//                        successForCommit(bean);
                        if(bean.getResultState()== ConstantSet.XXT_CMD_STATE_SUCCESS){

                            successForCommit(bean);
                        }else {
                            showErrorMessage(bean.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        hideLoading();
                    }
                });
    }

}
