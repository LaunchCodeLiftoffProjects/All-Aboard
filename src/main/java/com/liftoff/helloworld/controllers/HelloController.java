package com.liftoff.helloworld.controllers;

import com.liftoff.helloworld.models.Message;
import com.liftoff.helloworld.models.data.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {


    @Autowired
    private MessageRepository messageRepository;



    @GetMapping
    @ResponseBody
    public String helloWorld() {
        Message m = this.messageRepository.save(new Message("abc", 1, 2));

        int id = m.getId();

        return Integer.toString(id);
    }

    @GetMapping(value = "map")
    public String map() {
        return "map";
    }

}
