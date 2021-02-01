package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.models.Message;
import com.liftoff.allaboard.models.data.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {


    @Autowired
    private MessageRepository messageRepository;



    @GetMapping
    public String helloWorld() {
        return "index";
    }

    @GetMapping(value = "map")
    public String map() {
        return "map";
    }

}
