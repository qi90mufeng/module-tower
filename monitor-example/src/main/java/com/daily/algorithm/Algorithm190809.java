package com.daily.algorithm;

/**
 *
 */
public class Algorithm190809 {

//    Reverse digits of an integer.
//    Example1: x = 123, return 321
//    Example2: x = -123, return -321
//
//    click to show spoilers.
//
//    Have you thought about this?
//    Here are some good questions to ask before coding. Bonus points for you if you have already thought through this!
//
//    If the integer's last digit is 0, what should the output be? ie, cases such as 10, 100.
//
//    Did you notice that the reversed integer might overflow? Assume the input is a 32-bit integer, then the reverse of 1000000003 overflows. How should you handle such cases?
//
//    Throw an exception? Good, but what if throwing an exception is not an option? You would then have to re-design the function (ie, add an extra parameter).

    //first-mine
    public int reverse(int x) {
        String s = String.valueOf(x);
        int length = s.length();
        boolean flag = s.startsWith("-");
        StringBuffer sb = new StringBuffer();
        if (flag){
            sb.append("-");
        }
        for (int j = length - 1; j >= 0; j--){
            if (s.toCharArray()[j] != '-'){
                sb.append(s.toCharArray()[j]);
            }
        }
        return Integer.valueOf(sb.toString());
    }

    //other-people
    public int reverse2(int x) {
        int f = 1;
        if(x == 0)return 0;
        if(x < 0 ){
            x = -x;
            f = -1;
        }
        long re = 0l;
        while(x > 0){
            re = re*10 + x%10;
            x = x/10;
        }
        re = f*re;
        if(re > Integer.MAX_VALUE || re < Integer.MIN_VALUE)return 0;
        else return (int)re;
    }
}
