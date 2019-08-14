package com.daily.algorithm;

/**
 *
 */
public class Algorithm190814 {
//    实现函数 strStr。
//    函数声明如下：
//    char *strStr(char *haystack, char *needle)
//    返回一个指针，指向needle第一次在haystack中出现的位置，如果needle不是haystack的子串，则返回null。
//
//    Implement strStr().
//    Returns a pointer to the first occurrence of needle in haystack, or null if needle is not part of haystack

    //first-mine
    public String strStr(String haystack, String needle) {
        if(haystack == null || needle == null || needle.length() > haystack.length()){
            return null;
        }
        for(int i = 0;i <= haystack.length()-needle.length(); i++) {
            if(haystack.substring(i,i + needle.length()).equals(needle))
            {
                return haystack.substring(i,haystack.length());
            }
        }
        return null;
    }
    //other-people
    public String strStr2(String haystack, String needle) {
        return "";
    }

}
