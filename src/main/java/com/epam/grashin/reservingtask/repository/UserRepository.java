package com.epam.grashin.reservingtask.repository;

import com.epam.grashin.reservingtask.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private static final String insertUserSql = "";
    private static final String getUsersSql = "";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insert(User user) {
        return jdbcTemplate.update(insertUserSql);
    }

    public List<User> findAll() {
        return jdbcTemplate.query(getUsersSql, new BeanPropertyRowMapper(User.class));
    }
}
