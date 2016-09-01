package cn.thinkjoy.startup.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.BaseActivity;
import cn.thinkjoy.startup.ui.usercenter.LoginActivity;
import cn.thinkjoy.startup.util.APPPreferenceUtil;
import cn.thinkjoy.startup.util.ConstantSet;


public class WelcomeToActivity extends BaseActivity {

    //    private TextView welcome_go;
    private ImageView introduce;
    private ViewPager viewpager;
    private ImageAdapter imageadapter;
    private ArrayList<ImageView> images;
    private String type = "1";
    private int fromPage;
    private EdgeEffectCompat rightEdge;

    @Override
    protected void findWidgets() {
        introduce = (ImageView) this.findViewById(R.id.image_tiaoguo);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        if (screenWidth > 720) {
            lp.setMargins(0, 5, 0, 140);
        } else if (screenWidth > 480) {
            lp.setMargins(0, 5, 0, 90);
        } else {
            lp.setMargins(0, 5, 0, 40);
        }
//        welcome_go.setLayoutParams(lp);

        // 图片
        final int[] imageResID = new int[]{R.drawable.welcome_pic0001, R.drawable.welcome_pic0002, R.drawable.welcome_pic0003, R.drawable.welcome_pic0004};

        images = new ArrayList<ImageView>();

        for (int i = 0; i < imageResID.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(mContext).load(imageResID[i]).into(imageView);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            images.add(imageView);
        }

        imageadapter = new ImageAdapter();
        this.viewpager = (ViewPager) this.findViewById(R.id.viewpager);
        initViewPager();
        viewpager.setAdapter(imageadapter);

        viewpager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int arg0) {
//                if (arg0 == imageResID.length-1) {
//                    APPPreferenceUtil.getInstance().setPrefBoolean(mContext.getResources().getString(R.string.app_version), false);
//                    if(fromPage!=ConstantSet.SETTING){
//
//                        startActivity(new Intent(mContext, LoginActivity.class));
//                    }
//                    finish();
////                    welcome_go.setVisibility(View.VISIBLE);
//                } else {
////                    welcome_go.setVisibility(View.GONE);
//                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
                if (rightEdge != null && !rightEdge.isFinished()) {//到了最后一张并且还继续拖动，出现蓝色限制边条了
                    if (fromPage != ConstantSet.SETTING) {
                        APPPreferenceUtil.getInstance().setPrefBoolean(mContext.getResources().getString(R.string.app_version), false);
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                    finish();
                }
            }
        });

        this.introduce.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                APPPreferenceUtil.getInstance().setPrefBoolean(mContext.getResources().getString(R.string.app_version), false);
                if (fromPage != ConstantSet.SETTING) {

                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                finish();
            }
        });
    }

    @Override
    protected void initComponent() {
        String TYPE = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(TYPE) && !"1".equals(TYPE)) {
            type = "2";
        }

        introduce.setVisibility(View.VISIBLE);

        fromPage = getIntent().getIntExtra(ConstantSet.FROMPAGE, 100);

    }

    @Override
    protected void getIntentData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_to_activity);
        hideTitle();
    }

    protected void goBack() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.disappear, R.anim.appear);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        // 指定本次页面( arg1 )将要显示的内容与当前页面( arg0 )的内容是否一致
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0.equals(arg1);
        }

        // 添加页面
        @Override
        public Object instantiateItem(ViewGroup viewpager, int position) {
            viewpager.addView(images.get(position));
            return images.get(position);
        }

        // 销毁页面
        @Override
        public void destroyItem(ViewGroup viewpager, int position, Object object) {
            // super.destroyItem(container, position, object);
            ((ViewPager) viewpager).removeView((View) object);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    private void initViewPager() {
        try {
            Field rightEdgeField = viewpager.getClass().getDeclaredField("mRightEdge");
            if (rightEdgeField != null) {
                rightEdgeField.setAccessible(true);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewpager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}