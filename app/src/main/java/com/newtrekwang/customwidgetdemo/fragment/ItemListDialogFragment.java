package com.newtrekwang.customwidgetdemo.fragment;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newtrekwang.customwidgetdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemListDialogFragment extends BottomSheetDialogFragment {
    private Listener mListener;
    private BottomSheetBehavior<View> mBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_item_list_dialog, null);
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter(getData()));
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
       return  dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
//        默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list=new ArrayList<>();
        ContentResolver contentResolver=getActivity().getContentResolver();
//        查询所有联系人
        Cursor cursor =contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            Map<String,Object> map=new HashMap<>();
//            id
            String contactId=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            map.put(ContactsContract.Contacts._ID,contactId);
//            name
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            map.put(ContactsContract.Contacts.DISPLAY_NAME,name);

//            phones
            List<String> detaile=new ArrayList<>();
//            查询指定联系人的电话号码
            Cursor phones=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
            while (phones.moveToNext()){
               String phoneNumber= phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                detaile.add(phoneNumber);
            }
            map.put("phones",detaile);
            phones.close();

            list.add(map);
        }
        cursor.close();
        return list;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       mListener=new Listener() {
           @Override
           public void onItemClicked(int position) {
//               点击item关闭
               mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
           }
       };
    }
    public interface Listener {
        void onItemClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

   public TextView text1,text2;
        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(android.R.layout.simple_list_item_2, parent, false));
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
            text2= (TextView) itemView.findViewById(android.R.id.text2);
           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClicked(getAdapterPosition());
                        dismiss();
                    }
                }
            });
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Map<String,Object>> data;

        ItemAdapter(List<Map<String,Object>> data) {
            this.data=data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
          holder.text1.setText((String)data.get(position).get(ContactsContract.Contacts.DISPLAY_NAME));
            ArrayList<String> list= (ArrayList<String>) data.get(position).get("phones");
            holder.text2.setText(list.toString());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public List<Map<String, Object>> getData() {
            return data;
        }

        public void setData(List<Map<String, Object>> data) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

}
