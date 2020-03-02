package com.daily.algorithm;

/**
 * 能够排除首部的空格，从第一个非空字符开始计算
 * 允许数字以正负号(+-)开头
 * 遇到非法字符便停止转换，返回当前已经转换的值，如果开头就是非法字符则返回0
 * 在转换结果溢出时返回特定值，这里是最大/最小整数
 */
public class Algorithm19080901 {

//    Implement atoi to convert a string to an integer.
//    Hint: Carefully consider all possible input cases. If you want a challenge, please do not see below and ask yourself what are the possible input cases.
//    Notes: It is intended for this problem to be specified vaguely (ie, no given input specs). You are responsible to gather all the input requirements up front.
//    spoilers alert... click to show requirements for atoi.
//    Requirements for atoi:
//    The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.
//    The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
//    If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
//    If no valid conversion could be performed, a zero value is returned. If the correct value is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.

    //first-mine
    public static int atoi(String str) {
        if (str == null || str.trim().equals("")){
            return 0;
        }
        int sign = 1;
        boolean hasFuhao = false;
        boolean hasNum = false;
        String nstr = "";
        for(char tmp: str.toCharArray()){
            if (tmp == '-' || tmp == '+'){
                if (hasNum){
                    break;
                }
                if (!hasFuhao){
                    nstr += tmp;
                    hasFuhao = true;
                }else{
                    break;
                }
            }else if (tmp >= '0' && tmp <= '9'){
                nstr += tmp;
                hasFuhao = true;
                hasNum = true;
            }else{
                if (hasFuhao || hasNum){
                    break;
                }
            }
        }
        if ("".equals(nstr) || "-".equals(nstr) || "+".equals(nstr)){
            return 0;
        }
        Long ll = Long.valueOf(nstr);
        if (sign * ll > Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }
        if (sign * ll < Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }
        return sign * ll.intValue();
    }

    //other-people
    public int atoi2(String str) {

        return 0;
    }

    public static void main(String[] args) {
        atoi("2147483648");
    }
}
