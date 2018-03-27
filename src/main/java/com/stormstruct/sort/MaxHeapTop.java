package com.stormstruct.sort;

/**
 * Author: changdalin
 * Date: 2018/2/28
 * Description:
 **/

/**
 * date:2018/2/28
 * description:最大堆可以找到 一群数中最小的topN
 * 头节点是最大的
 */
class MaxHeap {

    private int[] data;


    // 将一个数组传入构造方法，并转换成一个小根堆
    public MaxHeap(int[] data) {
        this.data = data;
        buildHeap();
    }

    // 将数组转换成最大堆
    private void buildHeap() {
        // 完全二叉树只有数组下标小于或等于 (data.length) / 2 - 1 的元素有孩子结点，遍历这些结点。
        // 从中间向前组成，这样最前面的那个一定是最小的。
        for (int i = (data.length) / 2 - 1; i >= 0; i--) {
            // 对有孩子结点的元素heapify
            heapify(i);
        }
    }

    //构建
    private void heapify(int i) {
        // 获取左右结点的数组下标
        int r = right(i);
        int l = left(i);
        // 这是一个临时变量，表示 跟当前节点、左结点、右结点中最小的值的结点的下标
        int biggest = i;

        // 存在左结点，左节点大于 当前节点
        if (l < data.length && data[l] > data[i])
            biggest = l;

        // 存在右结点，且 右结点的值 大于 以上比较的较小值
        if (r < data.length && data[r] > data[biggest])
            biggest = r;

        // 左右结点的值都大于根节点，直接return，不做任何操作
        // biggest代表数组中的一个位置，那么它的子节点可能受到影响
        if (i == biggest)
            return;

        // 交换根节点和左右结点中最小的那个值，把根节点的值替换下去
        swap(i, biggest);

        // 由于替换后左右子树会被影响，所以要对受影响的子树再进行heapify
        heapify(biggest);
    }

    // 获取右结点的数组下标
    private int right(int i) {
        return (i + 1) << 1;
    }

    // 获取左结点的数组下标
    private int left(int i) {
        return ((i + 1) << 1) - 1;
    }

    // 交换元素位置
    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    // 获取堆中的最大的元素，根元素
    public int getRoot() {
        return data[0];
    }

    // 替换根元素，并重新heapify
    public void setRoot(int root) {
        data[0] = root;
        heapify(0);
    }

}

public class MaxHeapTop {
    public static void main(String[] args) {
        // 源数据
        int[] data = {1, 275, 12, 6, 45, 478, 41, 2, 456, 12, 3, 4};

        int[] top5 = topMinK(data, 5);

        for (int i = 0; i < 5; i++) {
            System.out.println(top5[i]);
        }
    }

    // 从data数组中获取最小的k个数
    private static int[] topMinK(int[] data, int k) {
        // 先取K个元素放入一个数组topk中
        // 主要就是靠这个数组
        int[] topk = new int[k];
        for (int i = 0; i < k; i++) {
            topk[i] = data[i];
        }

        // 转换成最大堆
        MaxHeap heap = new MaxHeap(topk);

        // 从k开始，遍历data
        for (int i = k; i < data.length; i++) {
            int root = heap.getRoot();

            // 当数据小于堆中最小的数（根节点）时，替换堆中的根节点，再转换成堆
            if (data[i] < root) {
                heap.setRoot(data[i]);
            }
        }
        return topk;
    }
}
