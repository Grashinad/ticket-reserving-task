package com.epam.grashin.reservingtask.entity;

import java.time.LocalDate;

public class Train {

    private Long id;
    private String destination;
    private LocalDate date;
    private Integer capacity;
    private Integer ticketsLeft;

    public Train(Long id, String destination, LocalDate date, Integer capacity) {
        this.id = id;
        this.destination = destination;
        this.date = date;
        this.capacity = capacity;
        this.ticketsLeft = capacity;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getTicketsLeft() {
        return ticketsLeft;
    }

    public void setTicketsLeft(Integer ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }
}
