package com.albert;

public class Solution {
    public static int[] twoSum(int[] numbers, int target) {
        int[] aa = new int[2];

        for(int i = 0; i < numbers.length - 1; i++){
            for(int j = i + 1; j < numbers.length; j++){
                if(target == numbers[i] + numbers[j]){
                    aa[0] = numbers[i] > numbers[j] ? numbers[j] : numbers[i];
                    aa[1] = numbers[i] > numbers[j] ? numbers[i] : numbers[j];
                    return aa;
                }
            }
        }

        return aa;
    }


    public static void main(String[] args) {
        twoSum(new int[]{3,2,4}, 6);
    }



}