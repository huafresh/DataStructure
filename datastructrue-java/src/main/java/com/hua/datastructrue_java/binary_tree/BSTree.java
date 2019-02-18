package com.hua.datastructrue_java.binary_tree;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉搜索树
 *
 * @author hua
 * @version V1.0
 * @date 2019/2/18 14:55
 */
@SuppressWarnings("unchecked")
public class BSTree<T extends Comparable<T>> {

    public static final String KEY_IS_FIRST = "isFirst";
    protected TreeNode<T> root;
    private int size = 0;

    public void insert(TreeNode<T> node) {
        if (root == null) {
            root = node;
            return;
        }

        TreeNode<T> curNode = root;
        while (true) {
            if (node.value.compareTo(curNode.value) < 0) {
                if (curNode.left != null) {
                    curNode = curNode.left;
                } else {
                    node.parent = curNode;
                    curNode.left = node;
                    break;
                }
            } else {
                if (curNode.right != null) {
                    curNode = curNode.right;
                } else {
                    node.parent = curNode;
                    curNode.right = node;
                    break;
                }
            }
        }

        size++;
    }

    public TreeNode<T> delete(TreeNode<T> node) {
        if (root == null) {
            return null;
        }

        TreeNode<T> curNode = root;
        while (curNode != null) {
            if (node.value.compareTo(curNode.value) == 0) {
                return deleteNode(curNode);
            }
            if (node.value.compareTo(curNode.value) < 0) {
                if (curNode.left != null) {
                    curNode = curNode.left;
                }
            } else {
                if (curNode.right != null) {
                    curNode = curNode.right;
                }
            }
        }

        return null;
    }

    private TreeNode<T> deleteNode(TreeNode<T> node) {
        TreeNode<T> parent = node.parent;

        //处理叶子结点情况
        if (node.left == null && node.right == null) {
            if (parent.value.compareTo(node.value) < 0) {
                parent.right = null;
            } else {
                parent.left = null;
            }
        }
        //处理左右孩子都存在情况
        else if (node.left != null && node.right != null) {
            //寻找直接前驱
            TreeNode<T> preNode = node.left;
            while (preNode.right != null) {
                preNode = preNode.right;
            }

            //删除直接前驱
            deleteNode(node);

            //直接前驱替换当前删除的结点
            preNode.parent = parent;
            if (parent.value.compareTo(node.value) < 0) {
                parent.right = preNode;
            } else {
                parent.left = preNode;
            }

            return preNode;
        }
        //处理仅有一个孩子的情况
        else {
            if (parent.value.compareTo(node.value) < 0) {
                if (node.left != null) {
                    parent.right = node.left;
                } else {
                    parent.right = node.right;
                }
                return parent.right;
            } else {
                if (node.left != null) {
                    parent.left = node.left;
                } else {
                    parent.left = node.right;
                }
                return parent.left;
            }
        }

        return null;
    }

    protected boolean isLeftChild(TreeNode<T> node1, TreeNode<T> node2) {
        return node1.value.compareTo(node2.value) < 0;
    }


    /**
     * 层序遍历
     */
    public static <T extends Comparable<T>> void levelTraversal(TreeNode<T> node,
                                                                ITraversal<T> traversal) {
        if (node == null) {
            return;
        }
        Queue<TreeNode<T>> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            TreeNode<T> curNode = queue.poll();
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
            if (traversal != null) {
                traversal.onTraversal(curNode);
            }
        }
    }

    /**
     * 中序遍历
     */
    public TreeNode<T>[] middleTraversal() {
        if (root == null) {
            return null;
        }

        List<TreeNode<T>> result = new ArrayList<>();

        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }

            curNode = stack.pop();
            result.add(curNode);
            curNode = curNode.right;
        }

        return result.toArray(new TreeNode[]{});
    }

    /**
     * 后续遍历
     */
    public void behindTraversal(ITraversal<T> traversal) {
        behindTraversalInternal(root, traversal, null);
    }

    /**
     * 后续遍历，回调栈数据
     */
    public static <T extends Comparable<T>> void behindTraversalWithStack(
            TreeNode<T> node,
            ITraversalWithStack<T> traversal) {
        behindTraversalInternal(node, null, traversal);
    }


    private static <T extends Comparable<T>> void behindTraversalInternal(
            TreeNode<T> node,
            ITraversal<T> traversal,
            ITraversalWithStack<T> traversalWithStack) {

        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> curNode = node;
        while (curNode != null || !stack.isEmpty()) {

            while (curNode != null) {
                stack.push(curNode);
                curNode.setExtra(KEY_IS_FIRST, false);
                curNode = curNode.left;
            }

            TreeNode<T> topNode = stack.pop();
            if (Boolean.FALSE.equals(topNode.getExtra(KEY_IS_FIRST))) {
                stack.push(topNode);
                topNode.setExtra(KEY_IS_FIRST, true);
                curNode = topNode.right;
            } else {
                if (traversal != null) {
                    traversal.onTraversal(topNode);
                }
                if (traversalWithStack != null) {
                    traversalWithStack.onTraversal(stack, topNode);
                }
            }
        }

    }

    public static void sort(int[] data) {
        BSTree<Integer> bsTree = new BSTree<>();
        for (int value : data) {
            bsTree.insert(new TreeNode<Integer>(value));
        }

        TreeNode<Integer>[] result = bsTree.middleTraversal();
        for (int i = 0; i < result.length; i++) {
            data[i] = result[i].value;
        }
    }

    interface ITraversal<T extends Comparable<T>> {
        void onTraversal(TreeNode<T> node);
    }

    interface ITraversalWithStack<T extends Comparable<T>> {
        void onTraversal(Stack<TreeNode<T>> stack, TreeNode<T> node);
    }

}
