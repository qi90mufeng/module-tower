package com.albert.utils;

import com.albert.utils.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * @Author: zw
 * @Date: 19/1/10
 */
public class TreeSetTest {


    public static void main(String[] args) {

        Person person1=new Person(1,"张三1",1,79);
        Person person2=new Person(2,"张三2",2,79);
        Person person3=new Person(3,"张三3",9,79);
        Person person4=new Person(4,"张三4",4,79);
        Person person5=new Person(5,"张三5",5,79);
        Person person6=new Person(6,"张三6",6,79);
        Person person7=new Person(7,"张三7",7,79);
        Person person11=new Person(11,"张三11",10,79);

        Person person8=new Person(4,"张三3",9,80);
        Person person9=new Person(2,"张三9重复",7,79);
        Person person10=new Person(2,"张三10重复",9,79);


        List<Person> personList=new ArrayList<>();
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        personList.add(person5);
        personList.add(person6);
        personList.add(person7);
        personList.add(person8);
        personList.add(person9);
        personList.add(person10);
        personList.add(person11);

        personList.add(person11);

        TreeSet<Person> treePerson=new TreeSet();

        personList.forEach(item->{
            treePerson.add(item);
        });

        for (Person person:treePerson){
            System.out.println(person.getId() +":  "+person.getAge()+":  "+person.getName()+":  "+person.getScore());
        }

    }



}
