package com.hello.suanfastudy;

import java.util.LinkedList;

/**
 * Created by lyhao on 2021/12/2.
 */
public class Util {
    // 层次遍历结构 数组转二叉树
    public static TreeNode array2BinTree(int[] array) {
        if (array.length == 0) {
            return null;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        int index = 0;
        TreeNode root = new TreeNode(array[index++]);
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur == null) {
                continue;
            }
            if (index < array.length) {
                int val = array[index++];
                if (val != Integer.MIN_VALUE) { // 空结点不入队
                    cur.left = new TreeNode(val);
                    queue.add(cur.left);
                }

            }
            if (index < array.length) {
                int val = array[index++];
                if (val != Integer.MIN_VALUE) { // 空结点不入队
                    cur.right = new TreeNode(val);
                    queue.add(cur.right);
                }
            }
        }
        return root;
    }

    //层次遍历二叉树
    public static void levelVisit(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.value);
            System.out.print("  ");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }
    //层次遍历二叉树2
    public static void levelVisit2(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (queue != null) {
            LinkedList<TreeNode> nextQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                System.out.print(node.value);
                System.out.print(" ");
                if (node.left != null) {
                    nextQueue.add(node.left);
                }
                if (node.right != null) {
                    nextQueue.add(node.right);
                }
            }
            if (nextQueue.size() > 0) {
                queue = nextQueue;
                System.out.println(" ");
            } else {
                queue = null;
            }
        }
    }
}
