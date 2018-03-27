package com.stormstruct.sort;

/**
 * Author: changdalin
 * Date: 2018/2/28
 * Description:
 **/
public class QuickMethod {

    public static void qsort(int[] arr, int left, int right) {
        if (left < right) {
            int pivot = partition(arr, left, right);        //将数组分为两部分
            qsort(arr, left, pivot - 1);                   //递归排序左子数组
            qsort(arr, pivot + 1, right);                  //递归排序右子数组
        }
    }


    private static int partition(int[] arr, int left, int right) {
        // 选第一个为基准
        //拿出来做temp
        int pivot = arr[left];     //基准记录
        // 循环条件，只要left小于右就循环
        while (left < right) {
            //改--right,然后arr[right]换到arr[left]
            while (left < right && arr[right] >= pivot) --right;
            arr[left] = arr[right];             //交换比基准小的记录到左端
            //改 ++left，然后arr[left]换到刚才arr[right]那个位置
            while (left < right && arr[left] <= pivot) ++left;
            arr[right] = arr[left];           //交换比基准大的记录到右端
        }
        //扫描完成，基准到位
        arr[left] = pivot;
        //返回的是基准的位置
        return left;
    }

    public static void main(String[] args) {
        int[] data = new int[]{10, 30, 20, 60, 40, 15};
        qsort(data, 0, data.length - 1);
        for (int i : data) {
            System.out.println(i);
        }
    }
}
