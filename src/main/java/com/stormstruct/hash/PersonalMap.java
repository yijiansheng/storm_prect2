package com.stormstruct.hash;

import java.util.Map;

/**
 * Author: changdalin
 * Date: 2017/9/26
 * Description:
 **/
class PersonalMap<K, V> {


    /**
     * date:2017/9/26
     * description:实现这个inter，主要是inter里面有几个方法
     */
    static class Node<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        //决定了在table上面的位置
        int hash;
        Node<K, V> next;

        //构造一个node，需要key value hash 和 next


        public Node(int hash, K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V val) {
            V oldValue = value;
            value = val;
            return oldValue;
        }
    }


    Node<K, V>[] table;
    int nodeSize = 10;
    float kuorongLv = 0.75f;
    int currentNodeNum = 0;

    public PersonalMap() {
        table = new Node[nodeSize];
    }


    public V get(K key) {
        if (key == null) {
            return getNullKey();
        }
        return getNode(key);
    }

    public V put(K key, V val) {
        if (key == null) {
            return putNullKey(val);
        }
        int columnIndex = indexFor(hash(key), nodeSize);
        //先找一遍
        for (Node<K, V> tmp = table[columnIndex]; tmp != null; tmp = tmp.next) {
            if (tmp.hash == hash(key) && tmp.key.equals(key)) {
                V oldValue = tmp.value;
                tmp.value = val;
                return oldValue;
            }
        }
        currentNodeNum++;
        //没有找到key相同，则添加
        addNode(hash(key), key, val, columnIndex);
        return null;
    }


    /**
     * date:2017/9/26
     * description:对key求hash
     */
    public final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * date:2017/9/26
     * description:根据hash值，得到一个index
     */
    public int indexFor(int hash, int length) {
        return hash % length;
    }


    /**
     * date:2017/9/26
     * description:如果是null的key，那么就会在table[0]上
     * 遍历table[0]上面的每一个node，如果有node里面的key是null的，就找出来
     */
    public V getNullKey() {
        for (Node<K, V> tmp = table[0]; tmp != null; tmp = tmp.next) {
            if (tmp.key == null) {
                return tmp.value;
            }
        }
        return null;
    }

    public V getNode(K k) {
        //从这条链上找
        int columnIndex = indexFor(hash(k), nodeSize);
        for (Node<K, V> tmp = table[columnIndex];
             tmp != null;
             tmp = tmp.next) {
            //注意条件
            if (tmp.hash == hash(k) && tmp.key.equals(k)) {
                return tmp.value;
            }
        }
        return null;
    }

    /**
     * date:2017/9/26
     * description:注意，return的这个是一个旧值
     */
    public V putNullKey(V val) {
        //首先检查table[0]上是否有null的
        for (Node<K, V> tmp = table[0]; tmp != null; tmp = tmp.next) {
            if (tmp.key == null) {
                V oldValue = tmp.value;
                tmp.value = val;
                return oldValue;
            }
        }
        currentNodeNum++;
        //新建一个Node，并且放入0列
        addNode(0, null, val, 0);
        return null;
    }


    /**
     * date:2017/9/26
     * description:注意这个地方,需要扩容
     * 而且扩容,会影响那个columnIndex，需要重新计算这个index
     */
    public void addNode(int hash, K key, V val, int columnIndex) {
        resize(table.length * 2);
        //链表拿出来
        Node<K, V> cList = table[columnIndex];
        if (cList == null) {
            cList = newNode(hash, key, val, null);
            table[columnIndex] = cList;
        } else {
            //尾上添一个node
            while (cList.next != null) {
                cList = cList.next;
            }
            cList.next = newNode(hash, key, val, null);
        }

    }

    /**
     * date:2017/9/26
     * description:这个方法容易出并发问题
     * 它的核心是:将node从原来的table里面的一个list上拿下来，重新计算新table的index
     * 将node的next指向整条index链表,然后将node设置成index链表
     * 两个线程调用，出现了环链表，get的时候，next的就出问题了
     */
    private void resize(int capacity) {
        if (currentNodeNum < (nodeSize * kuorongLv)) {
            return;
        }
        /**
         * date:2017/9/26
         * description:扩容，改变指针，
         */
        Node<K, V>[] newTable = new Node[capacity];
        //为了方便，选择倒序添加到newtable[i]，不然每次还得找newtable[i]的最后一个元素
        for (Node<K, V> oldHead : table) {
            Node<K, V> oldNode = oldHead;
            while (oldNode != null) {
                int newIndex = indexFor(oldNode.hash, capacity);
                Node<K, V> second = oldNode.next;
                //oldNode就是离散出来的node,有自己的一块内存
                //让next指向newtable[i]，再把自己给newtable[i]
                oldNode.next = newTable[newIndex];
                newTable[newIndex] = oldNode;
                oldNode = second;
            }
        }
        table = newTable;
        nodeSize = capacity;
    }

    public Node<K, V> newNode(int hash, K key, V val, Node<K, V> next) {
        return new Node<K, V>(hash, key, val, null);
    }


}


class MapTest {
    public static void main(String[] args) {
        PersonalMap<String, Integer> map = new PersonalMap<String, Integer>();
//        map.put(null, 1);
//        map.put(null, 10);
//        System.out.println(map.get(null));
//        map.put("111", 111);
//        map.put("111", 11);
//        System.out.println(map.get("111"));
//        System.out.println("num:" + map.currentNodeNum);
//        System.out.println(map.hash(null));
        for (int i = 0; i < 31; i++) {
            map.put("" + i, i);
        }
        System.out.println(map.currentNodeNum);
        System.out.println(map.nodeSize);
        System.out.println(map.get("5"));
    }
}