package org.coworking.ilsq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/paid")
public class PaidController {

    @PostMapping
    public ModelAndView goToLogin(@RequestParam(name = "name") String name, ModelMap model) {
        model.addAttribute("name", name);
        return new ModelAndView("paid", model);
    }
}