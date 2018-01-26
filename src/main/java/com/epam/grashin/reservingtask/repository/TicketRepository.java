package com.epam.grashin.reservingtask.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class TicketRepository {

    private static final String INSERT_TICKET_SQL = "INSERT INTO TICKETS(USER_ID, TRAIN_ID) VALUES (?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(long userId, long trainId) {
        jdbcTemplate.update(INSERT_TICKET_SQL, trainId, userId);
    }
}
