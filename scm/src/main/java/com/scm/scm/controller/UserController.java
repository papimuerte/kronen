package com.scm.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class UserController {
    @RequestMapping(value = "scm", method=RequestMethod.GET)
    @ResponseBody
    public String getHelloWorld() 
    {
        return "<h1>Erste Einrichtung des Projektes</h1>";
    }
    
}