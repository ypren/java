package com.ypren.niuke;

public class ReferenceTest {

    class Value {
        public int i = 15;
    }

    public static void main(String argv[]) {
        ReferenceTest t = new ReferenceTest();
        t.first();
    }

    public void first() {
        int i = 5;
        Value v = new Value();
        v.i = 25;
        second(v, i);
        System.out.println(v.i);
    }

    public void second(Value tmp, int i) {
        i = 0;
        tmp.i = 20;
        Value val = new Value();
        tmp = val;
        System.out.println(tmp.i + " " + i);
    }
}
