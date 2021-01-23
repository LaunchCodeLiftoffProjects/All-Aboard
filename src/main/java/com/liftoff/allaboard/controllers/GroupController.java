package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.data.GroupRepository;
import com.liftoff.allaboard.models.Group;
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

    @GetMapping("delete")
    public String displayDeleteGroupForm(Model model) {
        model.addAttribute("title", "Delete Group");
        model.addAttribute("groups", groupRepository.findAll());
        return "group/delete";
    }// lives at /events/delete

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
    public String displayEditForm(Model model, @PathVariable int groupId) {
        Group group = groupRepository.findByGroupId(groupId);
        model.addAttribute("group", group);
        if (group != null) {
            model.addAttribute("title", "Edit Group " + group.getGroupName() + " (ID=" + group.getGroupId() + ")");
        }
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int groupId, String name, String description) {
        Group group = groupRepository.findByGroupId(groupId);
        group.setGroupName(name);
        group.setGroupDescription(description);
        return "redirect:";

    }

}
