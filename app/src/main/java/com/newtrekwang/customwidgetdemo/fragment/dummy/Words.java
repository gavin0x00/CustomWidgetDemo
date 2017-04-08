package com.newtrekwang.customwidgetdemo.fragment.dummy;

import android.net.Uri;
import android.provider.SyncStateContract;

public final class Words {
    public static  final String AUTHORITY="com.newtrekwang.customwidgetdemo.utils.TestProvider";
    public static final class Word implements SyncStateContract.Columns{
//        定义Content所允许操作的三个数据列
        public final static String _ID="_id";
        public final static String WORD="word";
        public final static String DETAIL="detail";
//        定义Content提供服务的两个URi
        public final  static Uri DICT_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/words");
        public final  static Uri WORD_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/word");
    }
}
