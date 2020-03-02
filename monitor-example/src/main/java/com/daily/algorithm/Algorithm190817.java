package com.daily.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class Algorithm190817 {

//    给出n对括号，请编写一个函数来生成所有的由n对括号组成的合法组合。
//    例如，给出n=3，解集为： "((()))", "(()())", "(())()", "()(())", "()()()"
//    Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
//    For example, given n = 3, a solution set is:
//    "((()))", "(()())", "(())()", "()(())", "()()()"

    //first-mine
    public ArrayList<String> generateParenthesis(int n) {
        //n = 1  1 = 2^(2 * 1 - 2) 1个
        //n = 2  4 = 2^(2 * 2 - 2)   2个
        //n = 3  16 = 2^(2 * 3 - 2)   5个
        //n = 4  64 = 2^(2 * 4 - 2)  14个

        //
        Set<String> all = new HashSet<>();
        for (int i = 0; i < 2 * n; i++){

        }
        return new ArrayList<>(all);
    }


    //00001111
    //00010111 00011011 00011101
    //00100111 00101011 00101101


    //other-people
    public ArrayList<String> generateParenthesis2(int n) {


        return null;
    }
}
