package com.albert;

public class TestMultipy {

    public static void main(String[] args) {
        int count = 0;
        for (int i = 1; i <= 1000; i++){
           int i1 =  i / 100;
           int i2 = (i - i1 * 100) / 10;
           int i3 = (i - i1 * 100 - i2 * 10) / 1;
            System.out.println(i1 + " " + i2 + " " + i3);
           int i4 = i1 * i2 * i3;
           if (i4 == 0){
               count++;
           }
        }
        System.out.println(count);
    }


}
