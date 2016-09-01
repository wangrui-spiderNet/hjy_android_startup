package cn.thinkjoy.startup.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.widget.recycle.BaseListAdapter;
import cn.thinkjoy.startup.widget.recycle.BasePullViewHolder;
import cn.thinkjoy.startup.widget.recycle.DividerItemDecoration;
import cn.thinkjoy.startup.widget.recycle.PullRecycler;
import cn.thinkjoy.startup.widget.recycle.layoutmanager.ILayoutManager;
import cn.thinkjoy.startup.widget.recycle.layoutmanager.MyLinearLayoutManager;


/**
 * 封装带有recycleView控件的基本页面
 */
public abstract class BaseListActivity<T> extends BaseActivity implements PullRecycler.OnRecyclerRefreshListener {
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        setUpView();
        setUpData();
    }

    protected void setUpView() {
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
    }

    protected void setUpData() {
        setUpAdapter();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    }

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    /**
     * 默认是LinearLayoutManager
     *
     * @return
     */
    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BasePullViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListActivity.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BasePullViewHolder getViewHolder(ViewGroup parent, int viewType);

}
