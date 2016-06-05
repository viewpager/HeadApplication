package com.wb.head.wrap;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wb.head.R;
import com.wb.head.bean.Item;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class RecylerAdpater extends RecyclerView.Adapter<RecylerAdpater.MyViewHolder> {

    private List items;

    public RecylerAdpater(List list){
        super();
        items = list;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Item item = (Item)items.get(position);
        holder.mTitle.setText(item.getMaintitle());
        holder.mSubtitle.setText(item.getSubtitle());

        if (mOnItemClickLitener != null){
            // 设置点击事件  通过系统view类的回调设置我们的回调
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.ll_item,pos);
                }
            });

            // 设置长按事件 通过系统view类的回调设置我们的回调
            holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.ll_item,pos);
                    return true;
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle;
        TextView mSubtitle;
        LinearLayout ll_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.maintitle);
            mSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    //回调接口用于点击事件
    public interface OnItemClickLitener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
