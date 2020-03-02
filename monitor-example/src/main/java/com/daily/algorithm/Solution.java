package com.daily.algorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    static ArrayList<String> r = new ArrayList<String>();
    public static ArrayList<String> generateParenthesis(int n) {
        //保证左边‘（’的数量始终大于等于右边的‘）’数量，可以考虑回溯法
        ArrayList<String> s = new ArrayList<String>();
        gp("",0,0,n);
        return r;
    }
    private static void gp(String s,int left,int right,int n){
        if(right == n){
            r.add(s);
        }
        if(left < n){
            gp(s+"(",left+1,right,n);
        }
        // 递归过程中 左括号x的个数必须大于等于右括号个数
        if(left > right){
            gp(s+")",left,right+1,n);
        }
    }

    private static final String STR_FORMAT = "00000000";
    public static void main(String[] args) {
        List<String> all = generateParenthesis(4);
        for (String tmp: all){
            System.out.print(tmp + "  ");
        }
        System.out.println();
        int n = 4;
        for (int i = 0; i < (2 * n - 2); i++){
//            for(){
//
//            }
        }
        // 1 + 2 + 4 + 8 = 15、1 + 2 + 4 + 16 = 23、 1 + 2 + 4 + 32 = 39、 1 + 2 + 4 + 64 = 71
        // 1 + 2 + 8 + 16 = 27、 1 + 2 + 8 + 32 = 43、1 + 2 + 8 + 64 = 75
        // 1 + 2 + 16 + 32 = 51、1 + 2 + 16 +64 = 83
        // 1 + 4 + 8 + 16 = 29、 1 + 4 + 8 + 32 = 45、1 + 4 + 8 +64 = 77
        // 1 + 4 + 16 + 32 = 53、1 + 4 + 16 + 64 = 85
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        Integer[] nn = {15, 23, 39, 71, 27, 43, 75, 29, 45, 77, 53, 85, 51, 83};
        for (Integer i = 0; i < nn.length; i++){
            System.out.print(df.format(Integer.valueOf(Integer.toBinaryString(nn[i])))+ "  ");
        }
    }
}

//(((())))  ((()()))  ((())())  ((()))()  (()(()))  (()()())  (()())()  (())(())  (())()()  ()((()))  ()(()())  ()(())()  ()()(())  ()()()()
//00001111  00010111  00011011  00011101  00100111  00101011  00101101  00110011  00110101  01000111  01001011  01001101  01010011  01010101
//00001111  00010111  00100111  01000111  00011011  00101011  01001011  00011101  00101101  01001101  00110101  01010101  00111001  01011001
