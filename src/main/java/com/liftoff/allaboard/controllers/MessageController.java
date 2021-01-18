package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.models.Message;
import com.liftoff.allaboard.models.data.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("messaging")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("add")
    public String addMessage(Model model) {
        model.addAttribute(new Message());
        return "messages/add";
    }

    @PostMapping("add")
    public String processAddMessageForm(@ModelAttribute @Valid Message message,
                                        Errors errors, HttpServletRequest request, Model model) {

//        if (errors.hasErrors()) {
//            return "employers/add";
//        }

        Integer userId = (Integer) request.getSession()
                .getAttribute("user");
        if(userId == null){
            userId = 1;
        }
        message.setFromUserId(userId);
        message.setToGameGroupId(2);

        this.messageRepository.save(message);

        return "redirect:/add";

    }




    @GetMapping("wtf")
    @ResponseBody
    public String messageWtf() {
        Message m = this.messageRepository.save(new Message("abc", 1, 2));

        int id = m.getId();

        return m.getTimeSent() + Integer.toString(id);
    }


}
