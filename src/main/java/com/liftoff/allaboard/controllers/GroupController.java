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
    public String displayDeleteGameGroupForm(Model model) {
        model.addAttribute("title", "Delete Group");
        model.addAttribute("groups", groupRepository.findAll());
        return "group/deleteGroup";
    }

    @PostMapping("delete")
    public String processDeleteGameGroupForm(@RequestParam(required = false) int[] gameGroupIds) {
        if (gameGroupIds != null) {
            for (int id : gameGroupIds) {
                groupRepository.deleteById(id);
            }
        }
        return "redirect:/group";

    }

    @GetMapping("edit/{gameGroupId}")
    public String displayGameGroupEditForm(Model model, @PathVariable int gameGroupId) {
              Optional<GameGroup> gameGroup = groupRepository.findById(gameGroupId);
        if (gameGroup.isEmpty()) {
            model.addAttribute("title", "Invalid Game ID: " + gameGroupId);
        } else {
            model.addAttribute("gameGroup", gameGroup.get());
            if (gameGroup != null) {
                model.addAttribute("title", "Edit Group " + gameGroup.get().getGameGroupName() + " (ID=" + gameGroup.get().getId() + ")");
            }
        }
        return "group/editGroup";
    }

    @PostMapping("edit")
    public String processGameGroupEditForm(Model model, int gameGroupId, String gameGroupName, String gameGroupDescription, String gameGroupRules,
                                           String gameGroupAddress, int gameGroupZipCode) {
        Optional<GameGroup> result = groupRepository.findById(gameGroupId);

        if (result.isEmpty()) {
            model.addAttribute("title","Invalid Game ID: " + gameGroupId);
        }   else {
            GameGroup gameGroup= result.get();
            gameGroup.setGameGroupName(gameGroupName);
            gameGroup.setGameGroupDescription(gameGroupDescription);
            gameGroup.setGameGroupRules(gameGroupRules);
            gameGroup.setGameGroupAddress(gameGroupAddress);
            gameGroup.setGameGroupZipCode(gameGroupZipCode);
            groupRepository.save(gameGroup);
        }
        return "redirect:";
    }

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
