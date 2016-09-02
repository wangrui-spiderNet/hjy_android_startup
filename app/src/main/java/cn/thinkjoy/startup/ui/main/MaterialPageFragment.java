package cn.thinkjoy.startup.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseFragment;
import cn.thinkjoy.startup.base.MyApplication;
import cn.thinkjoy.startup.bean.ContactsBean;
import cn.thinkjoy.startup.db.ContactsDao;
import cn.thinkjoy.startup.util.Log;


/**
 * Created by wangrui on 2016/6/20.
 */
public class MaterialPageFragment extends BaseFragment {

    private View view;
    public static final String TAG = "MaterialPageFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_material, null);
            init(view);
        } else {

            ViewGroup viewGroup = (ViewGroup) (view.getParent());
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }

        queryContacts();

        return view;
    }

    private void init(View view) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }

    private void queryContacts() {
        ContactsDao dao = new ContactsDao(MyApplication.getAppContext());
        ContactsBean contactsBean = dao.queryContacts();

        if (contactsBean != null) {
            Log.e("通讯录：" + contactsBean.toString());
        }
    }

}
