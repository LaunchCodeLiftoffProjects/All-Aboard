package com.liftoff.allaboard.controllers;

import com.liftoff.allaboard.data.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("groupmembers")
public class GroupMemberController {
//    @Autowired
//    GroupMemberRepository groupMemberRepository;
//
//    @GetMapping
//    public String displayGroupMembers(Model model) {
//        model.addAttribute("title", "All Group Members");
//        model.addAttribute("groupmembers", groupMemberRepository.findAll());
//        return "groupmembers/index";
//    }
}
