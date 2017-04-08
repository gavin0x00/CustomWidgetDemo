package com.newtrekwang.customwidgetdemo.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.newtrekwang.customwidgetdemo.fragment.dummy.Words;

public class TestProvider extends ContentProvider {
    private MyDataBaseHelper databaseHelper;

    // Creates a UriMatcher object.
    private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);

    private static final int WORDS=1;
    private static final  int WORD=2;

    static {
//        register UriMatcher
//        对应"content://"+AUTHORITY+"/words"
        matcher.addURI(Words.AUTHORITY,"words",WORDS);
        matcher.addURI(Words.AUTHORITY,"word/#",WORD);
    }

    private static final String TAG = "TestProvider>>>";


    @Override
    public boolean onCreate() {
       databaseHelper=new MyDataBaseHelper(this.getContext(),"myDict.db3",1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            case WORD:
//                解析出想查询的ID
                long id= ContentUris.parseId(uri);
                String whereClause=Words.Word._ID+"="+id;
//                 如果原来的where字句存在，拼接where字句
                if (!TextUtils.isEmpty(whereClause)){
                    whereClause=whereClause+" and "+selection;
                }
                return db.query("dict",projection,whereClause,selectionArgs,null,null,sortOrder);
            case WORDS:
                return db.query("dict",projection,selection,selectionArgs,null,null,sortOrder);
            default:
                throw new IllegalArgumentException("未知uri:"+uri);
        }
    }
/**
 * 返回指定uri参数对应的数据的MIME类型
 */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
      switch (matcher.match(uri)){
          case WORDS:
              return "vnd.android.cursor.dir/com.newtrekwang.dict";
          case WORD:
              return "vnd.android.cursor.item/com.newtrekwang.dict";
          default:
              throw new IllegalArgumentException("未知uri:"+uri);
      }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
       SQLiteDatabase database=databaseHelper.getWritableDatabase();
        switch (matcher.match(uri)){
            case WORDS:
//                插入数据，返回插入数据的ID
                long rowId=database.insert("dict", Words.Word._ID,values);
                if (rowId>0){
                    Uri wordUri=ContentUris.withAppendedId(uri,rowId);
//                    通知数据已经改变
                getContext().getContentResolver().notifyChange(wordUri,null);
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知uri:"+uri);
        }
        return null;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database=databaseHelper.getWritableDatabase();
        int num=0;
        switch (matcher.match(uri)){
            case WORD://delete
             long id=ContentUris.parseId(uri);
                String whereClause= Words.Word._ID+"="+id;
                if (!TextUtils.isEmpty(selection)){
                    whereClause+=" and "+selection;
                }
                num=database.delete("dict",whereClause,selectionArgs);
                break;
            case WORDS://delete all
                num=database.delete("dict",selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知uri:"+uri);

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database=databaseHelper.getWritableDatabase();
        int num=0;
        switch (matcher.match(uri)){
            case WORD:
                long id=ContentUris.parseId(uri);
                String whereClause=Words.Word._ID+"="+id;
                if (!TextUtils.isEmpty(selection)){
                    whereClause=whereClause+" and "+selection;
                }
                num=database.update("dict",values,whereClause,selectionArgs);
                break;
            case WORDS:
                num=database.update("dict",values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知uri:"+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }
}
