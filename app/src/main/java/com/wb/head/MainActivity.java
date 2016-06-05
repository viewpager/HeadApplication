package com.wb.head;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wb.head.bean.Item;
import com.wb.head.wrap.RecylerAdpater;
import com.wb.head.wrap.WrapRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    private RelativeLayout rl_head;
    private LinearLayout point;
    private WrapRecyclerView recycler;
    private LinearLayout ll_headparent;
    private SwipeRefreshLayout refresh;

    private ArrayList<Integer> mList;
    private int[] banner = new int[] {R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3};
    private StartViewpager mStartViewpager;
    private RecylerAdpater mRecylerAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_headparent = (LinearLayout) findViewById(R.id.ll_headparent);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        point = (LinearLayout) findViewById(R.id.index_point);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        recycler = (WrapRecyclerView) findViewById(R.id.recycler);
        refresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        initData();
        MyViewPager mMyViewPager = new MyViewPager(this,viewpager,mList,point);
        mMyViewPager.showViewpager();
        initRecycler();
        initRefresh();
    }

    private void initData(){
        mList = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            mList.add(banner[i]);
        }
    }

    private void initRecycler(){
        ll_headparent.removeView(rl_head);
        recycler.addHeaderView(rl_head);

        TextView textView = new TextView(this);
        textView.setText("我是脚布局");
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,80);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);
        recycler.addFootView(textView);

        mRecylerAdpater = new RecylerAdpater(loadData(0,11));
        recycler.setAdapter(mRecylerAdpater);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        onRecyclerClick();
    }

    private void initRefresh(){
        refresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refresh.setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.white));
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        items.clear();
                        mRecylerAdpater = new RecylerAdpater(loadData(10, 21));
                        recycler.setAdapter(mRecylerAdpater);
                        mRecylerAdpater.notifyDataSetChanged();
                        onRecyclerClick();
                        refresh.setRefreshing(false);
                    }
                }, 4000);
            }
        });
    }

    // 如果连续快速点击，条目的动画会有一个bug，所以使用了一个标记来禁止点击连续点击
    boolean isClick = false;
    private void onRecyclerClick(){
        mRecylerAdpater.setOnItemClickLitener(new RecylerAdpater.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!isClick) {
                    isClick = true;
                    Toast.makeText(MainActivity.this, "点了我position" + position, Toast.LENGTH_SHORT).show();
                    ViewCompat.animate(view).setDuration(200).scaleX(0.9f).scaleY(0.9f).setInterpolator(new CycleInterpolator())
                            .setListener(new ViewPropertyAnimatorListener() {

                                @Override
                                public void onAnimationStart(View view) {
                                }

                                @Override
                                public void onAnimationEnd(View view) {
                                    isClick = false;
                                }

                                @Override
                                public void onAnimationCancel(View view) {
                                }
                            }).withLayer().start();
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "长按了我position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class CycleInterpolator implements android.view.animation.Interpolator {

        private final float mCycles = 0.5f;

        @Override
        public float getInterpolation(final float input) {
            return (float) Math.sin(2.0f * mCycles * Math.PI * input);
        }
    }

    ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList loadData(int start,int end){
        items.clear();
        for (int i = start; i < end; i++) {
            items.add(new Item("Item title :" + i,"This is the Item number :" + i));
        }
        return items;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "onStart启动了");
        mStartViewpager.startViewpager(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStartViewpager.startViewpager(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStartViewpager.startViewpager(false);
    }

    ////////////////////////////////////////////////////////////////
    /////////////////////////回调接口////////////////////////////////
    ////////////////////////////////////////////////////////////////
    public interface StartViewpager{
        void startViewpager(boolean isStart);
    }

    public void setStartViewpager(StartViewpager mStartViewpager){
        this.mStartViewpager = mStartViewpager;
    }

}
