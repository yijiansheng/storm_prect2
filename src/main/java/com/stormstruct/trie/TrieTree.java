package com.stormstruct.trie;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Author: changdalin
 * Date: 2018/2/26
 * Description:
 **/

class Node {
    //当前节点的char
    char ch;
    //记录后续节点
    TreeMap<Character, Node> nodeMap;
    //途径这个节点的次数
    int count;

    public Node(char ch) {
        this.ch = ch;
        nodeMap = new TreeMap();
        count = 0;
    }

    public void print() {
        System.out.print(ch);
    }
}

public class TrieTree {

    private Node root;

    /**
     * date:2018/2/26
     * description:
     * 注意，插入的都是str，每插入一个str，最后落到的那个node里面，count不能为0
     */
    public int insert(String str) {
        int res = 0;
        Node temp = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            temp = insert(ch, temp);
        }
        //temp就是最后一个map里面的插入(或者更新的node)
        temp.count++;
        //所以只要node里面的count 不为0，就是一个叶节点
        return res;
    }


    /**
     * date:2018/2/26
     * description:将ch 插入到 temp的map里面
     */
    public Node insert(char ch, Node temp) {
        if (temp.nodeMap.get(ch) == null) {
            temp.nodeMap.put(ch, new Node(ch));
        }
        temp = temp.nodeMap.get(ch);
        return temp;
    }


    private void show(Node foo, String str) {
        if (foo.count != 0) {
            System.out.println(str + " : " + foo.count);
        }
        for (Character ch : foo.nodeMap.keySet()) {
            show(foo.nodeMap.get(ch), str + ch);
        }
    }

    public void showALL() {
        show(root, "");
    }


    public void search(String str, Node temp) {
        Set<String> set = new HashSet<String>();
        Map<Character, Node> tempMap = temp.nodeMap;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (tempMap.get(ch) == null) {
                System.out.println("未匹配");
                return;
            }
            tempMap = tempMap.get(ch).nodeMap;
        }
        //过了for循环，说明存在
        //而且只能在这个路径里面
        for (Map.Entry<Character, Node> entry : tempMap.entrySet()) {
            show(entry.getValue(), str + entry.getKey());
        }
    }

    public static void main(String[] args) {
        Node root = new Node(' ');
        TrieTree tree = new TrieTree();
        tree.root = root;
        tree.insert("ser");
        tree.insert("serious");
        tree.insert("seriously");
        tree.insert("seriously");
        tree.showALL();
        tree.search("se", root);
    }
}
