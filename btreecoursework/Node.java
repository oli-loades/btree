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
public class Node {

    private Node[] children;
    private int numChildren;

    private Node parent;

    private int[] keys;
    private int numKeys;

    private final int MAX_NUM_KEYS;
    private final int MAX_NUM_CHILDREN;

    private final int order;

    public Node(Node parent, int order) {
        this.order = order;
        this.parent = parent;
        MAX_NUM_KEYS = order * 2 + 1;//add 1 to allow for value to be pushed upto parent
        MAX_NUM_CHILDREN = MAX_NUM_KEYS + 1;
        children = new Node[MAX_NUM_CHILDREN];
        keys = new int[MAX_NUM_KEYS];//add 1 to insert before split
        numChildren = 0;
        numKeys = 0;
    }

    public Node getParent() {
        return parent;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public Node getChild(int index) {
        return children[index];
    }

    public void setParent(Node newParent) {
        parent = newParent;
    }

    public int getKey(int index) {
        return keys[index];
    }

    public boolean isFull() {
        return numKeys == MAX_NUM_KEYS;
    }

    public boolean isLeaf() {
        return numChildren == 0;
    }

    public boolean isEmpty() {
        return numKeys == 0;
    }

    public void addChild(Node child) {
        child.setParent(this);
        insertChild(child);//find correct position    
        numChildren++;
    }

    public void addKey(int val) {
        int pos;
        if (isEmpty()) {//new node
            pos = 0;
            keys[pos] = val;
            numKeys++;
        } else {
            pos = getPos(val);
            if (pos != -1) {//if it has been found
                shiftRight(pos);
                numKeys++;
                keys[pos] = val;
            }
            if (isFull()) {//reactive
                split();
            }
        }
    }

    private void shiftRight(int pos) {//creates space at pos in key array
        for (int i = numKeys - 1; i >= pos; i--) {
            keys[i + 1] = keys[i];
        }
    }

    private int getPos(int val) {//used t ofind teh position in teh node that the new key should be stored
        int pos = -1;
        int i = 0;
        boolean found = false;
        while (i <= numKeys - 1 && !found) {
            if (val < keys[i]) {
                pos = i;
                found = true;
            }
            i++;
        }
        if (!found) {
            pos = i;//set to last position i nte harray
        }
        return pos;
    }

    private void split() {
        int mid = numKeys / 2;
        int tempMid = keys[mid];//holds the key being pushed up
        int i = numKeys - 1;
        Node newChild = new Node(null, order);

        while (i > mid) {//start at end of array and go back into middle key
            newChild.addKey(keys[i]);//add largest keys to new node
            numKeys--;//removes access to last elem
            i--;
        }

        if (!isLeaf()) {//has children
            newChild = moveChilden(newChild);
        }

        if (parent == null) {//if root is beign split
            Node newRoot = new Node(null, order);
            setParent(newRoot);
            parent.addChild(this);
        }

        newChild.setParent(parent);
        parent.addChild(newChild);//add child to parent
        parent.addKey(tempMid);
        numKeys--;//removes key that has been pushed up

    }

    private Node moveChilden(Node n) {//used when node splitting has children
        int mid = numChildren / 2;//middle child position

        int i = numChildren - 1;//last child
        while (i >= mid) {
            n.addChild(children[i]);//add child to node
            numChildren--;//reduce number of children
            i--;
        }
        return n;
    }

    public int findChildPos(int key) {//used to find position i nwhich to inser the child node within teh children array
        int index = 0;
        int pos = 0;
        boolean found = false;
        while (index <= numKeys - 1 && !found) {
            if (key < getKey(index)) {//get next elem in node
                if (index <= numChildren - 1) {//not at end of array
                    pos = index;
                    found = true;
                } else {
                    pos = numChildren - 1;
                    found = true;
                }
            } else {
                index++;
            }
        }
        if (!found) {
            pos = numChildren - 1;
        }
        return pos;
    }

    private void insertChild(Node n) {
        int i = 0;
        int key = n.getKey(0);
        boolean inserted = false;
        if (!isLeaf()) {
            while (!inserted && i <= numChildren - 1) {
                //comapre first elem of inserted child with current children last elem
                if (key > children[i].getKey(children[i].getNumKeys() - 1)) {//conpare with other nodes
                    i++;//next child
                } else {
                    //position found
                    inserted = true;
                    shiftChildrenRight(i);//makes space within childre narray
                    children[i] = n;

                }
            }
            if (!inserted) {
                children[i] = n;
            }
        } else {//has no otehr children
            children[0] = n;
        }
    }

    private void shiftChildrenRight(int pos) {//makes space in children array
        for (int i = numChildren - 1; i >= pos; i--) {
            children[i + 1] = children[i];
        }
    }

}
