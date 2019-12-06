package org.coworking.ilsq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/register")
public class RegisterController {

    @GetMapping
    public ModelAndView goToRegister(ModelMap model) {
        return new ModelAndView("register", model);
    }
}