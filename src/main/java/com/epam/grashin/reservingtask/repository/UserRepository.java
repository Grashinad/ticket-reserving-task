package com.epam.grashin.reservingtask.repository;

import com.epam.grashin.reservingtask.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@Slf4j
public class UserRepository {

    private static final String INSERT_USER_SQL = "INSERT INTO USERS(NAME, EMAIL) VALUES (?,?)";
    private static final String UPDATE_USER_SQL = "UPDATE USERS SET NAME=?, EMAIL=? WHERE ID=?";
    private static final String DELETE_USER_SQL = "DELETE FROM USERS WHERE ID=?";
    private static final String GET_USER_BY_ID_SQL = "SELECT * FROM USERS WHERE ID=?";
    private static final String GET_ALL_USERS_SQL = "SELECT * FROM USERS";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long insert(String name, String email) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, email);
            return statement;
        },keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int update(User user) {
        return jdbcTemplate.update(UPDATE_USER_SQL,
                user.getName(), user.getEmail(), user.getId());
    }

    public int deleteById(long id) {
        return jdbcTemplate.update(DELETE_USER_SQL, id);
    }

    public User findUserById(long id) {
        try{
            return (User) jdbcTemplate.queryForObject(GET_USER_BY_ID_SQL, new Object[] { id }, new BeanPropertyRowMapper(User.class));
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public List<User> findAll() {
        log.info("Lookup for all users");
        return jdbcTemplate.query(GET_ALL_USERS_SQL, new BeanPropertyRowMapper(User.class));
    }

}
