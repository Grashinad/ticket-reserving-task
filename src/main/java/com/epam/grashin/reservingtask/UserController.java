package com.epam.grashin.reservingtask;

import com.epam.grashin.reservingtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    UserRepository userRepository;
}
