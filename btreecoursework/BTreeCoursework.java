/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btreecoursework;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 14054494
 */
public class BTreeCoursework {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        BTree newBTree = new BTree(2);
        newBTree.insert(10);

        newBTree.insert(10);
        newBTree.insert(12);
        newBTree.insert(5);
        newBTree.insert(9);
        newBTree.insert(25);
        newBTree.checkRoot();
        newBTree.print();
         */
        Scanner sc = new Scanner(System.in);

        ArrayList<Integer> numList = new ArrayList<>();

        String fileName = fileChoice(sc);
        numList = readFile(fileName);
        
        int order = orderChoice(sc);
        
        BTree newBTree = createBTree(numList, order);
        menu(sc, newBTree);
    }

    public static String fileChoice(Scanner sc) {
        System.out.println("1)50 \n2)100 \n3)200 \n4)300");
        System.out.println("Enter data size");
        int dataSize = sc.nextInt();
        boolean valid = false;
        String fileName = "";
        while (!valid) {
            switch (dataSize) {
                case 1:
                    fileName = "50nums.txt";
                    valid = true;
                    break;
                case 2:
                    fileName = "100nums.txt";
                    valid = true;
                    break;
                case 3:
                    fileName = "200nums.txt";
                    valid = true;
                    break;
                case 4:
                    fileName = "300nums.txt";
                    valid = true;
                    break;
                default:
                    System.out.println("pick 1, 2, 3 or 4");
                    valid = false;
            }
        }
        return fileName;
    }

    public static ArrayList<Integer> readFile(String fileName) {
        ArrayList<Integer> numList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int data;
            while ((line = br.readLine()) != null) {
                data = Integer.parseInt(line);
                numList.add(data);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return numList;
    }

    public static int orderChoice(Scanner sc) {
        System.out.println("enter order of the tree");
        int order = sc.nextInt();
        return order;
    }

    public static BTree createBTree(ArrayList<Integer> numList, int order) {
        BTree newBTree = new BTree(order);
        numList.forEach((i) -> {
            newBTree.insert(i);
        });
        return newBTree;
    }

    private static void menu(Scanner sc, BTree BTree) {
        boolean valid = false;

        System.out.println("0)quit \n1)print \n2)search");

        while (!valid) {
            System.out.println("enter menu choice");
            int menuChoice = sc.nextInt();

            switch (menuChoice) {
                case 0:
                    valid = true;
                    System.exit(0);
                    break;
                case 1:
                    valid = true;
                    BTree.print();
                    menu(sc, BTree);
                    break;
                case 2:
                    valid = true;
                    search(sc, BTree);
                    menu(sc, BTree);
                    break;
                default:
                    System.out.println("pick 0, 1, or 2");
                    valid = false;
                    break;
            }
        }
    }

    private static void search(Scanner sc, BTree BTree) {
        System.out.println("Enter value to search for");
        int val = sc.nextInt();

        boolean found = BTree.search(val);
        if (found) {
            System.out.println(val + " is stored in this BTree");
        } else {
            System.out.println(val + " is not currently stored");
        }

    }

}
