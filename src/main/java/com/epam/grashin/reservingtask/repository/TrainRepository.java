package com.epam.grashin.reservingtask.repository;

import com.epam.grashin.reservingtask.entity.Train;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
@Slf4j
public class TrainRepository {
    private static final String INSERT_TRAIN_SQL = "INSERT INTO TRAINS(DESTINATION, DATE, CAPACITY, TICKETS) VALUES (?,?,?,?)";
    private static final String UPDATE_TICKETS_SQL = "UPDATE TRAINS SET TICKETS=? WHERE ID=?";
    private static final String GET_TRAIN_BY_ID_SQL = "SELECT * FROM TRAINS WHERE ID=?";
    private static final String GET_TRAIN_BY_DESTINATION_SQL = "SELECT * FROM TRAINS WHERE DESTINATION=?";
    private static final String GET_TRAIN_BY_DATE_SQL = "SELECT * FROM TRAINS WHERE DATE=?";
    private static final String GET_ALL_TRAINS_SQL = "SELECT * FROM TRAINS";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long insert(String destination, LocalDate date, Integer capacity) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        synchronized (this) {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(INSERT_TRAIN_SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, destination);
                statement.setDate(2, Date.valueOf(date));
                statement.setInt(3, capacity);
                statement.setInt(4, capacity);
                return statement;
            }, keyHolder);
        }

        return keyHolder.getKey().longValue();
    }

    public void updateTicketsAmountForTrain(long ticketsLeft, long trainId) {
        jdbcTemplate.update(UPDATE_TICKETS_SQL,
                ticketsLeft, trainId);
    }

    public List<Train> findTrainByDestination(String destination) {
        return jdbcTemplate.query(GET_TRAIN_BY_DESTINATION_SQL, new Object[]{destination}, new BeanPropertyRowMapper<>(Train.class));
    }

    public List<Train> findTrainByDate(LocalDate date) {
        return jdbcTemplate.query(GET_TRAIN_BY_DATE_SQL, new Object[]{Date.valueOf(date)}, new BeanPropertyRowMapper<>(Train.class));
    }

    public Train findTrainById(Long id) {
        try{
            return jdbcTemplate.queryForObject(GET_TRAIN_BY_ID_SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Train.class));
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public List<Train> findAll() {
        log.info("Lookup for all Trains");
        return jdbcTemplate.query(GET_ALL_TRAINS_SQL, new BeanPropertyRowMapper<>(Train.class));
    }
}
