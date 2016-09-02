package cn.thinkjoy.startup.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseActivity;
import cn.thinkjoy.startup.base.MyApplication;
import cn.thinkjoy.startup.bean.ContactsBean;
import cn.thinkjoy.startup.db.ContactsDao;
import cn.thinkjoy.startup.util.APPPreferenceUtil;
import cn.thinkjoy.startup.util.ConstantSet;
import cn.thinkjoy.startup.util.DateUtils;
import cn.thinkjoy.startup.util.JsonUtil;
import cn.thinkjoy.startup.widget.FragmentTabHost;

/**
 * Created by wangrui on 2016/6/22.
 */
public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;

    /**
     * 切换tab配置
     */
    public static Class mFragmentArray[] = {WorkPageFragment.class, MaterialPageFragment.class,
            MePageFragment.class};
    private String mTextArray[] = {"工作台", "资料", "我"};
    private int mImageArray[] = {R.drawable.home_tab, R.drawable.contact_tab, R.drawable.mine_tab};

    private TextView tv_new_msg_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        instance = this;

        initTabHost();
        loadData();
    }

    private void initTabHost() {
        mLayoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        Bundle bundle = new Bundle();
        mTabHost.addTab(mTabHost.newTabSpec(WorkPageFragment.TAG).setIndicator(createIndicatorView(0)), mFragmentArray[0], bundle);
        mTabHost.addTab(mTabHost.newTabSpec(MaterialPageFragment.TAG).setIndicator(createIndicatorView(1)), mFragmentArray[1], bundle);
        mTabHost.addTab(mTabHost.newTabSpec(MePageFragment.TAG).setIndicator(createIndicatorView(2)), mFragmentArray[2], new Bundle());
        mTabHost.getTabWidget().setDividerDrawable(R.color.transparent);

    }

    private View createIndicatorView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tabicon);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_tabname);
        tv_new_msg_count = (TextView) view.findViewById(R.id.tv_new_msg_count);
        textView.setText(mTextArray[index]);

        return view;
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void getIntentData() {

    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                // 请求离线通讯录
                ContactsBean contactsBean = analyticalContactsJson(ConstantSet.test_json);

                if (null != contactsBean) {
                    APPPreferenceUtil.getInstance().setPrefString(ConstantSet.REFRESH_CONTACT_DAY, DateUtils.dateToString(new Date(),
                            ConstantSet.DATE_FORMAT));
                    APPPreferenceUtil.getInstance().setPrefBoolean(ConstantSet.IS_REFRESH_CONTACT, false);
                    // 填充userId
                    if (role != null) {
                        contactsBean.setUserId(role.getUserId());
                    }
                    // 插入离线数据
                    ContactsDao dao = new ContactsDao(MyApplication.getAppContext());
                    dao.insertContactsInfo(contactsBean);

                }
            }
        }).start();
    }

    /**
     * 转成实体类
     *
     * @param responseBody
     * @return
     */
    private ContactsBean analyticalContactsJson(String responseBody) {
        try {
            ContactsBean contactGroupList = JsonUtil.parseObject(responseBody, ContactsBean.class);
            return contactGroupList;
        } catch (Exception e) {
            return null;
        }
    }

}
