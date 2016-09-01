package cn.thinkjoy.startup.widget.recycle.layoutmanager;

import android.support.v7.widget.RecyclerView;

import cn.thinkjoy.startup.widget.recycle.BaseListAdapter;


/**
 * Created by Stay on 5/3/16.
 * Powered by www.stay4it.com
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}
