package com.wb.head.wrap;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 * 将adapter进行包装
 */
public class RecylerWrapAdapter extends RecyclerView.Adapter{

    private RecyclerView.Adapter mAdapter;
    private ArrayList<View> mHeaderViews;
    private ArrayList<View> mFootViews;
    private final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<View>();
    private int mCurrentPosition;

    public RecylerWrapAdapter(ArrayList<View> mHeaderViews,ArrayList<View> mFootViews,RecyclerView.Adapter mAdapter){
        this.mAdapter = mAdapter;
        if (mHeaderViews == null){
            this.mHeaderViews = EMPTY_INFO_LIST;
        }else{
            this.mHeaderViews = mHeaderViews;
        }

        if (mFootViews == null){
            this.mFootViews = EMPTY_INFO_LIST;
        }else{
            this.mFootViews = mFootViews;
        }
    }

    public int getHeaderCount(){
        return mHeaderViews.size();
    }

    public int getFootCount(){
        return mFootViews.size();
    }

    /**
     * 返回该position对应的item的id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        int heads = getHeaderCount();
        if (mAdapter != null && position >= heads){
            int adjposition = position - heads; //减去头布局的当前位置
            int adapterCount = mAdapter.getItemCount(); //总共有多少条目
            if (adjposition < adapterCount){ // 当前位置如果是在条目中的
                return mAdapter.getItemId(adjposition);
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null){
            return getHeaderCount() + getFootCount() + mAdapter.getItemCount();
        }else{
            return getHeaderCount() +getFootCount();
        }
    }

    /**
     * 判断应该返回的是头布局还是正常子布局还是脚布局 在onCreateViewHolder中使用
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        mCurrentPosition = position;
        int heads = getHeaderCount();
        // 也就是说recycle的第一个条目位置为头布局
        if (position < heads){
            // 返回类型为头布局
            return RecyclerView.INVALID_TYPE;
        }
        int adjposition = position - heads; //减去头布局后当前布局的位置
        int adapterCount = 0;
        if (mAdapter != null){
            adapterCount = mAdapter.getItemCount();
            if (adjposition < adapterCount){
                //返回类型为正常布局
                return mAdapter.getItemViewType(adjposition);
            }
        }
        // 返回类型为脚布局
        return RecyclerView.INVALID_TYPE - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RecyclerView.INVALID_TYPE){
            // 返回头布局
            return new HeaderViewHolder(mHeaderViews.get(0));
        }else if (viewType == RecyclerView.INVALID_TYPE - 1){
            // 返回脚布局
            return new HeaderViewHolder(mFootViews.get(0));
        }
        // 返回正常布局
        return mAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int heads = getHeaderCount();
        if (position < heads) return;
        int adjPosition = position - heads;
        int adapterCount = 0;
        if (mAdapter != null){
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount){
                // 绑定正常条目数据
                mAdapter.onBindViewHolder(holder,adjPosition);
                return;
            }
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
