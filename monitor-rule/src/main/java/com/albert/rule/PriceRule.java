package com.albert.rule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "priceRule", description = "check currentPrice is bigger than priceLower", priority = 1)
public class PriceRule {

    @Condition
    public boolean checkPriceLower(@Fact("priceLower") Long priceLower, @Fact("currentPrice")Long currentPrice){
        return currentPrice > priceLower;
    }

    @Action
    public void printResult(){
        System.err.println("当前价格高于最低价");
    }
}
