package com.stormstruct.sort;


/**
 * Author: changdalin
 * Date: 2018/2/28
 * Description:
 **/
public class MergeMethod {

    public static void sort(int[] a, int left, int right) {
        //当left>=right的时，已经不需要再划分了
        if (left < right) {
            int middle = (left + right) / 2;
            sort(a, left, middle);          //左子数组
            sort(a, middle + 1, right);       //右子数组
            merge(a, left, middle, right);    //合并两个子数组
        }
    }

    // 合并两个 有序子序列  arr[left, ..., middle] 和 arr[middle+1, ..., right] temp是辅助数组
    // 所以有空间占用
    private static void merge(int arr[], int left, int middle, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = middle + 1;
        //temp的index
        int k = 0;
        //将记录由小到大地放进temp数组
        //arr[i,middle] arr[middle+1,j] 同时放入arr[left,right]里面
        while (i <= middle && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= middle) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        //这里有一个 temp数组 进入 arr的过程
        //把数据复制回原数组
        for (i = 0; i < k; ++i) {
            arr[left + i] = temp[i];
        }
    }

    public static void main(String[] args) {
        int[] data = new int[]{10, 30, 20, 15};
        sort(data, 0, data.length - 1);
        for (int i : data) {
            System.out.println(i);
        }
    }
}
