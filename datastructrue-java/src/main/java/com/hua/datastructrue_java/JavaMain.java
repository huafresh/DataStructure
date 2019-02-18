package com.hua.datastructrue_java;



import com.hua.datastructrue_java.binary_tree.BSTree;

import java.util.Random;

/**
 * @author hua
 * @version V1.0
 * @date 2019/2/18 16:20
 */

public class JavaMain {

    public static void main(String[] args) {
        int[] data = new int[20];
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            data[i] = random.nextInt(200);
        }

        System.out.println("排序前：\n");
        printArray(data);
        BSTree.sort(data);
        System.out.println("排序后：\n");
        printArray(data);
    }

    private static void printArray(int[] data) {
        for (int i : data) {
            System.out.println(i);
        }
    }

}
