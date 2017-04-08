package com.newtrekwang.customwidgetdemo.fragment;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.fragment.dummy.Words;
import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContentProviderFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "ContentProviderFragment>>>>>";
    @BindView(R.id.query)
    Button query;
    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.contentProvider_ListView)
    ListView contentProviderListView;
    Unbinder unbinder;
    @BindView(R.id.contentProvider_et)
    EditText contentProviderEt;
    private ContentResolver contentResolver;
    private Uri testUri;
    private SimpleAdapter simpleAdapter;

    public ContentProviderFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentResolver = getActivity().getContentResolver();
        testUri = Uri.parse("content://com.newtrekwang.customwidgetdemo.utils.TestProvider/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        contentProviderListView.setOnItemClickListener(this);

        queryData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void queryData() {
        Cursor cursor = contentResolver.query(Words.Word.DICT_CONTENT_URI, null, null, null, null);
        if (cursor!=null) {
            simpleAdapter=new SimpleAdapter(getActivity(),converCursorTolist(cursor),android.R.layout.simple_list_item_1,new String[]{Words.Word.WORD},new int[]{android.R.id.text1});
            contentProviderListView.setAdapter(simpleAdapter);
        }
    }

    private void insert(String str) {
        ContentValues values = new ContentValues();
        values.put(Words.Word.WORD, str);
        values.put(Words.Word.DETAIL, "....");
        Uri newUri = contentResolver.insert(Words.Word.DICT_CONTENT_URI, values);
        if (!TextUtils.isEmpty(newUri.toString())){
        showToast("添加生词成功！");
            contentProviderEt.setText("");
            queryData();
        }
    }

    @OnClick({R.id.query, R.id.insert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.query:
                queryData();
                break;
            case R.id.insert:
                insert(contentProviderEt.getText().toString());
                break;
        }
    }

    private void showToast(String str) {
        new ToastBuilder().setContentString("远程ContentProvider返回的Cursor为：" + str).createDefaultTextToast(getActivity()).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contentResolver = null;
    }

    private List<Map<String,String>>  converCursorTolist(Cursor cursor){
        ArrayList<Map<String,String>>  result=new ArrayList<>();
        while (cursor.moveToNext()){
            Map<String,String> map=new HashMap<>();
            map.put(Words.Word.WORD,cursor.getString(1));
            map.put(Words.Word.DETAIL,cursor.getString(2));
            result.add(map);
        }
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("确定删除该Item?")
                .setTitle("提示")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String,String> map= (Map<String, String>) simpleAdapter.getItem(position);
                           int result= contentResolver.delete(Words.Word.DICT_CONTENT_URI,Words.Word.WORD+" like ?", new String[]{map.get(Words.Word.WORD)});
                        if (result!=-1){
                            showToast("删除成功！");
                            queryData();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel,null)
                .create()
                .show();
    }
}
