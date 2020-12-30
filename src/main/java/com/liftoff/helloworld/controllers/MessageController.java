package com.liftoff.helloworld.controllers;

import com.liftoff.helloworld.models.Message;
import com.liftoff.helloworld.models.data.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("wtf")
    @ResponseBody
    public String messageWtf() {
        Message m = this.messageRepository.save(new Message("abc", 1, 2));

        int id = m.getId();

        return m.getTimeSent() + Integer.toString(id);
    }


}
