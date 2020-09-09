/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesimulation;

/**
 *
 * @author Annette Peterman, Mariah Edwards, Jaclyn Kordus
 */
class Register {

    private class Node {

        public Node next;
        public Customer customer;

        public Node(Customer customer, Node next) {
            this.customer = customer;
            this.next = next;
        }
    }// ends class Node

    //instance variables
    private double scanTime;
    private double payTime;
    private int lineLength = 0;
    private Node first;
    private int maxLineLength = 0;
    private int totalNumCustomersForRegister = 0;

    //constructor Register
    Register(double d, double d0) {
        scanTime = d;
        payTime = d0;
    }//ends Register

    //gets and returns line length
    int getLineLength() {
        return lineLength;
    }//ends getLineLength

    // adds a customer
    void enqueue(Customer customer) {
        if (lineLength == 0) {
            first = new Node(customer, null);
            first.next = first;
            lineLength++;
            totalNumCustomersForRegister++;
        } else {
            Node nn = new Node(customer, null);
            Node lastNode = first.next;
            nn.next = lastNode;
            first.next = nn;
            lineLength++;
            totalNumCustomersForRegister++;
            if (lineLength > maxLineLength) {
                maxLineLength = lineLength;
            }
        }
    }//ends enqueue

    //removes a customer
    Customer dequeue() {
        Node currentNode = first;
        for (int i = 0; i < lineLength; i++) {
            if (currentNode.next == first) {
                Customer x = first.customer;
                currentNode.next = first.next;
                first = currentNode;
                lineLength--;
                return x;
            } else {
                currentNode = currentNode.next;
            }
        }
        return null;
    }//ends dequeue

    //checks if the line is empty
    boolean isEmpty() {
        if (lineLength == 0) {
            return true;
        } else {
            return false;
        }
    }//ends isEmpty

    //returns the first customer if there is one
    Customer peek() {
        if (lineLength == 0) {
            System.out.println("No Line");
            System.exit(0);
        }
        return first.customer;
    }//ends peek

    //gets and returns the time to scan per item
    double getScanTime() {
        return scanTime;
    }//ends getScanTime

    //gets and returns the time it takes to pay
    double getPayTime() {
        return payTime;
    }//ends getPayTime

    //gets and returns the number of customers at the register
    int getTotalNumCustomersForRegister(Register r) {
        return totalNumCustomersForRegister;
    }//ends getTotalNumCustomersForRegister

    //gets and returns the maximum length of the line
    int getMaxLineLength() {
        return maxLineLength;
    }//ends getMaxLineLength

}//ends class register
