package com.daily.algorithm;

public class Algorithm190812 {

//    在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
//    每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
//    判断数组中是否含有该整数。

    //first-mine
    public boolean Find(int target, int [][] array) {
        if(array == null || array.length == 0 || (array.length==1&&array[0].length==0)){
            return false;
        }
        int maxj = 0;
        for(int i = 0; i < array.length; i++){
            if(i == 0){
                maxj = array[i].length;
            }
            for(int j = 0; j < maxj; j++){
                if(target > array[i][j]){
                    continue;
                }else if(target == array[i][j]){
                    return true;
                }else if(target < array[i][j]){
                    if(j == 0){
                        return false;
                    }
                    maxj = j;
                }
            }
        }
        return false;
    }

    //other-people
    public boolean Find2(int target, int [][] array) {
        int row=0;
        int col=array[0].length-1;
        while(row<=array.length-1&&col>=0){
            if(target==array[row][col])
                return true;
            else if(target>array[row][col])
                row++;
            else
                col--;
        }
        return false;
    }
}
