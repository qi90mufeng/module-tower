package com.daily.algorithm;

import java.util.HashMap;
import java.util.Map;

public class Solution1 {
    public static void solve(char[][] board) {
        if(board == null || board[0] == null){
            return;
        }
        //保留的位置 寻找元素 ： 任何边界上的 'O'  或者 与边界上的 'O' 相连的 'O'
        Map<String, String> map = new HashMap<>();
        //第一排
        for(int j = 0; j < board[0].length; j++){
            if(board[0][j] == 'O'){
                map.put("0-" + j, "O");
                dfs(board, map, 1, j, true);
            }
        }

        //最后一排
        for(int j = 0; j < board[0].length; j++){
            if(board[board.length-1][j] == 'O'){
                map.put(board.length-1 + "-" + j, "O");
                dfs(board, map, board.length-1, j, false);
            }
        }
        //第一列
        for(int i = 0; i < board.length; i++){
            if(board[i][0] == 'O'){
                map.put(i + "-0", "O");
                dfs(board, map, i, 1, false);
            }
        }
        //最后一列
        for(int i = 0; i < board.length; i++){
            if(board[i][board[0].length-1] == 'O'){
                map.put(i + "-" + board[0].length, "O");
                dfs(board, map, i, board[0].length-1, false);
            }
        }


        //其他全部填充X
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                String key = i + "-" + j;
                if(!map.containsKey(key)){
                    board[i][j] = 'X';
                }
            }
        }
    }


    //递归查找 flag向上还是向下走
    public static void dfs(char[][] board, Map<String, String> map, int i, int j, boolean flag){
        //从第一排中往下寻找相邻的0  重复类似动作
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length){
            return;
        }
        if(board[i][j] == 'O'){
            map.put(i + "-" + j, "O");
            //向右
            for(int m = j + 1; m < board[0].length; m++){
                if(board[i][m] == 'O'){
                    map.put(i + "-" + m, "O");
                    //向右、向左、向下
                    dfs(board, map, (flag ? i+1 : i-1), m, flag);
                }
            }
            //向左
            for(int m = j; m >= 0; m--){
                if(board[i][m] == 'O'){
                    map.put(i + "-" + m, "O");
                    //向右、向左、向下
                    dfs(board, map, (flag ? i-1 : i-1), m, flag);
                }
            }
            //向下
            dfs(board, map, (flag ? i+1 : i-1), j, flag);
        }

    }

    public static void main(String[] args) {
        solve(null);
    }
}
