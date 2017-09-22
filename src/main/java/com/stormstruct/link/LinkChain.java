package com.stormstruct.link;

/**
 * Author: changdalin
 * Date: 2017/9/22
 * Description:
 **/

class LNode {
    LNode next;
    int val;

    public LNode(int value) {
        val = value;
    }
}


class LinkList {
    public LNode init() {
        LNode l1 = new LNode(3);
        LNode l2 = new LNode(1);
        LNode l3 = new LNode(5);
        LNode l4 = new LNode(2);
        LNode l5 = new LNode(20);
        LNode l6 = new LNode(11);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = null;
        return l1;
    }

    /**
     * date:2017/9/22
     * description: 打印
     */
    public void printLink(LNode head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }


    /**
     * date:2017/9/22
     * description:reverse,tail元素是指向null
     * 就调整一个next指针，让当前的next指针，指向prev。
     * 然后移动node。prev node都要重新赋值 node要=node.next prev要=node
     */
    public LNode reverse(LNode head) {
        LNode prev = null;
        while (head != null) {
            LNode nextNode = head.next;
            head.next = prev;
            prev = head;
            head = nextNode;
        }
        return prev;
    }


    /**
     * date:2017/9/22
     * description:判断是否闭合
     * 注意，出现了null，一定没有环,null代表到了最后了
     */
    public boolean isBihe(LNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        LNode fast, slow;
        fast = head.next;
        slow = head;
        while (fast != slow) {
            if (fast == null || fast.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;
    }


    /**
     * date:2017/9/22
     * description:list长度
     */
    public int getLength(LNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    /**
     * date:2017/9/22
     * description:sort
     */
    public void quickSort(LNode begin, LNode end) {
        if (begin == end || begin == null) {
            return;
        }
        LNode index = qiefen(begin, end);
        quickSort(begin, index);
        quickSort(index.next, end);
    }


    public LNode qiefen(LNode begin, LNode end) {
        if (begin == null || begin == end) {
            return begin;
        }
        //拿最前面这个值
        int val = begin.val;
        LNode index = begin;
        //从下一个数开始,走一遍
        LNode cur = begin.next;
        while (cur != end) {
            //如果后面的值，小于标杆值
            // index  cur
            // 3       1     5    2
            if (cur.val < val) {
                //index后移一位
                index = index.next;

                int tmp = cur.val;
                cur.val = index.val;
                index.val = tmp;
            }
            cur = cur.next;
        }
        begin.val = index.val;
        index.val = val;

        return index;
    }

    public LNode getEndNode(LNode head) {
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }

}

public class LinkChain {
    public static void main(String[] args) {
        LinkList list = new LinkList();
        LNode head = list.init();
        //LNode newHead = list.reverse(head);
        //list.printLink(newHead);
        //System.out.println(list.isBihe(head));
        //System.out.println(list.getLength(head));
        LNode tail = list.getEndNode(head);
        list.quickSort(head, tail);
    }
}
