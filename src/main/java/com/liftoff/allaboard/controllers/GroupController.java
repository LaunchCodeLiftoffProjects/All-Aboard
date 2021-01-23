package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.data.GroupRepository;
import com.liftoff.allaboard.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class GroupController {


    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("group") //lives at group
    public String displayAllGroups(Model model) {
            model.addAttribute("title", "All Groups");
            model.addAttribute("groups", groupRepository.findAll());
        return "group";
    }

    @GetMapping("create")
    public String createGroupForm(Model model){
        model.addAttribute("title", "Create Group");
        model.addAttribute("group", new Group());
        return "group/createGroup";
    }

    @PostMapping("group")
    public String processGroupForm(@ModelAttribute @Valid Group newGroup,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Group");
            model.addAttribute("group", new Group());
            return "group/createGroup";
        }
        groupRepository.save(newGroup);
        return "redirect:";
    }


}
