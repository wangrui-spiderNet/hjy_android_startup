package cn.thinkjoy.startup.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import cn.thinkjoy.startup.bean.Role;
import cn.thinkjoy.startup.http.OkHttpManager;
import cn.thinkjoy.startup.util.DialogUtil;

/**
 * fragment基类
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public Context mContext;
    public BaseActivity mActivity;//依附的activity
    /**
     * 当前角色
     */
    public Role role;
    protected View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        role = MyApplication.getInstance().getRole();

    }

    protected View createView(View view) {
        this.mView = view;
        findWidgets();
        initComponent();
        initListener();
        initHandler();
        asyncRetrive();
        return mView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        createView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity  = (BaseActivity) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpManager.getInstance().cancel(mContext);
    }

    /**
     * 初始化控件
     */
    protected abstract void findWidgets();

    /**
     * 初始化控件数据
     */
    protected abstract void initComponent();

    @SuppressWarnings("unchecked")
    protected <T> T findView(int id) {
        return (T) mView.findViewById(id);
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


    public void showDialog(Context context, String string) {
        DialogUtil.showProgressDialog(context, string);
        DialogUtil.setDialogCancelable(true);
    }

    public void closeDialog() {
        DialogUtil.closeProgressDialog();
    }

}
