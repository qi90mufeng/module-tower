package com.albert;

import com.albert.rule.PriceRule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class PriceEasyRules {
    public static void main(String[] args) {
        Rules rules = new Rules();
        // create rules
        PriceRule priceRule = new PriceRule();
        // register rules 注册规则
        rules.register(priceRule);
        // fire rules
        //设置入参 入参类型须一致
        Facts facts = new Facts();
        facts.put("priceLower", 100L);
        facts.put("currentPrice", 200L);

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
