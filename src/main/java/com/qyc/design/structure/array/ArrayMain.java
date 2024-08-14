package com.qyc.design.structure.array;

import java.util.Arrays;

public class ArrayMain {

    public static void main(String[] args) {
        int[] a = new int[10];
        int[] b = new int[]{1,2,3,4,5,6,7,8,9,10};
        a[9] = 9;
        b[2] = 2;
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
    }

}
