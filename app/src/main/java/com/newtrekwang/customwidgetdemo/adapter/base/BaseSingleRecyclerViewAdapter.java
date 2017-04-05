package com.newtrekwang.customwidgetdemo.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WJX .
 * Desc:  封装的Item单类型布局适配器，可添加头部和尾部，子类仅实现onBindViewHolder方法就行
 * Created on 2017/1/31 15:44.
 * Mail:408030208@qq.com
 */

public abstract class BaseSingleRecyclerViewAdapter<VH extends RecyclerView.ViewHolder,F extends RecyclerView.ViewHolder,H extends RecyclerView.ViewHolder,D>  extends RecyclerView.Adapter {
    public static final int FOOTER_TYPE =100;
    public static final int ITEM_TYPE=101;
    public static final int HEDER_TYPE=102;

    private static final String TAG = "BaseSingleRecyclerViewA调试信息";

    protected boolean isAddFooterView=false;
    protected abstract boolean getIsAddFooterView();

    protected boolean isAddHeaderView=false;
    protected abstract boolean getIsAddHeaderView();

    protected List<D> datas;
    protected Context mContext;
    protected int itemViewId;
    protected abstract int getItemViewLayoutId();

    protected int foolterLayoutId;
    protected abstract int getFoolterLayoutId();

    protected int headerLayoutId;
    protected abstract int getHeaderLayoutId();

    private OnRecyclerViewItemClickListener<D> onRecyclerViewItemClickListener;
    public BaseSingleRecyclerViewAdapter(Context context){
        this.mContext=context;
        datas=new ArrayList<>();
        this.itemViewId=getItemViewLayoutId();
        isAddFooterView = getIsAddFooterView();
        isAddHeaderView=getIsAddHeaderView();
        this.foolterLayoutId = getFoolterLayoutId();
        this.headerLayoutId=getHeaderLayoutId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      if (viewType==FOOTER_TYPE){
            View view= LayoutInflater.from(parent.getContext()).inflate(foolterLayoutId,parent,false);
            return getFoolterViewHolder(view);
        }else  if (viewType==HEDER_TYPE){
            View view=LayoutInflater.from(parent.getContext()).inflate(headerLayoutId,parent,false);
            return getHeaderViewHolder(view);
        }else if (viewType==ITEM_TYPE)
      {
          View view= LayoutInflater.from(parent.getContext()).inflate(itemViewId,parent,false);
          view.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (onRecyclerViewItemClickListener!=null){
                      onRecyclerViewItemClickListener.onItemClick(view, (D) view.getTag());
                  }
              }
          });
          return getCustomItemViewHolder(view);
      }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (isAddHeaderView&&position==0){
            return HEDER_TYPE;
        }else if (isAddFooterView&&isAddHeaderView&&(position==datas.size()+1)){
            return FOOTER_TYPE;
        }else if (isAddFooterView&&!isAddHeaderView&&(position==datas.size())){
            return FOOTER_TYPE;
        }else
        return ITEM_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder!=null) {
            if (holder.getItemViewType()==HEDER_TYPE){
                onCustomHeaderBindViewHolder((H)holder);
            }else if (holder.getItemViewType()==FOOTER_TYPE){
                onCustomFoolterBindViewHolder((F) holder);
            }else if (holder.getItemViewType()==ITEM_TYPE){
                if (isAddHeaderView){
                    onCustomItemBindViewHolder((VH)holder,position-1,datas.get(position-1));
                    holder.itemView.setTag(datas.get(position-1));
                }else {
                    onCustomItemBindViewHolder((VH)holder,position,datas.get(position));
                    holder.itemView.setTag(datas.get(position));
                }
            }
        }
    }
    protected abstract void onCustomFoolterBindViewHolder(F holder);
    protected abstract void onCustomHeaderBindViewHolder(H holder);
    protected abstract void onCustomItemBindViewHolder(VH holder, int position, D data);

    protected abstract RecyclerView.ViewHolder  getCustomItemViewHolder(View view);
    protected abstract RecyclerView.ViewHolder getFoolterViewHolder(View view);
    protected abstract RecyclerView.ViewHolder getHeaderViewHolder(View view);

    public List<D> getDatas() {
        return datas;
    }

    public void setDatas(List<D> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addData(D data){
        datas.add(data);
        notifyItemInserted(datas.size());
    }
    public void insertData(int position, D data){
        datas.add(position,data);
        notifyItemInserted(position);
    }
    public void deleteData(int position){
        datas.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        int allCount=datas.size();
        if (isAddFooterView){
           allCount+=1;
        }
        if (isAddHeaderView){
            allCount+=1;
        }
        return allCount;
    }

    public OnRecyclerViewItemClickListener<D> getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<D> onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener<P>{
        void onItemClick(View view, P bean);
    }



}
