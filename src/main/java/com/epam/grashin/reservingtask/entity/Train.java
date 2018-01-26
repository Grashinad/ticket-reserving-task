package com.epam.grashin.reservingtask.entity;

import java.sql.Date;

public class Train {

    private Long id;
    private String destination;
    private Date date;
    private Integer capacity;
    private Integer tickets;

    public Train(){
    }

    public Train(Long id, String destination, Date date, Integer capacity) {
        this.id = id;
        this.destination = destination;
        this.date = date;
        this.capacity = capacity;
        this.tickets = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }
}
