package com.example.Nubida;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {
    @GetMapping("/")
    public String hello(){
        return "Main Controller";
    }

    @GetMapping("/test")
    public String test(){
        return "test!!!";
    }
}