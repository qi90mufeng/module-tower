package com.daily.algorithm;

/**
 * 需要注意int最小值取正时溢出问题
 */
public class Algorithm190813 {

//   Divide two integers without using multiplication, division and mod operator.

    //first-mine
    //二分倍减
    public int divide(int dividend, int divisor) {
        int sign = 1;
        if(divisor == 0){
            throw new RuntimeException("divisor 不能为 0");
        }
        if (dividend == 0){
            return 0;
        }
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)){
            sign = -1;
        }
        long ldividend = dividend;
        if (dividend < 0){
            ldividend = Math.abs((long)dividend);
        }
        long ldivisor = divisor;
        if (divisor < 0){
            ldivisor = Math.abs((long)divisor);
        }
        long finalRes = 0;
        while(ldividend >= ldivisor){
            long res = 1;
            long sum = ldivisor;
            while(sum + sum <= ldividend){
                sum += sum;
                res += res;
            }
            ldividend -= sum;
            finalRes += res;
        }
        finalRes = finalRes * sign;
        if(finalRes > Integer.MAX_VALUE){
            finalRes = Integer.MAX_VALUE;
        }else if(finalRes < Integer.MIN_VALUE){
            finalRes = Integer.MIN_VALUE;
        }
        return (int)finalRes;
    }

    //other-people
    public int divide2(int dividend, int divisor) {

        return 0;
    }

}
