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
 * Desc:  封装的Item单类型布局适配器,子类仅实现onBindViewHolder方法就行
 * Created on 2017/1/31 15:44.
 * Mail:408030208@qq.com
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder,D> extends RecyclerView.Adapter<VH> {
    protected List<D> datas;
    protected int itemViewId;
    protected Context context;
    protected abstract int getItemViewLayoutId();
    private   OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public BaseRecyclerViewAdapter(Context context){
        this.context=context;
        datas=new ArrayList<>();
        itemViewId=getItemViewLayoutId();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(itemViewId,parent,false);
        return (VH) getCustomViewHolder(view);
    }

    protected abstract RecyclerView.ViewHolder getCustomViewHolder(View view);

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClick(v,datas.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }



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

    public OnRecyclerViewItemClickListener<D> getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<D> onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }


    public interface OnRecyclerViewItemClickListener<D>{
        void onItemClick(View view, D bean);
    }
}
