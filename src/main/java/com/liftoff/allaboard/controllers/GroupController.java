package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.data.GroupRepository;
import com.liftoff.allaboard.models.GameGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        model.addAttribute("group", new GameGroup());
        return "group/createGroup";
    }

    @PostMapping("group")
    public String processGroupForm(@ModelAttribute @Valid GameGroup newGameGroup,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Group");
            model.addAttribute("group", new GameGroup());
            return "group/createGroup";
        }
        groupRepository.save(newGameGroup);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteGroupForm(Model model) {
        model.addAttribute("title", "Delete Group");
        model.addAttribute("groups", groupRepository.findAll());
        return "group/delete";
    }

    @PostMapping("delete")
    public String processDeleteGroupForm(@RequestParam(required = false) int[] groupIds) {
        if (groupIds != null) {
            for (int id : groupIds) {
                groupRepository.deleteById(id);
            }
        }
        return "redirect:";

    }

    @GetMapping("edit/{groupId}")
    public String displayGroupEditForm(Model model, @PathVariable int groupId) {
        Optional<GameGroup> gameGroup = groupRepository.findById(groupId);
        if (gameGroup.isEmpty()) {
            model.addAttribute("title", "Invalid Game ID: " + groupId);
        } else {
            model.addAttribute("group", gameGroup.get());
            if (gameGroup != null) {
                model.addAttribute("title", "Edit Group " + gameGroup.get().getGroupName() + " (ID=" + gameGroup.get().getId() + ")");
            }
        }
        return "group/edit";
    }

    @PostMapping("edit")
    public String processGroupEditForm(Model model, int groupId, String groupName, String groupDescription) {
        Optional<GameGroup> gameGroup = groupRepository.findById(groupId);
        if (gameGroup.isEmpty()) {
            model.addAttribute("Invalid Game ID: " + groupId);
        }   else {
            model.addAttribute(gameGroup.get());
            gameGroup.get().setGroupName(groupName);
            gameGroup.get().setGroupDescription(groupDescription);
            }
        return "redirect:";
    }

    @GetMapping("createGroup") //lives at group/create
    public String displayCreateGroupForm(Model model) {
        model.addAttribute("title", "Create Group");
        //model.addAttribute(new Group());
        return "createGroup";
    }

    @PostMapping("createGroup")
    public String processCreateGroupForm(@ModelAttribute @Valid GameGroup newGameGroup,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Group");
            return "createGroup";
        }
        return "group";
    }
}
