package com.daily.algorithm;

import java.util.HashMap;
import java.util.Map;

public class Algorithm190802 {

    //Given a string, find the length of the longest substring without repeating characters. For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3.
    // For "bbbbb" the longest substring is "b", with the length of 1.

    // test-case
    // wlrbbmqbhcdarzowk kyhiddqscdxrjmowfrxsjybldbefsarcbynecdyggxxpklorellnmpapqfwkhopkmco    12

    //first-mine
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0){
            return 0;
        }
        int max = 0;
        Map<Character, Integer> aa = new HashMap<>();
        for (int i = 0; i < s.length(); i++){
            if (aa.containsKey(s.charAt(i))){
                //表示有重复字段，需要截断
                int start = aa.get(s.charAt(i));
                max = max > aa.size() ? max : aa.size();
                returnA(s, start, i, aa);
            }else{
                aa.put(s.charAt(i), i);
            }
        }
        return max > aa.size() ? max : aa.size();
    }

    public void returnA(String s, int start, int cur, Map<Character, Integer> map){
        map.clear();
        for (int i = start + 1; i <= cur; i++){
            map.put(s.charAt(i), i);
        }
    }

    /*
        "滑动窗口"
        比方说 abcabccc 当你右边扫描到abca的时候你得把第一个a删掉得到bca，
        然后"窗口"继续向右滑动，每当加到一个新char的时候，左边检查有无重复的char，
        然后如果没有重复的就正常添加，
        有重复的话就左边扔掉一部分（从最左到重复char这段扔掉），在这个过程中记录最大窗口长度
    */
    //other-people
    public int lengthOfLongestSubstring2(String s) {
        if(s == null || s.length() == 0) return 0;
        //新建一个map进行存储char
        HashMap<Character,Integer> map = new HashMap<Character,Integer>();
        int leftBound = 0;
        int max = 0;
        for(int i=0; i<s.length();i++){
            char  c = s.charAt(i);
            //窗口左边可能为下一个char，或者不变
            leftBound = Math.max(leftBound,(map.containsKey(c))? map.get(c)+1:0);
            max = Math.max(max, i-leftBound+1);//当前窗口长度
            map.put(c,i);
        }
        return max;

    }
}
