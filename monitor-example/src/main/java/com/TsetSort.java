package com;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TsetSort {
    public static void main(String[] args) {


        Stream.of("3", "1", "5", "4", "2").parallel()
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s;
                }).collect(Collectors.toList())
                .forEach(s -> System.out.println("forEach: " + s));
    }
}
