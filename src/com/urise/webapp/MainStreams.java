package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        int[] values = {1, 8, 2, 2, 3, 1};
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 1, 1, 2, 2, 1));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 1, 1, 2, 2));

        System.out.println((minValue(values)));
        System.out.println("Even - " + (oddOrEven(list)));
        System.out.println("Odd - " + (oddOrEven(list2)));

    }


    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {

        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 != 0));

        if (map.get(true).size() % 2 != 0) {
            return map.get(false);
        } else {
            return map.get(true);
        }
    }
}
