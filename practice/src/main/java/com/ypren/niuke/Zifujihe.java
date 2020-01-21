package com.ypren.niuke;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 输入一个字符串，求出该字符串包含的字符集合
 */
public class Zifujihe {

    private static final Queue<Character> q = new LinkedList<Character>();

    public static void main(String[] args) {
        String original = "abcZqBweracbA";
        for (int i = 0; i < original.length(); i++) {
            if (!q.contains(original.charAt(i))) {
                q.add(original.charAt(i));
            }
        }

        while (q.size() > 0) {
            System.out.print(q.remove());
        }
    }
}
