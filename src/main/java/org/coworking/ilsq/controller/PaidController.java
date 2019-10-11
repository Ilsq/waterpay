package org.coworking.ilsq.controller;

import org.coworking.ilsq.entity.Payment;
import org.coworking.ilsq.repository.LevyRepository;
import org.coworking.ilsq.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

import static org.coworking.ilsq.controller.AccountController.getModelAndView;

@Controller
@RequestMapping(path = "/paid")
public class PaidController {


    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LevyRepository levyRepository;

    @PostMapping
    public ModelAndView goToPaid(@RequestParam(name = "name") String name, ModelMap model) {
        model.addAttribute("name", name);
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().getSumm());
        return new ModelAndView("paid", model);
    }

    @PostMapping(path = "/fixing")
    public ModelAndView fixingOfPaid(@RequestParam(name = "name") String name, @RequestParam(name = "method") String method, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        Payment payment = new Payment();
        payment.setMethod(method);
        payment.setPayer(name);
        payment.setOrder_id(levyRepository.findFirstByOrderByIdDesc().getId());
        payment.setAmount(levyRepository.findFirstByOrderByIdDesc().getSumm());

        long d = System.currentTimeMillis();
        Date date = new Date(d);
        payment.setDate(date);
        paymentRepository.save(payment);
        return getModelAndView(name, model, paymentRepository, levyRepository);
    }



}