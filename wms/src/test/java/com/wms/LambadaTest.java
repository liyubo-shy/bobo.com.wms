package com.wms;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: bo
 * @DATE: 2022/11/4 8:54
 **/
public class LambadaTest {

    public static void main(String[] args) {
        List<String> city = Arrays.asList("beijing", "shanghai", "tianjin", "wuhan");
        //使用lambada表达式遍历
        city.forEach(i -> System.out.println(i));
        //使用方法引用
        city.forEach(System.out::println);
    }
}
