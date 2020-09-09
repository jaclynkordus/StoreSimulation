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
class Customer {

    //in arrival file
    private double arriveTime;
    private int numItems;
    private double avgSelectionTime;
    private int registerIndex;
    //add more varables to collect stats about the customer

    //constructor Customer
    Customer(double arriveTime, int items, double avgSelectionTime) {
        setArriveTime(arriveTime);
        setNumItems(items);
        setAvgSelectionTime(avgSelectionTime);
    }//ends customer

    /**
     * @return the arriveTime
     */
    public double getArriveTime() {
        return arriveTime;
    }//ends getArriveTime

    /**
     * @param arriveTime the arriveTime to set
     */
    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }//ends setArriveTime

    /**
     * @return the numItems
     */
    public int getNumItems() {
        return numItems;
    }//ends getNumItems

    /**
     * @param numItems the numItems to set
     */
    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }//ends setNumItems

    /**
     * @return the avgSelectionTime
     */
    public double getAvgSelectionTime() {
        return avgSelectionTime;
    }//ends getAvgSelectionTime

    /**
     * @param avgSelectionTime the avgSelectionTime to set
     */
    public void setAvgSelectionTime(double avgSelectionTime) {
        this.avgSelectionTime = avgSelectionTime;
    }//ends setAvgSelectionTime

    void setCheckoutLine(int registerIndex) {
        this.registerIndex = registerIndex;
    }//ends setCheckoutLine

    int getCheckoutLine() {
        return this.registerIndex;
    }//ends getCheckoutLine
}//ends class Customer
