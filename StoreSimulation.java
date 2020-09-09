/**
 * Store Simulation Project
 * This file controls the flow of the store simulation.
 * Used: https://www.youtube.com/watch?v=5gzayWys06o,
 */
package storesimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author/edited by Annette Peterman, Mariah Edwards, Jaclyn Kordus CS 180 - 02
 * Prof & Author: Dr. Timmerman Assignment: Store Simulation
 */
public class StoreSimulation {

    private static final int NUMBER_STANDARD_CHECKOUT = 4; // number of cashier registers
    private static final int NUMBER_SELF_CHECKOUTS = 8; // number of self-scan registers
    private static double simClock = 0; // elapsed time (minutes)
    private static MyHeap events = new MyHeap(); // events that occur in the store
    private static ArrayList<Register> registers = new ArrayList(); // registers used in the store
    public static int numCustomers = 0;
    public static int waitMoreThan2 = 0;
    public static int waitMoreThan3 = 0;
    public static int waitMoreThan5 = 0;
    public static int waitMoreThan10 = 0;
    public static double totalWaitTime = 0;
    public static int registersLength = 0;

    public static void main(String[] args) {
        loadRegisters();
        loadCustomerData();

        // Events are stored in a priority queue, so they will always be returned in order.
        while (events.getSize() > 0) {
            Event e = events.remove();
            simClock = e.getEventTime(); // Always set the clock to the time of the new event.
            if (e.getEventType() == EventType.ARRIVAL) {
                handleArrival(e);
            } else if (e.getEventType() == EventType.END_SHOPPING) {
                handleEndShopping(e);
            } else {
                handleEndCheckout(e);
            }
        } // end while
        printCollectedStatistics();
    } //end main

    //loads standard and self checkouts
    private static void loadRegisters() {
        for (int i = 0; i < NUMBER_STANDARD_CHECKOUT; i++) {
            Register r = new Register(0.01, 1.5);
            registers.add(r);
            registersLength++;
        }
        for (int i = 0; i < NUMBER_SELF_CHECKOUTS; i++) {
            Register r = new Register(0.04, 3.0);
            registers.add(r);
            registersLength++;
        }
    }

    //loads customer data from arrival file
    private static void loadCustomerData() {
        double arriveTime, avgSelectionTime;
        int items;

        try {
            File myFile = new File("arrival.txt");
            Scanner inputFile = new Scanner(myFile);
            while (inputFile.hasNext()) {
                numCustomers++;
                arriveTime = inputFile.nextDouble();
                items = inputFile.nextInt();
                avgSelectionTime = inputFile.nextDouble();
                Customer customer = new Customer(arriveTime, items, avgSelectionTime);
                Event event = new Event(customer, arriveTime, EventType.ARRIVAL);
                events.insert(event);
            }//end while
            inputFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("File was not found");
            System.exit(0);
        }
    }//end loadCustomerData

    private static void handleArrival(Event e) {
        Customer c = e.getCustomer();
        double endShoppingTime = c.getArriveTime() + c.getNumItems() * c.getAvgSelectionTime();
        Event endShopping = new Event(c, endShoppingTime, EventType.END_SHOPPING);
        events.insert(endShopping);
    }// end handleArrival

    //adds customer to shortest register line
    private static void handleEndShopping(Event e) {
        int shortest = getShortestLine();
        Customer customer = e.getCustomer();
        customer.setCheckoutLine(shortest); // Customer will always enter shortest checkout line.
        registers.get(shortest).enqueue(customer); // Even if line is empty, customer must be enqueued and dequeued so that the customer gets included in the stats for the register
        if (registers.get(shortest).getLineLength() == 1) { // If new customer is the only one in line, begin checkout.
            startCheckout(customer);
        }
    }//end handleEndShopping

    //removes customer from register
    private static void handleEndCheckout(Event e) {
        int line = e.getCustomer().getCheckoutLine();
        Customer c = registers.get(line).dequeue();
        if (registers.get(line).isEmpty()) {
            return;
        } else {
            Customer customer = registers.get(line).peek();
            startCheckout(customer);
        }
    }//end handleEndCheckout

    private static void startCheckout(Customer customer) {
        int line = customer.getCheckoutLine();
        double checkoutLength = customer.getNumItems() * registers.get(line).getScanTime() + registers.get(line).getPayTime();
        Event endCheckout = new Event(customer, checkoutLength + simClock, EventType.END_CHECKOUT);
        events.insert(endCheckout);
        double endShoppingTime = customer.getArriveTime() + (customer.getNumItems() * customer.getAvgSelectionTime());
        double endCheckoutTime = endCheckout.getEventTime();
        double startCheckout = endCheckoutTime - checkoutLength;
        double waitTime = startCheckout - endShoppingTime;
        if (waitTime >= 120) {
            waitMoreThan2++;
            if (waitTime >= 180) {
                waitMoreThan3++;
                if (waitTime >= 300) {
                    waitMoreThan5++;
                    if (waitTime >= 600) {
                        waitMoreThan10++;
                    }
                }

            }
        }
        totalWaitTime += waitTime;
    }//end startCheckout

    //prints store statistics 
    private static void printCollectedStatistics() {
        //Max Line Length for each register
        int maxLengthRegister1 = registers.get(0).getMaxLineLength();
        int maxLengthRegister2 = registers.get(1).getMaxLineLength();
        int maxLengthRegister3 = registers.get(2).getMaxLineLength();
        int maxLengthRegister4 = registers.get(3).getMaxLineLength();
        int maxLengthRegister5 = registers.get(4).getMaxLineLength();
        int maxLengthRegister6 = registers.get(5).getMaxLineLength();
        int maxLengthRegister7 = registers.get(6).getMaxLineLength();
        int maxLengthRegister8 = registers.get(7).getMaxLineLength();
        System.out.println("Max Line Length Register 1: " + maxLengthRegister1 + "\n"
                + "Max Line Length Register 2: " + maxLengthRegister2 + "\n"
                + "Max Line Length Register 3: " + maxLengthRegister3 + "\n"
                + "Max Line Length Register 4: " + maxLengthRegister4 + "\n"
                + "Max Line Length Register 5: " + maxLengthRegister5 + "\n"
                + "Max Line Length Register 6: " + maxLengthRegister6 + "\n"
                + "Max Line Length Register 7: " + maxLengthRegister7 + "\n"
                + "Max Line Length Register 8: " + maxLengthRegister8);
        if (registersLength > 8) {
            int maxLengthRegister9 = registers.get(8).getMaxLineLength();
            int maxLengthRegister10 = registers.get(9).getMaxLineLength();
            System.out.println("Max Line Length Register 9: " + maxLengthRegister9 + "\n"
                    + "Max Line Length Register 10: " + maxLengthRegister10);
            if (registersLength > 10) {
                int maxLengthRegister11 = registers.get(10).getMaxLineLength();
                int maxLengthRegister12 = registers.get(11).getMaxLineLength();
                System.out.println("Max Line Length Register 11: " + maxLengthRegister11 + "\n"
                        + "Max Line Length Register 12: " + maxLengthRegister12 +  "\n");
            }

        }

        //Total customers at each register
        int totalCusRegister1 = registers.get(0).getTotalNumCustomersForRegister(registers.get(0));
        int totalCusRegister2 = registers.get(1).getTotalNumCustomersForRegister(registers.get(1));
        int totalCusRegister3 = registers.get(2).getTotalNumCustomersForRegister(registers.get(2));
        int totalCusRegister4 = registers.get(3).getTotalNumCustomersForRegister(registers.get(3));
        int totalCusRegister5 = registers.get(4).getTotalNumCustomersForRegister(registers.get(4));
        int totalCusRegister6 = registers.get(5).getTotalNumCustomersForRegister(registers.get(5));
        int totalCusRegister7 = registers.get(6).getTotalNumCustomersForRegister(registers.get(6));
        int totalCusRegister8 = registers.get(7).getTotalNumCustomersForRegister(registers.get(7));
        System.out.println("Total Customers Register 1: " + totalCusRegister1 + "\n"
                + "Total Customers Register 2: " + totalCusRegister2 + "\n"
                + "Total Customers Register 3: " + totalCusRegister3 + "\n"
                + "Total Customers Register 4: " + totalCusRegister4 + "\n"
                + "Total Customers Register 5: " + totalCusRegister5 + "\n"
                + "Total Customers Register 6: " + totalCusRegister6 + "\n"
                + "Total Customers Register 7: " + totalCusRegister7 + "\n"
                + "Total Customers Register 8: " + totalCusRegister8);
        if (registersLength > 8) {
            int totalCusRegister9 = registers.get(8).getTotalNumCustomersForRegister(registers.get(8));
            int totalCusRegister10 = registers.get(9).getTotalNumCustomersForRegister(registers.get(9));
            System.out.println("Total Customers Register 9: " + totalCusRegister9
                    + "\n" + "Total Customers Register 10: " + totalCusRegister10);
            if (registersLength > 10) {
                int totalCusRegister11 = registers.get(10).getTotalNumCustomersForRegister(registers.get(10));
                int totalCusRegister12 = registers.get(11).getTotalNumCustomersForRegister(registers.get(11));
                System.out.println("Total Customers Register 11: " + totalCusRegister11
                        + "\n" + "Total Customers Register 12: " + totalCusRegister12 + "\n");
            }
        }

        //Average wait time for each customer
        double averageWaitTime = totalWaitTime / numCustomers;
        System.out.println("Average wait time per customer: " + averageWaitTime + " seconds");

        //Customer wait time percentages
        int percentMoreThan2 = (int) (((float) waitMoreThan2 / numCustomers) * 100);
        System.out.println("Percent of customers who waited more than 2 minutes = " + (percentMoreThan2) + "%");
        int percentMoreThan3 = (int) (((float) waitMoreThan3 / numCustomers) * 100);
        System.out.println("Percent of customers who waited more than 3 minutes = " + percentMoreThan3 + "%");
        int percentMoreThan5 = (int) (((float) waitMoreThan5 / numCustomers) * 100);
        System.out.println("Percent of customers who waited more than 5 minutes = " + percentMoreThan5 + "%");
        int percentMoreThan10 = (int) (((float) waitMoreThan10 / numCustomers) * 100);
        System.out.println("Percent of customers who waited more than 10 minutes = " + percentMoreThan10 + "%");
    }//end printCollectedStatistics

    //finds the register with the shortest line
    private static int getShortestLine() {
        int shortestLine = registers.get(0).getLineLength();
        int shortestlineIndex = 0;
        for (int i = 1; i < registers.size(); i++) {
            if (registers.get(i).getLineLength() < shortestLine) {
                shortestLine = registers.get(i).getLineLength();
                shortestlineIndex = i;
            }
        }
        return shortestlineIndex;
    }//end getShortestLine

} //end class store simulation
