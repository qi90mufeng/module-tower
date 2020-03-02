package com;

public class Test1109 {
        public String str = "6";
        public static void main(String[] args) {
            Test1109 sv = new Test1109();
            sv.change(sv.str);
            System.out.println(sv.str);
        }
        public void change(String str) {
            this.str = "10";
        }
    }