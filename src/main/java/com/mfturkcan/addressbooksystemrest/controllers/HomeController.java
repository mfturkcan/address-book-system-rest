package com.mfturkcan.addressbooksystemrest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalTime;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "index";
    }
}