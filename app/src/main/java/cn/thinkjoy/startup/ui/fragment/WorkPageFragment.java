package cn.thinkjoy.startup.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseFragment;
import cn.thinkjoy.startup.ui.activity.SampleListActivity;


/**
 * Created by wangrui on 2016/6/20.
 */
public class WorkPageFragment extends BaseFragment {

    public static final String TAG="WorkPageFragment";

    private View view;
    private TextView tv_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view==null){
            view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_work_station ,null);

            tv_list = (TextView)view.findViewById(R.id.tv_list);
            tv_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), SampleListActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            ViewGroup viewGroup=(ViewGroup)(view.getParent());
            if(viewGroup!=null){
                viewGroup .removeView(view);
            }
        }

        return view;
    }


    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {

    }
}
