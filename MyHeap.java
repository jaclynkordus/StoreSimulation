/*
 * Organizes events so we know which one happens next
 */
package storesimulation;

import java.util.Arrays;

/**
 *
 * @author Annette Peterman, Mariah Edwards, Jaclyn Kordus
 */
class MyHeap<T> {

    //instance variables
    private Event[] myArrayOfEvents = new Event[50000];
    private int numItems = 0;
    private int currentIndex = 0;
    private Event parent;
    private Event root;
    private Event child;

    int getSize() {
        return numItems;
    }

    //removes event from heap
    Event remove() {
        root = myArrayOfEvents[0];
        myArrayOfEvents[0] = myArrayOfEvents[numItems - 1];
        numItems--;
        currentIndex = 0;
        while (currentIndex < numItems) {
            //loop that checks if it has two children    
            if (myArrayOfEvents[(2 * currentIndex) + 2] != null) {
                if (myArrayOfEvents[currentIndex].compareTo(myArrayOfEvents[(2 * currentIndex) + 1]) > 0
                        || (myArrayOfEvents[currentIndex].compareTo(myArrayOfEvents[(2 * currentIndex) + 2])) > 0) {
                    if (myArrayOfEvents[(2 * currentIndex) + 1].compareTo(myArrayOfEvents[(2 * currentIndex) + 2]) < 0) {
                        child = myArrayOfEvents[(2 * currentIndex) + 1];
                        myArrayOfEvents[(2 * currentIndex) + 1] = myArrayOfEvents[currentIndex];
                        myArrayOfEvents[currentIndex] = child;
                        currentIndex = (2 * currentIndex) + 1;
                    } else {
                        child = myArrayOfEvents[(2 * currentIndex) + 2];
                        myArrayOfEvents[(2 * currentIndex) + 2] = myArrayOfEvents[currentIndex];
                        myArrayOfEvents[currentIndex] = child;
                        currentIndex = (2 * currentIndex) + 2;
                    }
                } else {
                    return root;
                }
                //seperate code for 1 kid
            } else if (myArrayOfEvents[(2 * currentIndex) + 2] == null && myArrayOfEvents[(2 * currentIndex) + 1] != null) {
                if (myArrayOfEvents[currentIndex].compareTo(myArrayOfEvents[(2 * currentIndex) + 1]) > 0) {
                    child = myArrayOfEvents[(2 * currentIndex) + 1];
                    myArrayOfEvents[(2 * currentIndex) + 1] = myArrayOfEvents[currentIndex];
                    myArrayOfEvents[currentIndex] = child;
                    currentIndex = (2 * currentIndex) + 1;
                } else {
                    return root;
                }
            } else {
                return root;
            }
        }// end while
        return root;
    }

    //adds event to heap
    void insert(Event event) {
        myArrayOfEvents[numItems] = event;
        currentIndex = numItems;
        while (currentIndex > 0) {
            if (myArrayOfEvents[currentIndex].compareTo(myArrayOfEvents[(int) Math.floor((currentIndex - 1) / 2)]) < 0) {
                parent = myArrayOfEvents[(int) Math.floor((currentIndex - 1) / 2)];
                myArrayOfEvents[(int) Math.floor((currentIndex - 1) / 2)] = myArrayOfEvents[currentIndex];
                myArrayOfEvents[currentIndex] = parent;
                currentIndex = (int) Math.floor((currentIndex - 1) / 2);
            } else {
                break;
            }
        }// end while
        numItems++;
    }//ends insert

}//ends class myHeap
