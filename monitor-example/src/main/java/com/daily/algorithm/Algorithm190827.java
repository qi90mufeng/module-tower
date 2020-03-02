package com.daily.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

//给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
//
//例如：
//给定二叉树 [3,9,20,null,null,15,7],
//
//    3
//   / \
//  9  20
//    /  \
//   15   7
//返回锯齿形层次遍历如下：
//
//[
//  [3],
//  [20,9],
//  [15,7]
//]
//
public class Algorithm190827 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root == null){
            return new ArrayList<>();
        }
        Queue<TreeNode> q1 = new LinkedList<>(); //正序
        Stack<TreeNode> q2 = new Stack<>(); //倒序
        q1.add(root);
        List<List<Integer>> allList = new ArrayList<>();
        //
        while(!q1.isEmpty() || !q2.isEmpty()){
            if (!q1.isEmpty()){
                //循环q1
                List<Integer> curList = new ArrayList<>();
                q1.stream().forEach(item -> {
                    curList.add(item.val);
                    if(item.left!=null) q2.add(item.left);
                    if(item.right!=null) q2.add(item.right);
                });
                allList.add(curList);
                q1.clear();
            }
            if (!q2.isEmpty()){
                List<Integer> curList = new ArrayList<>();
                q2.stream().forEach(item -> {
                    if(item.left!=null) q1.add(item.left);
                    if(item.right!=null) q1.add(item.right);
                });
                //循环q2
                while(!q2.isEmpty()){
                    TreeNode item = q2.pop();
                    curList.add(item.val);
                }
                allList.add(curList);
            }
        }
        return allList;
    }
}


/**
 * Definition for a binary tree node.
*/
class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }
