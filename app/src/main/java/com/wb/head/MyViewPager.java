package com.wb.head;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyViewPager implements MainActivity.StartViewpager {

    private Context mContext;
    private ViewPager mViewPager;
    private ArrayList<Integer> mArrayList;
    private LinearLayout mPoint;
    private int currentPoint;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //Log.d("TAG","handleMessage");
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0,2000);
        }
    };

    public MyViewPager(Context context,ViewPager viewPager,ArrayList<Integer> list,LinearLayout point){
        this.mContext = context;
        this.mViewPager = viewPager;
        this.mArrayList = list;
        this.mPoint = point;
        ((MainActivity)mContext).setStartViewpager(MyViewPager.this);
    }

    public void showViewpager(){
        mViewPager.setCurrentItem(mArrayList.size() * 500);
        initPoint();
        mViewPager.setAdapter(new MyViewpagerAdapter());
        initViewPagetEvent();
    }

    private void initPoint(){
        for (int i = 0; i < mArrayList.size(); i++) {
            ImageView pointImage = new ImageView(mContext);
            pointImage.setImageResource(R.drawable.fragment_index_point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin=10;
            if (i > 0){
                params.rightMargin = 10;
                pointImage.setEnabled(false);
            }
            pointImage.setLayoutParams(params);
            mPoint.addView(pointImage);
        }
    }

    private class MyViewpagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mArrayList.size() * 1000;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % mArrayList.size();
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            for (int i = 0; i < mArrayList.size(); i++) {
                Integer integer = mArrayList.get(pos);
                imageView.setImageResource(integer);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void initViewPagetEvent() {

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //Log.d("TAG","过时position：" + position);
                position = position % mArrayList.size();
                mPoint.getChildAt(position).setEnabled(true);
                mPoint.getChildAt(currentPoint).setEnabled(false);
                currentPoint = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            int startX = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacksAndMessages(null);
                        startX = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        handler.sendEmptyMessageDelayed(0, 2000);
                        break;
                    case MotionEvent.ACTION_UP:
                        int endX = (int) event.getX();
                        int moveX = endX - startX;
                        if (Math.abs(moveX) < 5) {
                            // viewpager的点击事件
                            Toast.makeText(mContext,"我是头布局：" + mArrayList.get(currentPoint),Toast.LENGTH_SHORT).show();
                        } else {
                            handler.sendEmptyMessageDelayed(0, 2000);
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void startViewpager(boolean isStart) {
        if (isStart){
            handler.sendEmptyMessageDelayed(0, 2000);
            initViewPagetEvent();
        }else{
            handler.removeCallbacksAndMessages(null);
        }
    }
}
