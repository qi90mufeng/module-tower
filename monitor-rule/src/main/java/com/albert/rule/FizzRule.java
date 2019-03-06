package com.albert.rule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;

@Rule  //规则类
public class FizzRule {

    private int input;

    @Condition   //判断是否命中规则
    public boolean isFizz(){
        return input % 5 ==0 ;
    }

    @Action   //命中规则后执行动作
    public void printFizz(){
        System.out.println("fizz");
    }

    public void setInput(int input){
        this.input = input;
    }

    @Priority  //优先级 0/1/2/3.....  越小优先级越高
    public int getPriority(){
        return 1;
    }
}