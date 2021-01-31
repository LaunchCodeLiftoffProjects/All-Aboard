package com.liftoff.allaboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping
    public String helloWorld() {
        return "index";
    }

    @GetMapping(value = "map")
    public String map() {
        return "map";
    }

}
