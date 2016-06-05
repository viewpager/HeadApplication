package com.wb.head.wrap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 * 将recycle进行封装
 */
public class WrapRecyclerView extends RecyclerView {

    private ArrayList<View> mHeadViewList = new ArrayList<View>();
    private ArrayList<View> mFootViewList = new ArrayList<View>();
    private Adapter mAdapter;

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(View view){
        mHeadViewList.clear();
        mHeadViewList.add(view);
        if (mAdapter != null){
            if (!(mAdapter instanceof RecylerWrapAdapter)){
                mAdapter = new RecylerWrapAdapter(mHeadViewList,mFootViewList,mAdapter);
            }
        }
    }

    public void addFootView(View view){
        mFootViewList.clear();
        mFootViewList.add(view);
        if (mAdapter != null){
            if (!(mAdapter instanceof RecylerWrapAdapter)){
                mAdapter = new RecylerWrapAdapter(mHeadViewList,mFootViewList,mAdapter);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mHeadViewList.isEmpty() && mFootViewList.isEmpty()){
            super.setAdapter(adapter);
        }else{
            adapter = new RecylerWrapAdapter(mHeadViewList,mFootViewList,adapter);
            super.setAdapter(adapter);
        }
        mAdapter = adapter;
    }

}
