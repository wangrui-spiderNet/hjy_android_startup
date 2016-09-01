package cn.thinkjoy.startup.base;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.bean.Role;
import cn.thinkjoy.startup.http.OkHttpManager;
import cn.thinkjoy.startup.util.APPPreferenceUtil;
import cn.thinkjoy.startup.util.ActivityManager;
import cn.thinkjoy.startup.util.DensityUtil;
import cn.thinkjoy.startup.util.DialogUtil;
import cn.thinkjoy.startup.util.Log;

/**
 * Created by wangrui on 2016/6/22.
 */
public abstract class BaseActivity  extends FragmentActivity {

    public static Activity instance;
    public static Role role;
    private String session;
    public static final String formatter = "%s_%s_%s".replaceAll("_", "::");
    private String runningActivityName;
    public Context mContext;
    protected DisplayMetrics metric;
    protected int screenWidth;
    protected int screenHeight;

    /**
     * 标题栏标题
     */
    public TextView title;

    /**
     * 中间内容区域的容器
     */
    public LinearLayout base_content;
    /**
     * 中间内容区域的布局
     */
    private View contentView;
    /**
     * FrameLayout
     */
    public FrameLayout framelayout_root;
    /**
     * 标题栏根布局
     */
    public RelativeLayout rl_common_title;

    /**
     * 标题栏右边按钮
     */
    public TextView tv_right_text;

    /**
     * 返回按钮
     */
    public ImageView image_back;
    /**
     * 标题右侧图标
     */
    public ImageView image_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

        int sdkInt = Build.VERSION.SDK_INT;
        instance = this;

        // api15 以上打开硬件加速
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            if (!getComponentName().getClassName().equals("cn.thinkjoy.startup.base.BaseActivity")) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        }

        if (savedInstanceState != null) {
            role = (Role) savedInstanceState.getSerializable("role");
            session = savedInstanceState.getString("session");
            APPPreferenceUtil.getInstance().setSession(session);
            ((MyApplication) this.getApplication()).setRole(role);
        }

        mContext = this;
        // 添加Activity到堆栈
        ActivityManager.getAppManager().addActivity(this);

        screenWidth = DensityUtil.getScreenW(mContext);
        screenHeight =DensityUtil.getScreenH(mContext);

        super.setContentView(R.layout.activity_base_layout);

        this.init();
        runningActivityName = MyApplication.getInstance().getRunningActivityName();
        Log.i("currentActivity:", "当前所在的Activity为:" + runningActivityName);
    }

    /**
     * 设置内容区域
     *
     * @param resId 资源文件id
     */
    @Override
    public void setContentView(int resId) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contentView = inflater.inflate(resId, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        this.contentView.setLayoutParams(layoutParams);
        if (null != this.base_content) {
            this.base_content.addView(this.contentView);
        }

        getIntentData();
        findWidgets();
        initComponent();
        initListener();
        initHandler();
        asyncRetrive();
    }

    private void init() {
        this.rl_common_title = (RelativeLayout) findViewById(R.id.rl_common_title);
        this.image_back = (ImageView) findViewById(R.id.image_back);
        this.image_right = (ImageView) findViewById(R.id.common_right);
        this.title = (TextView) findViewById(R.id.tv_common_title);
        this.tv_right_text = (TextView) findViewById(R.id.tv_right_text);
        this.base_content = (LinearLayout) findViewById(R.id.base_content);
        this.framelayout_root = (FrameLayout) findViewById(R.id.framelayout_root);
        role=MyApplication.getInstance().getRole();
    }

    /**
     * 初始化控件
     */
    protected abstract void findWidgets();

    /**
     * 初始化控件数据
     */
    protected abstract void initComponent();

    /**
     * 初始化数据
     */
    protected abstract void getIntentData();

    @Override
    public void onBackPressed() {
        if (DialogUtil.isProgressDialogShowing()) {
            DialogUtil.closeProgressDialog();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpManager.getInstance().cancel(mContext);
        // 结束Activity&从堆栈中移除
        closeDialog();
        ActivityManager.getAppManager().finishActivity(this);
    }

    /**
     * 初始化控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 初始化Listener，子类根据需要自行重写
     */
    protected void initListener() {
        return;
    }

    /**
     * 初始化Handler，子类根据需要自行重写
     */
    protected void initHandler() {
        return;
    }


    /**
     * 异步查询网络数据，子类根据需要自行重写
     */
    protected void asyncRetrive() {
        return;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (DialogUtil.isProgressDialogShowing()) {
                DialogUtil.closeProgressDialog();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void showDialog(String string) {
        DialogUtil.showProgressDialog(this, string);
        DialogUtil.setDialogCancelable(true);
    }

    public void closeDialog() {
        DialogUtil.closeProgressDialog();
    }

    public void back(View v) {
        finish();
    }

    /**
     * 实现点击空白处，软键盘消失事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), /*right = left + v.getWidth();*/ right =screenWidth;
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
