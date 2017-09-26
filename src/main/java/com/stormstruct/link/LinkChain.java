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
    LNode l1;
    LNode l2;
    LNode l3;
    LNode l4;
    LNode l5;
    LNode l6;
    LNode l7;

    public LNode init() {
        l1 = new LNode(3);
        l2 = new LNode(1);
        l3 = new LNode(5);
        l4 = new LNode(2);
        l5 = new LNode(20);
        l6 = new LNode(11);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = null;
        return l1;
    }

    public LNode initSecond() {
        l7 = new LNode(21);
        l7.next = l4;
        return l7;
    }

    /**
     * date:2017/9/22
     * description: 打印
     */
    public void printLink(LNode head) {
        while (head != null) {
            System.out.print(head.val + "\t");
            head = head.next;
        }
        System.out.println();
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
     * date:2017/9/25
     * description:因为无法方便地访问下标
     */
    public LNode qiefen(LNode begin, LNode end) {
        if (begin == null || begin == end) {
            return begin;
        }
        //拿最前面这个值
        int standard = begin.val;
        LNode middle = begin;
        LNode cur = begin.next;
        //cur走一遍
        while (cur != end) {
            //后面的数，比standard小，要交换
            //大的不用换，小的需要调到前面来，遇到一个小的，middle先往后移一位
            // 3 5 6 1 2
            // 3 1 2 5 6
            // 2 1 3 5 6
            if (cur.val < standard) {
                middle = middle.next;

                int tmp = cur.val;
                cur.val = middle.val;
                middle.val = tmp;
            }
            cur = cur.next;
        }
        //将middle的值设置成为standard
        begin.val = middle.val;
        middle.val = standard;

        return middle;
    }


    public LNode findMiddleNode(LNode begin, LNode end) {
        if (begin == null || begin == end) {
            return begin;
        }
        LNode middle = begin;
        LNode cur = begin.next;
        int standard = begin.val;
        while (cur != end) {
            //如果cur向前的过程中，小于standard
            //standard代表的那个值是begin
            //前面的node，值都比value大，就把这个值往前放，与middle的值交换
            //这样，小于standard的值都跑到前面了,middle以及middle之前，都是小的
            if (cur.val < standard) {
                middle = middle.next;
                int temp = cur.val;
                cur.val = middle.val;
                middle.val = temp;
            }
            cur = cur.next;
        }
        begin.val = middle.val;
        middle.val = standard;
        return middle;
    }

    /**
     * date:2017/9/22
     * description:sort
     */
    public void quickSort(LNode begin, LNode end) {
        if (begin == end || begin == null) {
            return;
        }
        LNode middleNode = findMiddleNode(begin, end);
        quickSort(begin, middleNode);
        quickSort(middleNode.next, end);
    }

    /**
     * date:2017/9/25
     * description:找到最后一个节点
     */
    public LNode getEndNode(LNode head) {
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }


    /**
     * date:2017/9/25
     * description:找到倒数N个节点
     */
    public LNode getEndNumNode(LNode head, int num) {
        LNode temp = head;
        for (int i = 0; i < num; i++) {
            temp = temp.next;
        }
        while (temp != null) {
            temp = temp.next;
            head = head.next;
        }
        return head;
    }


    /**
     * date:2017/9/25
     * description:删除倒数N个节点
     * 注意，删除节点，就是n.next = n.next.next
     */
    public void delEndNumNode(LNode head, int num) {
        LNode temp = head;
        LNode delNode = head;
        for (int i = 0; i < num; i++) {
            temp = temp.next;
        }
        while (temp.next != null) {
            temp = temp.next;
            delNode = delNode.next;
        }
        delNode.next = delNode.next.next;
    }


    /**
     * date:2017/9/25
     * description:判断两个链表是否有交点
     */
    public boolean isMerge(LNode l1, LNode l2) {
        int step1 = 0;
        int step2 = 0;
        LNode temp1 = l1;
        LNode temp2 = l2;
        while (temp1 != null) {
            temp1 = temp1.next;
            step1 = step1 + 1;
        }
        while (temp2 != null) {
            temp2 = temp2.next;
            step2 = step2 + 1;
        }
        //分别走step1和step2，就走到end
        //那么如果有交点，一定是共有的某一个节点，到了end
        int diff = step1 - step2;
        for (int i = 0; i < diff; i++) {
            l1 = l1.next;
        }
        while (l1 != null) {
            if (l1 == l2) {
                return true;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return false;
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

//        LNode tail = list.getEndNode(head);
//        list.quickSort(head, tail);
//        list.printLink(head);

//        System.out.println(list.getEndNumNode(head, 1).val);

//        list.delEndNumNode(head, 1);
//        list.printLink(head);


        LNode secondHead = list.initSecond();
        System.out.println(list.isMerge(head, secondHead));
    }
}
