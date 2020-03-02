package com.daily.algorithm;

import java.util.Stack;

public class Algorithm19082701 {

    //有问题，不能提交
    public static String removeDuplicateLetters(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.toCharArray()[i];
            if (stack.contains(ch)) {
                continue;
            }
            while (stack.size() > 0 && stack.peek() > ch && s.lastIndexOf(stack.peek()) > i) {
                stack.pop();
            }
            stack.push(ch);
        }
        Object[] res = stack.toArray();

        return new String();
    }

    public static void main(String[] args) {
        removeDuplicateLetters("cbacdcbc");
    }
}
