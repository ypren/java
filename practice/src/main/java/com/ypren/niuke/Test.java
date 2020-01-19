package com.ypren.niuke;

public class Test {
    final int i;
    int j;

    public  Test() {
        i = 0;
    }

    public void doSomething() {
        System.out.println(++j + i);
    }
}
