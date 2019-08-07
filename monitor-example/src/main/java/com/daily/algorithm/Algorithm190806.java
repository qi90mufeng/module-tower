package com.daily.algorithm;

import java.util.Arrays;

/**
 * zigzag转换
 */
public class Algorithm190806 {
//    The string"PAYPALISHIRING"is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
//    P   A   H   N
//    A P L S I I G
//    Y   I   R
//    And then read line by line:"PAHNAPLSIIGYIR"
//
//    Write the code that will take a string and make this conversion given a number of rows:
//
//    string convert(string text, int nRows);
//    convert("PAYPALISHIRING", 3)should return"PAHNAPLSIIGYIR".

    //first-mine
    public static String convert(String s, int nRows) {
        //一行直接输出，不需转换
        if (nRows <= 1) return s;
        //多行处理
        int pageSize = 2 * nRows - 2;
        int[] rows = fetchRowsCount(s.length(), nRows, pageSize);
        String[] str = new String[s.length()];
        for (int index = 0; index < s.length(); index++){
            str[returnIndex(index, pageSize, nRows, rows)] = String.valueOf(s.toCharArray()[index]);
        }
        StringBuffer sb = new StringBuffer();
        for (String tmp : str){
            sb.append(tmp);
        }
        return sb.toString();
    }

    private static int[] fetchRowsCount(int length, int nRows, int pageSize) {
        int lastIndex = length % pageSize;  //从1开始计数
        int[] rows = new int[nRows];
        for (int i = 0; i < nRows; i++){
            if (i == 0 || i == nRows - 1){
                rows[i] = length / pageSize + (lastIndex > i ? 1 : 0);
            }else if(i > 0 && i < nRows - 1){
                int minRow = i + 1; //当前行所在的最小的index和当前行所在的最大的index，计算当前行多余几个  从1开始计数
                int maxRow = nRows + (nRows - i - 1);  //从1开始计数
                rows[i] = 2 * (length / pageSize) + (lastIndex >= minRow ? (lastIndex >= maxRow ? 2:1):0);
            }
        }
        return rows;
    }

    public static int returnIndex(int curIndex, int pageSize, int nRows, int[] rows){
        //判断当前index处在第几行
        int curRow = pageSize / 2 - Math.abs(pageSize / 2 - curIndex % pageSize) + 1; //从1开始计数
        if(curRow == 1){
            return curIndex / pageSize;
        }else if(curRow > 1 && curRow < nRows){
            int sum = 0;
            for (int i = 0; i <curRow - 1; i++){
                sum += rows[i];
            }
            int minRow = curRow; //当前行所在的最小的index和当前行所在的最大的index，计算当前行多余几个  从1开始计数
            int maxRow = nRows + (nRows - curRow);  //从1开始计数
            int lastIndex = curIndex % pageSize;
            int ti = sum + 2 * (curIndex / pageSize) + (lastIndex < minRow ? 0 : (lastIndex < maxRow ?  1 : 0));
            return ti;
        }else{
            int sum = 0;
            for (int i = 0; i <curRow - 1; i++){
                sum += rows[i];
            }
            int minRow = curRow; //当前行所在的最小的index和当前行所在的最大的index，计算当前行多余几个  从1开始计数
            int maxRow = nRows + (nRows - curRow);  //从1开始计数
            int lastIndex = curIndex % pageSize;
            int ti = sum + (curIndex / pageSize) + (lastIndex < minRow ? 0 : (lastIndex < maxRow ?  1 : 0)) ;
            return ti;
        }
    }

    public static void main(String[] args) {
        longestPalindrome2("ABCDEFGHIJK", 5);
    }
    //other-people
    //定义nRows长度的stringbuffer数组，存放每一行的结果  // 先从0~nRows，再从nRows-2~0
    public static String longestPalindrome2(String s, int nRows) {
        if(nRows <= 1) return s;
        StringBuffer[] sb = new StringBuffer[nRows];
        for(int i = 0; i < sb.length; i++)
            sb[i] = new StringBuffer();

        int len = s.length();
        int i = 0;
        while(i < len){
            for(int j = 0; j < nRows && i < len; j++)
                sb[j].append(s.charAt(i++));
            for(int j = nRows - 2; j > 0 && i < len; j--)
                sb[j].append(s.charAt(i++));
        }

        for(int j = 1; j < nRows; j++)
            sb[0].append(sb[j]);

        return sb[0].toString();
    }
}
