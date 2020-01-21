package com.ypren.niuke;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 有一个数组a[N]顺序存放0~N-1，要求每隔两个数删掉一个数，到末尾时循环至开头继续进行，求最后一个被删掉的数的原始下标位置。
 * 以8个数(N=7)为例:｛0，1，2，3，4，5，6，7｝，0->1->2(删除)->3->4->5(删除)->6->7->0(删除),如此循环直到最后一个数被删除。
 *
 * 解题思路：队列，形成一个循环队列，先入先出，每出队的第三个数删除，直到队列里只剩下最后一个数。
 */
public class Shanshu {
    private static final Queue<Integer> q = new LinkedList<Integer>();

    public static void main(String[] args) {
        int arraySize = 8;

        for (int i = 0; i < arraySize; i++) {
            q.add(i);
        }

        int count = 0;
        while (q.size() > 1) {
            int value = q.remove();
            if (count != 2) {
                q.add(value);
                count++;
            } else {
                count = 0;
            }
        }

        System.out.println(q.remove());
    }
}
