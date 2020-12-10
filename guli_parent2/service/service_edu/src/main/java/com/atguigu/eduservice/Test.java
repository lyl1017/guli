package com.atguigu.eduservice;

public class Test {
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("abcdef");
        System.out.println(buffer.reverse());

        StringBuilder builder = new StringBuilder();
        builder.append("123456");
        System.out.println(builder.reverse());
    }
}
