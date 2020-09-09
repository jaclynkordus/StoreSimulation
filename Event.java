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
enum EventType {
    ARRIVAL, END_SHOPPING, END_CHECKOUT
}

class Event implements Comparable<Event> {

    //instance variables
    private Customer customer;
    private double eventTime;
    private EventType eventType;

    //constructor Event
    Event(Customer customer, double eventTime, EventType eventType) {
        setCustomer(customer);
        setEventTime(eventTime);
        setEventType(eventType);
    }//ends Event

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }// ends getCustomer

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }// ends setCustomer

    /**
     * @return the eventTime
     */
    public double getEventTime() {
        return eventTime;
    }// ends getEventTime

    /**
     * @param eventTime the eventTime to set
     */
    public void setEventTime(double eventTime) {
        this.eventTime = eventTime;
    }//ends setEventTime

    /**
     * @return the eventType
     */
    public EventType getEventType() {
        return eventType;
    }//ends getEventType

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }//ends setEventType

    @Override
    public int compareTo(Event o) {
        if (this.eventTime < o.eventTime) {
            return -1;
        } else if (this.eventTime == o.eventTime) {
            return 0;
        } else {
            return 1;
        }
    }//ends compareTo
}//ends class Event
