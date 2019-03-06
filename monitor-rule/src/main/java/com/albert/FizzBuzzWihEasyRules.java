package com.albert;

import com.albert.rule.BuzzRule;
import com.albert.rule.FizzRule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

public class FizzBuzzWihEasyRules {
    public static void main(String[] args) {
        Rules rules = new Rules();
        // create rules
        FizzRule fizzRule = new FizzRule();
        BuzzRule buzzRule = new BuzzRule();
        // register rules 注册规则
        rules.register(fizzRule);
        rules.register(buzzRule);
        // fire rules
        for (int i = 1; i <= 7; i++) {
            Facts facts = new Facts();
            //设置入参
            fizzRule.setInput(i);
            buzzRule.setInput(i);
            RulesEngine rulesEngine = new DefaultRulesEngine();
            rulesEngine.fire(rules, facts);
            System.out.println();
        }
    }
}
