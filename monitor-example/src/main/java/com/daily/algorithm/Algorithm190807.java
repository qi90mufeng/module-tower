package com.daily.algorithm;

import java.util.Scanner;

/**
 * 回文子串
 */
public class Algorithm190807 {
    //Given a string S, find the longest palindromic substring in S. You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.

    //first-mine
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 1){
            return s;
        }


        return "";
    }

    //other-people
    public String longestPalindrome2(String s) {

        return null;
    }


    public static void main(String[] args) {
        String str = "abcde";
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print("请输入字符串：");
            String line = s.nextLine();
            if (line.equals("exit")) break;
            else {
                System.out.println("最大回文子串: "+Manacher(line));
            }
        }

    }
    public static String Manacher(String s) {
        // Insert '#'
        String t = "$#";
        for (int i = 0; i < s.length(); ++i) {
            t += s.charAt(i);
            t += "#";
        }
        t += "@";
        // Process t
        int[] p = new int[t.length()];;
        int mx = 0, id = 0, resLen = 0, resCenter = 0;
        for (int i = 1; i < t.length()-1; ++i) {
            p[i] = mx > i ? Math.min(p[2 * id - i], mx - i) : 1;
            while (((i - p[i])>=0) && ((i + p[i])<t.length()-1) && (t.charAt(i + p[i]) == t.charAt(i - p[i])))
                ++p[i];
            if (mx < i + p[i]) {
                mx = i + p[i];
                id = i;
            }
            if (resLen < p[i]) {
                resLen = p[i];
                resCenter = i;
            }
        }
        return s.substring((resCenter - resLen) / 2, (resCenter - resLen) / 2 + resLen-1);
    }

}
