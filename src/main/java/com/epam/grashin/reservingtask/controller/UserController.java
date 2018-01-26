package com.epam.grashin.reservingtask.controller;

import com.epam.grashin.reservingtask.entity.User;
import com.epam.grashin.reservingtask.repository.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value="userInfo", description="User management")
public class UserController{

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUserById(@PathVariable long id) {
            return userRepository.findUserById(id);

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public long createUser(@RequestParam String name, @RequestParam String email) {
        return userRepository.insert(name, email);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        userRepository.update(user);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public long deleteUser(@PathVariable long id) {
        return userRepository.deleteById(id);
    }
}
