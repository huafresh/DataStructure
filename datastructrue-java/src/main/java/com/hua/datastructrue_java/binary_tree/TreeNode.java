package com.hua.datastructrue_java.binary_tree;

import java.util.HashMap;

/**
 * @author hua
 * @version V1.0
 * @date 2019/2/18 14:54
 */

class TreeNode<T extends Comparable<T>> {

    TreeNode<T> parent;
    TreeNode<T> left;
    TreeNode<T> right;
    T value;
    private HashMap<String, Object> extras;

    TreeNode(T value) {
        this.value = value;
    }

    void setExtra(String key, Object value) {
        if (extras == null) {
            extras = new HashMap<>();
        }
        extras.put(key, value);
    }

    Object getExtra(String key) {
        if (extras != null) {
            return extras.get(key);
        }
        return null;
    }
}
