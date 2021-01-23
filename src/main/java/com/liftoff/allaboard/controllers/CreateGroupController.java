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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class CreateGroupController {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("createGroup") //lives at group/create
    public String displayCreateGroupForm(Model model) {
        model.addAttribute("title", "Create Group");
        //model.addAttribute(new Group());
        return "createGroup";
    }

    @PostMapping("createGroup")
    public String processCreateGroupForm(@ModelAttribute @Valid Group newGroup,
                                          Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Group");
            return "createGroup";
        }
        return "group";
    }

}
