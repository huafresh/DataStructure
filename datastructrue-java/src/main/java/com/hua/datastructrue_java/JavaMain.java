package com.hua.datastructrue_java;



import com.hua.datastructrue_java.binary_tree.SearchTree;
import com.hua.datastructrue_java.binary_tree.TreeNode;

import java.util.Random;

/**
 * @author hua
 * @version V1.0
 * @date 2019/2/18 16:20
 */

public class JavaMain {

    private static int[] array = new int[]{
            97,
            38,
            73,
            192,
            87,
            196,
            162,
            132,
            10,
            80
//            112,
//            93,
//            51,
//            122,
//            20,
//            124,
//            188,
//            110,
//            43,
//            99,
    };

    public static void main(String[] args) {
        int[] data = new int[20];
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            data[i] = random.nextInt(200);
        }

//        System.out.println("before sort：\n");
//        printArray(data);
//        BSTree.sort(data);
//        System.out.println("after sort：\n");
//        printArray(data);

//        System.out.println("before rb sort:");
//        printArray(data);
//        SearchTree.sortWithRBTree(array);
//        System.out.println("after rb sort:");
//        printArray(array);

        SearchTree<Integer> st = new SearchTree<>();
        for (int i : array) {
            st.insert(new TreeNode<Integer>(i));
        }
        st.graphPrint();

//        System.out.println(" __20__ ");
//        System.out.println(" |     |");
//        System.out.println("12     40");
    }

    private static void printArray(int[] data) {
        for (int i : data) {
            System.out.println(i);
        }
    }

}
