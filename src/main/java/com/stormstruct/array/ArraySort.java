package com.stormstruct.array;

/**
 * Author: changdalin
 * Date: 2017/9/25
 * Description:
 **/
public class ArraySort {


    /**
     * date:2017/9/25
     * description:
     */
    public void sort1(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int standard = a[left];
        int i = left;
        int j = right;
        //当i<j这一趟没有排完
        while (i < j) {
            while (i < j && a[j] >= standard) {
                j--;
            }
            while (i < j && a[i] <= standard) {
                i++;
            }
            //注意，在夹闭过程中,i加j减,那就说明不用交换了
            if (i < j) {
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
            }
        }
        //这一趟排完了，那么要把基准值放在合适的位置
        a[left] = a[i];
        a[i] = standard;
        sort1(a, left, i);
        sort1(a, i + 1, right);
    }

    public static void main(String[] args) {
        int[] array = new int[]{6, 1, 9, 2, 3, 2, 10, 11};
        new ArraySort().sort1(array, 0, 7);
        for (int temp : array) {
            System.out.println(temp);
        }
    }
}
