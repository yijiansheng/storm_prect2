package com.stormstruct.btree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Author: changdalin
 * Date: 2017/9/21
 * Description:
 **/
class IntegerNode {
    int val;
    IntegerNode left;
    IntegerNode right;

    public IntegerNode(int val) {
        this.val = val;
    }
}

class IntegerTree {
    //初始化root
    public IntegerNode init() {
        IntegerNode root = new IntegerNode(11);
        return root;
    }

    public void frontPrint(IntegerNode node) {
        if (node == null) {
            return;
        }
        //这里是处理根节点，可以加入到一个list里面等
        System.out.println(node.val);
        frontPrint(node.left);
        frontPrint(node.right);
    }

    public void batchInsert(IntegerNode root) {
        insert(root, 6);
        insert(root, 20);
        insert(root, 9);
        insert(root, 2);
        insert(root, 24);
    }

    /**
     * date:2017/9/21
     * description:排序搜索树插入,root的指针是不能动的
     * 左面的小于中间，右面的大于
     * 肯定是落在一个null节点上
     */

    public void insert(IntegerNode root, int value) {
        if (root.val == value) {
            return;
        }
        IntegerNode temp = root;
        IntegerNode last = null;
        /**
         * date:2017/9/21
         * description:for循环的作用是找到null之前的位置,即找到叶子结点的位置
         */
        for (; ; ) {
            //每次循环之前，先记录temp的位置
            last = temp;
            if (value < temp.val) {
                temp = temp.left;
            } else if (value == temp.val) {
                temp = null;
                last = null;
                return;
            } else {
                temp = temp.right;
            }
            if (temp == null) {
                break;
            }
        }
        IntegerNode newNode = new IntegerNode(value);
        if (value > last.val) {
            last.right = newNode;
        } else {
            last.left = newNode;
        }
    }

    /**
     * date:2017/9/21
     * description:路径是root到leaf的,不是任意位置的
     */
    void getPath(IntegerNode root, int i) {
        if (root == null) {
            return;
        }
        //需要一个栈
        Stack<Integer> stack = new Stack<Integer>();
        //一个当前value
        int currentSum = 0;
        findPath(root, i, stack, currentSum);
    }

    /**
     * date:2017/9/21
     * description:注意,还原现场的时候,currentSum是值传递
     * 每进入一个方法内，当方法闭合返回之后,外面的currentSum还是那个值
     */
    void findPath(IntegerNode node, int goal, Stack<Integer> stack, int currentSum) {
        currentSum += node.val;
        stack.push(node.val);
        //leaf结点
        if (node.left == null && node.right == null) {
            if (currentSum == goal) {
                for (int path : stack) {
                    System.out.println(path);
                }
            }
        }
        if (node.left != null) {
            findPath(node.left, goal, stack, currentSum);
        }
        if (node.right != null) {
            findPath(node.right, goal, stack, currentSum);
        }
        //root是最后pop的
        stack.pop();
    }

    void getRandomPath(IntegerNode root, int goal) {
        if (root == null) {
            return;
        }
        Stack<Integer> stack = new Stack<Integer>();
        //一个当前value
        int currentSum = 0;
        createPath(root, goal, stack, currentSum);
    }

    private void createPath(IntegerNode node, int goal, Stack<Integer> stack, int currentSum) {
        currentSum += node.val;
        stack.push(node.val);
        if (currentSum == goal) {
            for (int path : stack) {
                System.out.println(path);
            }
        }
        if (node.left != null) {
            createPath(node.left, goal, stack, currentSum);
        }
        if (node.right != null) {
            createPath(node.right, goal, stack, currentSum);
        }
        currentSum -= node.val;
        stack.pop();
        if (node.left != null) {
            createPath(node.left, goal, stack, currentSum);
        }
        if (node.right != null) {
            createPath(node.right, goal, stack, currentSum);
        }
    }

    /**
     * date:2017/9/21
     * description:某个值是否存在
     */
    public boolean isExist(IntegerNode root, int value) {
        if (root == null) {
            return false;
        }
        if (root.val == value) {
            return true;
        }
        boolean leftExist = false;
        boolean rightExist = false;
        //这样可以减少递归的次数
        if (root.val < value) {
            rightExist = isExist(root.right, value);
        } else {
            leftExist = isExist(root.left, value);
        }
        return rightExist || leftExist;
    }


    /**
     * date:2017/9/21
     * description:是否存在于区间[k1,k2]
     * 只会有node>k1 和 node< k2 两种情况，
     */
    public void getExistSec(IntegerNode root, int k1, int k2) {
        if (root == null) {
            return;
        }
        if (root.val < k2 && root.val > k1) {
            System.out.println(root.val);
        }
        //去左子树寻找区间内的值，因为root
        getExistSec(root.left, k1, k2);
        getExistSec(root.right, k1, k2);
    }

    /**
     * date:2017/9/21
     * description:顺序加入,需要借助队列
     * 如果左节点不为空，加入队列，右节点不为空，加入队列
     * 不能递归
     */
    public void shunxuPrint(IntegerNode root) {
        if (root == null) {
            return;
        }
        LinkedList<IntegerNode> queue = new LinkedList<IntegerNode>();
        IntegerNode current = null;
        queue.offer(root);
        //只要不为空，就poll()出来一个
        while (!queue.isEmpty()) {
            current = queue.poll();
            System.out.print(current.val + "  ");
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }

    /**
     * date:2017/9/21
     * description:
     */
    public ArrayList<ArrayList<Integer>> cengciPrint(IntegerNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (root == null) {
            return result;
        }
        LinkedList<IntegerNode> queue = new LinkedList<IntegerNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                IntegerNode temp = queue.poll();
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
                level.add(temp.val);
            }
            result.add(level);
        }
        return result;
    }

}

public class BinaryTreeInteger {
    public static void main(String[] args) {
        IntegerTree btree = new IntegerTree();
        IntegerNode root = btree.init();
        btree.batchInsert(root);
        //btree.frontPrint(root);
        //btree.getPath(root, 19);
        //btree.getRandomPath(root,37);
        //System.out.println(btree.isExist(root, 20));
        //btree.getExistSec(root, 1, 10);
        //btree.shunxuPrint(root);
        System.out.println(btree.cengciPrint(root));
    }
}