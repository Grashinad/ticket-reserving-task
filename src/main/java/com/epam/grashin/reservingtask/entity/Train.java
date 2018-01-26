package com.epam.grashin.reservingtask.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class Train {

    private Long id;
    private String destination;
    private Date date;
    private Integer capacity;
    private Integer tickets;

    public Train(Long id, String destination, Date date, Integer capacity) {
        this.id = id;
        this.destination = destination;
        this.date = date;
        this.capacity = capacity;
        this.tickets = capacity;
    }
}
