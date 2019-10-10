package org.coworking.ilsq.controller;

import org.coworking.ilsq.entity.Levy;
import org.coworking.ilsq.repository.LevyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

@Controller
@RequestMapping(path = "/enternewlevy")
public class NewlevyController {

    @Autowired
    private LevyRepository levyRepository;

    @PostMapping
    public ModelAndView goToNewlevy(ModelMap model) {
        return new ModelAndView("newlevy", model);
    }

    @PostMapping(path = "/create")
    public ModelAndView createNewLevy(@RequestParam(name = "date") Date date, @RequestParam(name = "prop") int prop, @RequestParam(name = "pay") int summ, @RequestParam(name = "requisites") String methods, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        if (levyRepository.findByDate(date) != null) {
            return new ModelAndView("enternewlevy", model);
        }
        Levy levy = new Levy();
        levy.setDate(date);
        levy.setProp(prop);
        levy.setSumm(summ);
        levy.setMethods(methods);
        levyRepository.save(levy);
        model.addAttribute("levies", levyRepository.findAll());
        return new ModelAndView("administrator", model);
    }
}