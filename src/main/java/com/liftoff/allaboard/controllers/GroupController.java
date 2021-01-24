package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.data.GroupRepository;
import com.liftoff.allaboard.data.UserRepository;
import com.liftoff.allaboard.models.GameGroup;
import com.liftoff.allaboard.models.User;
import com.liftoff.allaboard.models.dto.UserGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("group")
@Controller
public class GroupController {


    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("") //lives at group
    public String displayAllGroups(Model model) {
        if (groupRepository == null) {
            model.addAttribute("title", "No Game Groups Available ");
        } else {
                model.addAttribute("title", "All Groups");
                model.addAttribute("groups", groupRepository.findAll());
        }
        return "group/group";
    }

    @GetMapping("create")
    public String createGroupForm(Model model){
        model.addAttribute("title", "Create Group");
        model.addAttribute("gameGroup", new GameGroup());
        return "group/createGroup";
    }

    @PostMapping("create")
    public String processCreateGroupForm(@ModelAttribute @Valid GameGroup newGameGroup,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Group");
            model.addAttribute("gameGroup", new GameGroup());
            return "group/createGroup";
        }
        groupRepository.save(newGameGroup);
        return "redirect:/group";
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
                model.addAttribute("title", "Edit Group " + gameGroup.get().getGameGroupName() + " (ID=" + gameGroup.get().getId() + ")");
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
            gameGroup.get().setGameGroupName(groupName);
            gameGroup.get().setGameGroupDescription(groupDescription);
            }
        return "redirect:/";
    }
//    @GetMapping("add-group")
//    public String displayGameGroupToUserForm(@RequestParam Integer eventId, Model model) {
//        Optional<Event> result = eventRepository.findById(eventId);
//        Event event = result.get();
//        model.addAttribute("title", "Add Tag to: " + event.getName());
//        model.addAttribute("tags", tagRepository.findAll());
//        EventTagDTO eventTag = new EventTagDTO();
//        eventTag.setEvent(event);
//        model.addAttribute("eventTag", eventTag);
//        return "events/add-tag.html";
//    }

    @GetMapping("add-group")
    public String displayGameGroupToUserForm(@RequestParam Integer userId, Model model) {
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        model.addAttribute("title", "Add Group to: " + user.getUsername());
        model.addAttribute("gameGroups", groupRepository.findAll());
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setUser(user);
        model.addAttribute("userGroup", userGroupDTO);
        return "user/add-group.html";
    }

    @PostMapping("add-group")
    public String processGameGroupToUserForm(@ModelAttribute @Valid UserGroupDTO userGroup,
                                             Errors errors, Model model) {

        if (!errors.hasErrors()) {
            User user = userGroup.getUser();
            GameGroup gameGroup = userGroup.getGameGroup();
            if (!user.getGameGroup().contains(userGroup)){
                user.addGameGroup(gameGroup);
                userRepository.save(user);
            }
            return "redirect:group?userId=" + user.getId();
        }

        return "redirect:add-group";
    }

}
