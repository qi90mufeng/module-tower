package com;

import java.lang.reflect.Method;

public class Test2 {
    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("com.Test2");
        Method method = klass.getMethod("target", int.class);
        for (int i = 0; i < 20; i++) {
            method.invoke(null, i);
        }
    }
}

//# 使用 -verbose:class 打印加载的类
//$ java -verbose:class Test2

