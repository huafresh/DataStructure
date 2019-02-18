package com.hua.datastructrue_java.binary_tree;

import java.util.Stack;

/**
 * 红黑树
 *
 * @author hua
 * @version V1.0
 * @date 2019/2/18 16:47
 */

public class RBTree<T extends Comparable<T>> extends BSTree<T> {

    @Override
    public void insert(TreeNode<T> node) {
        RBTreeNode<T> rbTreeNode = new RBTreeNode<>(node.value);
        //rbTreeNode.left = new RBTreeNode<>(null, true);
        //rbTreeNode.right = new RBTreeNode<>(null, true);
        super.insert(rbTreeNode);
        fixUpInsert(rbTreeNode);
    }

    @Override
    public TreeNode<T> delete(TreeNode<T> node) {
        TreeNode<T> newNode = super.delete(node);
        if (newNode != null && newNode instanceof RBTree.RBTreeNode) {
            fixUpDelete((RBTreeNode<T>) newNode);
        }

        return newNode;
    }

    private void fixUpDelete(RBTreeNode<T> node) {

        RBTreeNode<T> curNode = node;
        while (true) {
            //黑+红，直接设为黑色
            if (curNode.isRed()) {
                curNode.color = COLOR.BLACK;
                return;
            }

            //黑+黑，且为根，不调整
            if (curNode.isBlack() && curNode.parent == null) {
                return;
            }

            RBTreeNode<T> parent = (RBTreeNode<T>) curNode.parent;
            RBTreeNode<T> brother;
            if (isLeftChild(curNode, parent)) {
                brother = (RBTreeNode<T>) parent.right;
            } else {
                brother = (RBTreeNode<T>) parent.left;
            }
            RBTreeNode<T> brotherLeft = (RBTreeNode<T>) brother.left;
            RBTreeNode<T> brotherRight = (RBTreeNode<T>) brother.right;

            //处理四个case
            if (brother.isRed()) {
                brother.color = COLOR.BLACK;
                parent.setRed();
                leftRotate(parent);
            } else if (brother.isBlack() && brotherLeft.isBlack() && brotherRight.isBlack()) {
                brother.setRed();
                curNode = parent;
            } else if (brother.isBlack() && brotherLeft.isRed() && brotherRight.isBlack()) {
                brotherLeft.setBlack();
                brother.setRed();
                rightRotate(brother);
            } else if (brother.isBlack() && brotherRight.isRed()) {
                brother.color = parent.color;
                parent.setBlack();
                brotherRight.setBlack();
                leftRotate(parent);
            }
        }

    }

    private void fixUpInsert(RBTreeNode<T> node) {
        RBTreeNode<T> curNode = node;
        while (true) {
            RBTreeNode<T> parent = (RBTreeNode<T>) curNode.parent;

            //根不需要调整
            if (node.parent == null) {
                break;
            }

            RBTreeNode<T> uncle = findUncle(parent);
            RBTreeNode<T> grandParent = (RBTreeNode<T>) parent.parent;

            //父结点为黑不用调整
            if (parent.color == COLOR.BLACK) {
                break;
            }

            if (curNode.color == null) {
                curNode.setRed();
            }

            //三个case情况
            if (uncle.isRed()) {
                parent.color = COLOR.BLACK;
                uncle.color = COLOR.BLACK;
                grandParent.color = COLOR.RED;
                curNode = grandParent;
            } else if (uncle.isBlack() && !isLeftChild(curNode, parent)) {
                curNode = parent;
                leftRotate(curNode);
            } else if (uncle.isBlack() && isLeftChild(curNode, parent)) {
                parent.color = COLOR.BLACK;
                grandParent.color = COLOR.RED;
                rightRotate(grandParent);
            }
        }

    }

    private boolean checkRbTreeIllegal() {
        if (root == null) {
            return true;
        }

        RBTreeNode node = (RBTreeNode) root;
        if (node.isRed()) {
            return false;
        }

        BSTree.levelTraversal(root, new ITraversal<T>() {
            @Override
            public void onTraversal(TreeNode<T> node) {
                //把当前结点当成根结点进行后序遍历，回调的栈的内容就是页结点
                //到当前结点的路径
                BSTree.behindTraversalWithStack(node, new ITraversalWithStack<T>() {
                    @Override
                    public void onTraversal(Stack<TreeNode<T>> stack, TreeNode<T> node) {
                        //判断结点为叶结点后统计栈中黑色结点的个数，如果出现不一致，则红黑树校验失败
                        if (node.left == null && node.right == null) {
                            Stack<TreeNode<T>> temp = new Stack<>();
                            while (!stack.isEmpty()){
                                TreeNode<T> pop = stack.pop();

                                temp.push()
                            }
                        }

                    }
                });

            }
        });

    }

    private RBTreeNode<T> findUncle(RBTreeNode<T> parent) {
        RBTreeNode<T> uncle;
        if (parent.value.compareTo(parent.parent.value) < 0) {
            uncle = (RBTreeNode<T>) parent.parent.right;
        } else {
            uncle = (RBTreeNode<T>) parent.parent.left;
        }
        return uncle;
    }

    private void leftRotate(RBTreeNode<T> node) {
        TreeNode<T> temp = node.right.left;
        node.right.left = node;
        node.right = temp;
    }

    private void rightRotate(RBTreeNode<T> node) {
        TreeNode<T> temp = node.left.right;
        node.left.right = node;
        node.left = temp;
    }

    enum COLOR {
        RED,
        BLACK
    }

    static class RBTreeNode<T extends Comparable<T>> extends TreeNode<T> {
        COLOR color;
        boolean isNIL;

        RBTreeNode(T value) {
            this(value, false);
        }

        RBTreeNode(T value, boolean nil) {
            super(value);
            this.isNIL = nil;
        }

        boolean isRed() {
            if (color == null) {
                throw new IllegalStateException("color is illegal");
            }
            return color == COLOR.RED;
        }

        boolean isBlack() {
            if (color == null) {
                throw new IllegalStateException("color is illegal");
            }
            return color == COLOR.BLACK;
        }

        void setRed() {
            color = COLOR.RED;
        }

        void setBlack() {
            color = COLOR.BLACK;
        }
    }

}
