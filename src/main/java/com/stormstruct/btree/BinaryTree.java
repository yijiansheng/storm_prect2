package com.stormstruct.btree;

/**
 * Author: changdalin
 * Date: 2017/9/20
 * Description:
 **/

class TwoNode {
    String val;
    TwoNode left;
    TwoNode right;

    public TwoNode(String val) {
        this.val = val;
    }
}

class TwoTree {
    public TwoNode init() {
        TwoNode root = new TwoNode("A");
        TwoNode b = new TwoNode("B");
        TwoNode c = new TwoNode("C");
        TwoNode d = new TwoNode("D");
        TwoNode e = new TwoNode("E");
        TwoNode f = new TwoNode("F");
        TwoNode g = new TwoNode("G");
        TwoNode h = new TwoNode("H");
        TwoNode i = new TwoNode("I");
        TwoNode j = new TwoNode("J");
        TwoNode k = new TwoNode("K");
        TwoNode l = new TwoNode("L");
        root.left = b;
        root.right = c;
        b.left = d;
        b.right = e;
        e.left = f;
        e.right = g;
        c.left = h;
        c.right = i;
        i.left = j;
        i.right = k;
        k.right = l;
        return root;
    }


    /**
     * date:2017/9/20
     * description:前序遍历 根左右
     * 递归的时候，只需要想root和最下面的叶子结点
     */
    public void frontPrint(TwoNode node) {
        if (node == null) {
            return;
        }
        //这里是处理根节点，可以加入到一个list里面等
        System.out.println(node.val);
        frontPrint(node.left);
        frontPrint(node.right);
    }


    /**
     * date:2017/9/20
     * description:中序遍历
     */
    public void zhongPrint(TwoNode node) {
        if (node == null) {
            return;
        }
        /**
         * date:2017/9/20
         * description:一定先往左找到最下面的节点，才会sysout
         * 遇到有左右子树的节点，先向底下
         */
        zhongPrint(node.left);
        System.out.println(node.val);
        zhongPrint(node.right);
    }


    /**
     * date:2017/9/20
     * description:后序遍历,左右根
     */
    public void houPrint(TwoNode node) {
        if (node == null) {
            return;
        }
        houPrint(node.left);
        houPrint(node.right);
        System.out.println(node.val);
    }


    /**
     * date:2017/9/20
     * description:求节点个数
     * 注意，这种累加的，比大小的，都是从下向上走的
     */
    public int getNodeNum(TwoNode node) {
        if (node == null) {
            return 0;
        }
        int num = 1;
        int leftNum = getNodeNum(node.left);
        int rightNum = getNodeNum(node.right);
        num = num + leftNum + rightNum;
        return num;
    }


    /**
     * date:2017/9/20
     * description:叶子结点个数
     */
    public int getLeafNum(TwoNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        int leftNum = getLeafNum(node.left);
        int rightNum = getLeafNum(node.right);
        return leftNum + rightNum;
    }

    /**
     * date:2017/9/20
     * description:k层个数。每下一层，只有当k减到1，才计做1
     * k若再减，就不算了
     */
    public int getKNum(TwoNode node, int k) {
        if (node == null || k <= 0) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        int leftNum = getKNum(node.left, k - 1);
        int rightNum = getKNum(node.right, k - 1);
        return leftNum + rightNum;
    }

    /**
     * date:2017/9/20
     * description:最大深度
     */
    public int getMaxHeight(TwoNode node) {
        if (node == null) {
            return 0;
        }
        int height = 1;
        /**
         * date:2017/9/20
         * description:只是不再加了，而是用较大函数
         */
        int leftHeight = getMaxHeight(node.left);
        int rightHeight = getMaxHeight(node.right);
        height = height + Math.max(leftHeight, rightHeight);
        return height;
    }

    /**
     * date:2017/9/20
     * description:最大深度2 一样，node.left==null 就返回1
     */
    public int getMaxHeight2(TwoNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        int leftHeight = getMaxHeight2(node.left);
        int rightHeight = getMaxHeight2(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * date:2017/9/20
     * description:最小深度
     */
    public int getMinHeight(TwoNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        int leftHeight = getMinHeight(node.left);
        int rightHeight = getMinHeight(node.right);
        return Math.min(leftHeight, rightHeight) + 1;
    }

    /**
     * date:2017/9/20
     * description:是否是平衡二叉树
     * 高度差<2
     * 最大高度-最小高度
     */
    public boolean isBalanced(TwoNode node) {
        int maxHeight2 = getMaxHeight2(node);
        int minHeight = getMinHeight(node);
        return maxHeight2 - minHeight > 1 ? false : true;
    }

    /**
     * date:2017/9/20
     * description:两个二叉树是否equal
     */
    public boolean isEqual(TwoNode n1, TwoNode n2) {
        //比到最后一层
        if (n1 == null && n2 == null) {
            return true;
        } else if (n1 == null || n2 == null) {
            //两个有一个为空，就false了
            return false;
        }
        //两个都不为false
        if (n1.val.equals(n2.val)) {
            return true;
        }
        /**
         * date:2017/9/20
         * description:这里，如果要比较镜像，就换一个n2.right
         */
        boolean left = isEqual(n1.left, n2.left);
        boolean right = isEqual(n1.right, n2.right);
        return left && right;
    }


    /**
     * date:2017/9/20
     * description:翻转
     */
    public TwoNode inverse(TwoNode node) {
        if (node == null) {
            return null;
        }
        inverse(node.left);
        inverse(node.right);
        TwoNode tmp = node.right;
        node.right = node.left;
        node.left = tmp;
        return node;
    }


}

public class BinaryTree {
    public static void main(String[] args) {
        TwoTree btree = new TwoTree();
        TwoNode root = btree.init();
        //btree.frontPrint(root);
        //btree.zhongPrint(root);
        //btree.houPrint(root);
        //System.out.println(btree.getNodeNum(root));
        //System.out.println(btree.getLeafNum(root));
        //System.out.println(btree.getKNum(root, 5));
        //System.out.println(btree.getMaxHeight2(root));
        //System.out.println(btree.getMinHeight(root));
        //System.out.println(btree.isBalanced(root));
        btree.frontPrint(btree.inverse(root));
    }
}
