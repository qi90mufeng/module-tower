## 规则引擎easy-rule
### 简介

```
https://github.com/j-easy/easy-rules
协议：MIT协议
```

```xml
<dependency>
    <groupId>org.jeasy</groupId>
    <artifactId>easy-rules-core</artifactId>
    <version>3.2.0</version>
</dependency>
```

### 学习

#### 主要类

* Rule
* RulesEngine
* RuleListener
* Facts 

#### 主要注解

* @Rule  

  > 可以标注name和description属性，每个rule的name要唯一，
  > 如果没有指定，则RuleProxy则默认取类名

* @Condition

  > 条件判断，要求返回boolean值，表示是否满足条件

* @Action

  > 标注条件成立之后触发的方法

* @Priority

  > 标注该rule的优先级，默认是Integer.MAX_VALUE - 1，值
  > 越小越优先

* @Fact

  > Facts的用法很像Map，它是客户
  > 端和规则文件之间通信的桥梁。在客户端使用put方法向Facts中添
  > 加数据，在规则文件中通过key来得到相应的数据



有两种使用方式：

1. java方式
    首先先创建规则并标注属性

```java
package com.lrq.wechatdemo.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.support.UnitRuleGroup;

/**
 * CreateBy: Liurenquan
 * CreateDate: 2018/12/26
 */
public class RuleClass {

    @Rule(priority = 1) //规则设定优先级
    public static class FizzRule {
        @Condition
        public boolean isFizz(@Fact("number") Integer number) {
            return number % 5 == 0;
        }

        @Action
        public void printFizz() {
            System.out.print("fizz\n");
        }
    }

    @Rule(priority = 2)
    public static class BuzzRule {
        @Condition
        public boolean isBuzz(@Fact("number") Integer number) {
            return number % 7 == 0;
        }

        @Action
        public void printBuzz() {
            System.out.print("buzz\n");
        }
    }

    public static class FizzBuzzRule extends UnitRuleGroup {

        public FizzBuzzRule(Object... rules) {
            for (Object rule : rules) {
                addRule(rule);
            }
        }

        @Override
        public int getPriority() {
            return 0;
        }
    }

    @Rule(priority = 3)
    public static class NonFizzBuzzRule {

        @Condition
        public boolean isNotFizzNorBuzz(@Fact("number") Integer number) {
            // can return true, because this is the latest rule to trigger according to
            // assigned priorities
            // and in which case, the number is not fizz nor buzz
            return number % 5 != 0 || number % 7 != 0;
        }

        @Action
        public void printInput(@Fact("number") Integer number) {
            System.out.print(number+"\n");
        }
    }

}
```

然后客户端调用

```java
package com.lrq.wechatdemo.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;

/**
 * CreateBy: Liurenquan
 * CreateDate: 2018/12/26
 */
public class RuleJavaClient {
    public static void main(String[] args) {
        // 创建规则引擎
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);

        // 创建规则集并注册规则
        Rules rules = new Rules();
        rules.register(new RuleClass.FizzRule());
        rules.register(new RuleClass.BuzzRule());
        rules.register(new RuleClass.FizzBuzzRule(new RuleClass.FizzRule(), new RuleClass.BuzzRule()));
        rules.register(new RuleClass.NonFizzBuzzRule());

        // 执行规则
        Facts facts = new Facts();
        for (int i = 1; i <= 100; i++) {
            facts.put("number", i);
            fizzBuzzEngine.fire(rules, facts);
            System.out.println();
        }
    }

}
```

2.yml方式

resources目录下新建fizzbuzz.yml

```
---
name: "fizz rule"
description: "print fizz if the number is multiple of 5"
priority: 1
condition: "number % 5 == 0"
actions:
- "System.out.println(\"fizz\")"

---
name: "buzz rule"
description: "print buzz if the number is multiple of 7"
priority: 2
condition: "number % 7 == 0"
actions:
- "System.out.println(\"buzz\")"

---
name: "fizzbuzz rule"
description: "print fizzbuzz if the number is multiple of 5 and 7"
priority: 0
condition: "number % 5 == 0 && number % 7 == 0"
actions:
- "System.out.println(\"fizzbuzz\")"

---
name: "non fizzbuzz rule"
description: "print the number itself otherwise"
priority: 3
condition: "number % 5 != 0 || number % 7 != 0"
actions:
- "System.out.println(number)"
```

客户端调用：

```java
package com.lrq.wechatdemo.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;
import org.jeasy.rules.mvel.MVELRuleFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * CreateBy: Liurenquan
 * CreateDate: 2018/12/26
 */
public class RuleYmlClient {

    public static void main(String[] args) throws FileNotFoundException {
        // create a rules engine
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);

        // create rules
        Rules rules = MVELRuleFactory.createRulesFrom(new FileReader("fizzbuzz.yml"));

        // fire rules
        Facts facts = new Facts();
        for (int i = 1; i <= 100; i++) {
            facts.put("number", i);
            fizzBuzzEngine.fire(rules, facts);
            System.out.println();
        }
    }
}
```

作者：HaleyLiu

链接：https://www.jianshu.com/p/41ea7a43093c

来源：简书

简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。