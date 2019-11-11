package org.coworking.ilsq.controller;

import org.coworking.ilsq.repository.LevyRepository;
import org.coworking.ilsq.repository.PaymentRepository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface IController {

    default ModelAndView getModelAndView(@RequestParam(name = "name") String login, ModelMap model, PaymentRepository paymentRepository, LevyRepository levyRepository) {
        model.addAttribute("name", login);
        model.addAttribute("payments", paymentRepository.findAll());
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().get().getSumm());
        return new ModelAndView("fastlevy", model);
    }

}
