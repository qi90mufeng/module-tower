package com.daily.algorithm;


import java.util.HashMap;

public class Algorithm190801 {

//    Given an array of integers, find two numbers such that they add up to a specific target number.
//    The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
//    You may assume that each input would have exactly one solution.
//    Input: numbers={2, 7, 11, 15}, target=9
//    Output: index1=1, index2=2

    //first-mine
    public int[] twoSum(int[] numbers, int target) {
        int[] aa = new int[2];
        for(int i = 0; i < numbers.length - 1; i++){
            for(int j = i + 1; j < numbers.length; j++){
                if(target == numbers[i] + numbers[j]){
                    aa[0] = i + 1;
                    aa[1] = j+ 1;
                    return aa;
                }
            }
        }
        return aa;
    }

    //other-people
    public int[] twoSum2(int[] nums, int target) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])){
                return new int[]{map.get(nums[i])+1,i+1};
            }
            map.put(target - nums[i],i);
        }
        return null;
    }
}
