package com.albert.utils;

/**
 * @Author: zw
 * @Date: 18/12/10
 */
public class Person implements Comparable<Person>{
    private Integer id;

    private String name;

    private Integer age;

    private Integer score;

    public Person(Integer id, String name, Integer age,Integer score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score=score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public int compareTo(Person o) {
        if (o.getId().compareTo(this.getId())==0){
            return 0;
        }
        if (o.getAge().compareTo(this.getAge())==0){
            return 1;
        }
        //通过ID 去重，根据年龄逆序
        return o.getAge().compareTo(this.getAge());
    }

}
