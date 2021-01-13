package com.liftoff.allaboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping
    @ResponseBody
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(value = "map")
    public String map() {
        return "map";
    }

}
