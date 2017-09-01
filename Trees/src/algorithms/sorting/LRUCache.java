package algorithms.sorting;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    int capacity, count;
    NodeList nodeList;
    Map<Integer, Node> map = new HashMap<>();
    public LRUCache(int capacity) {
        this.capacity = capacity;
        count = 0;
        nodeList = new NodeList();
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node cur = map.get(key);
        nodeList.update(cur);
        nodeList.print("after get "+ key+":");
        return cur.val;
    }
    
    public void put(int key, int value) {

        //if it's already in, do update
        if (map.containsKey(key)) {
            Node cur = map.get(key);
            cur.val = value;
            nodeList.update(cur);
            
        } else {
            if (count==capacity) {
                Node cur = nodeList.removeLRU();
                map.remove(cur.key);
                count--;
            }
            Node cur = new Node(key, value);
            map.put(key, cur);
            nodeList.add(cur);
            count++;
        }
        nodeList.print("after put ("+key+", "+value+"):");
        System.out.println(map.keySet());
    }
    
    private class Node {
        int key, val;
        Node pre,next;
        Node() {this(0,0);}
        Node(int k, int v) {key = k; val = v;}
       
    }
    private class NodeList {
        Node head, tail;
        NodeList(){head = new Node(); tail= new Node(); head.next = tail; tail.pre = head; }
        public Node removeLRU() {
        	Node d = tail.pre;
            remove(d);
            return d;
        }
        public void remove(Node n) {
            Node p1 = n.pre;
            Node p2 = n.next;
            p1.next = p2;
            p2.pre = p1;
        }
        //add to front always
        public void add(Node n) {
            Node tmp = head.next;
            head.next = n; n.pre = head;
            n.next = tmp; tmp.pre = n;
        }
        public void update(Node n) {
            remove(n);
            add(n);
        }
        public void print(String msg) {
            System.out.print(msg);
            Node cur = head;
            while (cur != null) {
                System.out.print("("+cur.key+", "+cur.val+")-->");
                cur = cur.next;
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
    	LRUCache cache = new LRUCache(2);
    	cache.put(1, 1);
    	cache.put(2, 2);
    	cache.get(1);
    	cache.put(3, 3);
    	cache.get(2);
    }

}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */