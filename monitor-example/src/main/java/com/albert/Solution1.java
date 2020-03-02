package com.albert;

import java.util.HashMap;
import java.util.Map;

public class Solution1 {
    public static void main(String[] args) {
        Point[] points1 = {new Point(1,1),new Point(1,1), new Point(2,3)}; // 3
        Point[] points2 = {new Point(-4,-4),new Point(-8,-582), new Point(-3,3), new Point(-9,-651), new Point(9,591)}; //3
        Point[] points3 = {new Point(84,250),new Point(0,0), new Point(1,0), new Point(0,-70),new Point(0,-70), new Point(1,-1),
                new Point(21,10),new Point(42,90),new Point(-42,-230)}; //6
        System.out.println(maxPoints(points3));
    }
    public static int maxPoints(Point[] points) {
        if(points == null || points.length<3) return points.length;
        int res =0;
        for(int i=1;i<points.length;i++){
            int count = 0;
            long a = points[i].x;
            long b = points[i].y;
            long dx = a - points[i-1].x;
            long dy = b - points[i-1].y;
            if(dx==0 && dy==0){
                for(int j=0;j<points.length;j++){
                    if(points[j].x==a && points[j].y==b){
                        count++;
                    }
                }
            }else{
                for(int j=0;j<points.length;j++){
                    if((points[j].x-a)*dy==(points[j].y-b)*dx){
                        count++;
                    }
                }
            }
            res = Math.max(res,count);
        }
        return res;
    }
}
