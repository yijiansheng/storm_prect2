package com.stormstruct.bitset;

import java.util.BitSet;

/**
 * Author: changdalin
 * Date: 2018/3/2
 * Description:
 **/
public class BitsetTest {

    /**
     * 求一个字符串包含的char
     */
    public static void containChars(String str) {
        BitSet used = new BitSet();
        for (int i = 0; i < str.length(); i++)
            used.set(str.charAt(i)); // set bit for char

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = used.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            if (used.get(i)) {
                sb.append((char) i);
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }



    /**
     * 进行数字排序
     */
    public static void sortArray() {
        int[] array = new int[]{423, 700, 9999, 2323, 356, 6400, 1, 2, 3, 2, 2, 2, 2};
        BitSet bitSet = new BitSet();

        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i]);
        }
        //剔除重复数字后的元素个数
        int bitLen = bitSet.cardinality();
        //这个是有 多少bit位 一定是64的整数倍
        System.out.println(bitSet.size());
        //有多少位true的数，实际的数
        System.out.println(bitSet.cardinality());

        //直接迭代BitSet中bit为true的元素
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.print(i + "\t");
        }
    }

    public static void main(String[] args) {
        sortArray();
    }
}
