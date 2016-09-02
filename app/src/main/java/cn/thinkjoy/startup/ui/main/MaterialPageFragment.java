package cn.thinkjoy.startup.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseFragment;


/**
 * Created by wangrui on 2016/6/20.
 */
public class MaterialPageFragment extends BaseFragment {
    private static MaterialPageFragment stationFragment;

    private View view ;
    public static final String TAG="MaterialPageFragment";

    public static MaterialPageFragment newInstance(Bundle bundle) {
        stationFragment = new MaterialPageFragment();
        if(bundle!=null){
            stationFragment.setArguments(bundle);
        }

        return stationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view==null){
            view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_material ,null);
            init(view);
        }else{

            ViewGroup viewGroup=(ViewGroup)(view.getParent());
            if(viewGroup!=null){
                viewGroup .removeView(view);
            }
        }

        return view;
    }

    private void init(View view){
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
}
