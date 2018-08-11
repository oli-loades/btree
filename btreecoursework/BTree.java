/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btreecoursework;

/**
 *
 * @author 14054494
 */
public class BTree {

    private Node root;
    private final int order;
    private int totalSize;

    public BTree(int val) {
        order = val;
        root = new Node(null, order);
        totalSize = 0;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getOrder() {
        return order;
    }

    public Node getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return totalSize == 0;
    }

    public boolean search(int key) {
        Node current = root;//set to start
        int index = 0;
        boolean found = false;

        do {

            while (!found && index <= current.getNumKeys() - 1) {//loop while its not found or at the end of the current node

                if (key == current.getKey(index)) {
                    found = true;
                } else if (key < current.getKey(index) && !current.isLeaf()) {//if key is less that what is currently in the node
                    current = current.getChild(index);
                    index = 0;
                } else if (index == current.getNumKeys() - 1 && !current.isLeaf()) {//set to latgest child
                    current = current.getChild(current.getNumChildren() - 1);
                    index = 0;
                } else {
                    index++;
                }

            }

        } while (!current.isLeaf() && !found);

        return found;
    }

    public void insert(int key) {
        Node current = root;
        int pos;
        boolean contains = search(key);

        if (!contains) {//stops duplicates

            while (!current.isLeaf()) {
                pos = current.findChildPos(key);//which child to look at next
                current = current.getChild(pos);
            }

            current.addKey(key);
            totalSize++;

        } else {
            System.out.println(key + " is already exists");
        }

        checkRoot();
    }

    public void print() {
        printTree(root, 0);
    }

    private void printTree(Node n, int level) {
        if (!isEmpty()) {

            System.out.println("Level: " + level);//loop through node
            for (int i = 0; i <= n.getNumKeys() - 1; i++) {
                System.out.print(n.getKey(i) + " ");
            }

            System.out.println("");
            level++;

            if (!n.isLeaf()) {//loop through children
                for (int j = 0; j <= n.getNumChildren() - 1; j++) {
                    printTree(n.getChild(j), level);
                }
            }

        } else {
            System.out.println("B Tree empty");
        }
    }

    private void checkRoot() {//changes root if root has been split
        if (root.getParent() != null) {
            root = root.getParent();
        }
    }
}
