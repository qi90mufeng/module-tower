package com.albert;

import java.util.HashMap;
import java.util.Map;

public class TestClass {
    private static void testMethod(){
        System.out.println("testMethod");
    }

//    public static void main(String[] args) {
//        System.out.println((TestClass)null);
//        ((TestClass)null).testMethod();
//    }


//    public static void main(String[] args) {
//        int i = 0;
//        Integer j = new Integer(0);
//        System.out.println(i == j);
//        System.out.println(j.equals(i));
//    }

//    public static void main(String[] args) {
//        String s1 = "abc" + "def";
//        String s2 = new String(s1);
//        if (s1 == s2)
//            System.out.println("111");
//        if (s1.equals(s2))
//            System.out.println(222);
//    }


//    public static void main(String[] args) {
//        final String pig = "length: 10";
//        final String dog = "length: " + pig.length();
//        System.out.println("Animals are equal: " + pig == dog);
//    }


//    static void setName(Map map){
//        map.put("name", "Tomcat");
//    }
//
//    public static void main(String[] args) {
//        Map map = new HashMap();
//        map.put("name", "Web");
//        setName(map);
//        System.out.println(map.get("name"));
//    }

//    public static String output = "";
//    public static void foo(int i){
//        try{
//            if (i==1){
//                throw new Exception();
//            }
//            output +="1";
//        }catch (Exception e){
//            output +="2";
//            return;
//        }finally {
//            output +="3";
//        }
//        output +="4";
//    }
//    public static void main(String[] args) {
//        foo(0);
//        foo(1);
//        System.out.println(output);
//    }


    public static void main(String[] args) {
        String.valueOf(100);
    }
}
