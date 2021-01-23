package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.models.Game;
import com.liftoff.allaboard.models.Message;
import com.liftoff.allaboard.models.User;
import com.liftoff.allaboard.models.data.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("messaging")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;


    @GetMapping("addFromGame")
    public String addFromGame(Model model, @RequestParam String gameId) {
        Integer theGameId = Integer.parseInt(gameId);
        Message m = new Message();
        m.setToGameGroupId(theGameId);
        model.addAttribute(m);


        Iterable<Message> messages = messageRepository.findAll();
        List<Message> lst = new ArrayList<Message>();
        for(Message m2: messages) {
            if(m2.getToGameGroupId() == theGameId)
            {
                lst.add(m2);
            }
        }


        model.addAttribute("messages", lst);

        return "messages/add";
    }


    @GetMapping("add")
    public String addMessage(Model model) {
        model.addAttribute(new Message());
        return "messages/add";
    }

    @PostMapping("add")
    public String processAddMessageForm(@ModelAttribute @Valid Message message,
                                        Errors errors, Authentication authentication, Model model) {

//        if (errors.hasErrors()) {
//            return "employers/add";
//        }

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        System.out.println(user);

//        if(userId == null){
//            userId = 1;
//        }
        message.setFromUserId(1);
        message.setTimeSent(LocalDateTime.now());

        this.messageRepository.save(message);

        return "redirect:addFromGame?gameId=" + message.getToGameGroupId();

    }


    @RequestMapping(value = "games")
    public String listGames(Model model) {

        List<Game> l = new ArrayList<Game>();
        l.add(new Game(1, "daves game"));
        l.add(new Game(2, "barry's game"));
        l.add(new Game(3, "minecraft"));

        Iterable<Game> games = l;

        model.addAttribute("games", games);

        return "messages/list-games";
    }





}
