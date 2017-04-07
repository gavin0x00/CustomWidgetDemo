package com.newtrekwang.customwidgetdemo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {
    public static List<Map<String,String>> getSimpleData(){
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map1=new HashMap<>();
        map1.put("key1","erqwerqw");
        map1.put("key2","dfasdfas");
        list.add(map1);
        Map<String,String> map2=new HashMap<>();
        map2.put("key1","erqwerqw");
        map2.put("key2","dfasdfas");
        list.add(map2);
        Map<String,String> map3=new HashMap<>();
        map3.put("key1","erqwerqw");
        map3.put("key2","dfasdfas");
        list.add(map3);
        Map<String,String> map4=new HashMap<>();
        map4.put("key1","erqwerqw");
        map4.put("key2","dfasdfas");
        list.add(map4);
        return list;
    }

}
