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
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().get().getSumm());
        return new ModelAndView("paid", model);
    }

    @PostMapping(path = "/fixing")
    public ModelAndView fixingOfPaid(@RequestParam(name = "name") String name, @RequestParam(name = "method") String method, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        Payment payment = new Payment();
        payment.setMethod(method);
        payment.setPayer(name);
        payment.setOrder_id(levyRepository.findFirstByOrderByIdDesc().get().getId());
        payment.setAmount(levyRepository.findFirstByOrderByIdDesc().get().getSumm());

        long d = System.currentTimeMillis();
        Date date = new Date(d);
        payment.setDate(date);
        paymentRepository.save(payment);

        model.addAttribute("prop", levyRepository.findFirstByOrderByIdDesc().getProp());
        model.addAttribute("collected", paymentRepository.amountSum(levyRepository.findFirstByOrderByIdDesc().getId()));
        model.addAttribute("name", name);
        model.addAttribute("payments", paymentRepository.findPaymentsByOrdera_id(levyRepository.findFirstByOrderByIdDesc().getId()));
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().getSumm());
        return new ModelAndView("fastlevy", model);
    }

}