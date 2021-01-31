package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.data.GroupRepository;
import com.liftoff.allaboard.models.Game;
import com.liftoff.allaboard.models.GameGroup;
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

    @Autowired
    private GroupRepository groupRepository;



    @GetMapping("addFromGame")
    public String addFromGame(Model model, @RequestParam String gameId, @RequestParam String gameName) {
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
        model.addAttribute("gameName", gameName);

        return "messages/add";
    }


    @GetMapping("add")
    public String addMessage(Model model) {
        model.addAttribute(new Message());
        return "messages/add";
    }

    @PostMapping("add")
    public String processAddMessageForm(@ModelAttribute @Valid Message message,
                                        Errors errors, Authentication authentication, @RequestParam String gameName) {

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

        return "redirect:addFromGame?gameId=" + message.getToGameGroupId() + "&gameName=" + gameName;

    }


    @RequestMapping(value = "games")
    public String listGames(Model model) {

        List<Game> l = new ArrayList<Game>();


        Iterable<GameGroup> allGames = groupRepository.findAll();
        for (GameGroup g: allGames) {
            l.add(new Game(g.getId(), g.getGameGroupName()));
        }

        Iterable<Game> games = l;

        model.addAttribute("games", games);

        return "messages/list-games";
    }





}
