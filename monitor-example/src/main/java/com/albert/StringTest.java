package com.albert;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class StringTest {

    public static void main(String[] args) {
        sortCnString();
    }

    //中文排序
    public static void sortCnString(){
        String[] strs = {"张三","李四","王五"};
        Comparator c = Collator.getInstance(Locale.CHINA);
        Arrays.sort(strs, c);
        for (String s: strs) {
            System.out.println(s);
        }
    }
}

