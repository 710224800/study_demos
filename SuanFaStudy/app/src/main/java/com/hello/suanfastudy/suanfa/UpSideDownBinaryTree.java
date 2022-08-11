package com.hello.suanfastudy.suanfa;

import com.hello.suanfastudy.TreeNode;
import com.hello.suanfastudy.Util;

/**
 * Created by lyhao on 2021/12/2.
 */
public class UpSideDownBinaryTree {
    public static void main(String[] args) {
        int none = Integer.MIN_VALUE;
        int[] array = {1,2,3,4,5};
        TreeNode root = Util.array2BinTree(array);
        Util.levelVisit2(root);
        System.out.println("\n");
        Util.levelVisit2(upsideDownBinaryTree(root));
    }

    // 给定一个二叉树，其中所有的右节点要么是具有兄弟节点（拥有相同父节点的左节点）的叶节点，要么为空，(意思是肯定有左节点)
    // 将此二叉树上下翻转并将它变成一棵树， 原来的右节点将转换成左叶节点。返回新的根。

    // 上下翻转二叉树
    public static TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        TreeNode temp = root.left;
        TreeNode ret = upsideDownBinaryTree(temp);
        temp.left = root.right;
        temp.right = root;
        root.left = null;
        root.right = null;
        return ret;
    }
}
