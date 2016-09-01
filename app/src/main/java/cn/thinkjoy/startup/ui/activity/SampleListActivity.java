package cn.thinkjoy.startup.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseListActivity;
import cn.thinkjoy.startup.bean.FoundBean;
import cn.thinkjoy.startup.widget.recycle.BasePullViewHolder;
import cn.thinkjoy.startup.widget.recycle.PullRecycler;
import cn.thinkjoy.startup.widget.recycle.layoutmanager.ILayoutManager;
import cn.thinkjoy.startup.widget.recycle.layoutmanager.MyGridLayoutManager;
import cn.thinkjoy.startup.widget.recycle.layoutmanager.MyLinearLayoutManager;
import cn.thinkjoy.startup.widget.recycle.layoutmanager.MyStaggeredGridLayoutManager;

/**
 * 示例代码
 */
public class SampleListActivity extends BaseListActivity<FoundBean> {

    private int random;
    private int page = 1;

    @Override
    protected void setUpData() {
        super.setUpData();
        recycler.setRefreshing();
    }

    @Override
    protected BasePullViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    protected ILayoutManager getLayoutManager() {
        random = new Random().nextInt(3);
        switch (random) {
            case 0:
                return new MyLinearLayoutManager(getApplicationContext());
            case 1:
                return new MyGridLayoutManager(getApplicationContext(), 3);
            case 2:
                return new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        return super.getLayoutManager();
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        if (random == 0) {
            return super.getItemDecoration();
        } else {
            return null;
        }
    }

    @Override
    public void onRefresh(final int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }

        /**
         * 请求网络数据
         */
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            mDataList.clear();
        }

        recycler.enableLoadMore(true);
        /**
         * 假数据
         */
        for (int i = 0; i < 10; i++) {
            FoundBean foundBean = new FoundBean();
            foundBean.setUrl("http://www.baidu.cn--" + i);
            mDataList.add(foundBean);
        }

        adapter.notifyDataSetChanged();
        recycler.onRefreshCompleted();
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

    class SampleViewHolder extends BasePullViewHolder {

        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.tv_sample);
        }

        @Override
        public void onBindViewHolder(int position) {
            mSampleListItemLabel.setText(mDataList.get(position).getUrl());
        }

        @Override
        public void onItemClick(View view, int position) {

        }

    }
}
