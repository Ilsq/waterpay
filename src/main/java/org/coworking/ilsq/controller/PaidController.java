package org.coworking.ilsq.controller;

import org.coworking.ilsq.entity.Levy;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.StringTokenizer;

@Controller
@RequestMapping(path = "/paid")
public class PaidController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LevyRepository levyRepository;

    @PostMapping
    public ModelAndView goToPaid(@RequestParam(name = "name") String name, ModelMap model) {

        Optional<Levy> last = levyRepository.findFirstByOrderByIdDesc();

        model.addAttribute("name", name);
        model.addAttribute("paidError", "");
//        model.addAttribute("methods", last.get().getMethods());


        if (!last.isPresent()) {
            model.addAttribute("prop", last.get().getProp());
            model.addAttribute("collected", paymentRepository.amountSum(last.get().getId()).orElse(0));
            model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().get().getSumm());
            model.addAttribute("payments", Collections.EMPTY_LIST);
            model.addAttribute("paidError", "Сборов нет");
            model.addAttribute("methods", "определенных споров нет");
            return new ModelAndView("fastlevy", model);
        }

        ArrayList<String> requisites = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(last.get().getMethods(), "\n");
        while (st.hasMoreTokens()) {
            String req = "";
            req = st.nextToken();
            requisites.add(req);
        }

//        ArrayList<String> req = new ArrayList<>();
//        req.add("one");
//        req.add("two");
//        req.add("three");
//        model.addAttribute("req", req);

        model.addAttribute("requisites", requisites);

        last.ifPresent(levy -> model.addAttribute("payments", paymentRepository.findPaymentsByOrderaId(levy.getId())));
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().get().getSumm());
        model.addAttribute("prop", last.get().getProp());
        model.addAttribute("collected", paymentRepository.amountSum(last.get().getId()).orElse(0));
        return new ModelAndView("paid", model);
    }

    @PostMapping(path = "/fixing")
    public ModelAndView fixingOfPaid(@RequestParam(name = "name") String name, @RequestParam(name = "method", required = false) String method, ModelMap model) {

        Optional<Levy> last = levyRepository.findFirstByOrderByIdDesc();
        if (last.isPresent()) {
            model.addAttribute("prop", last.get().getProp());
            model.addAttribute("collected", paymentRepository.amountSum(last.get().getId()).orElse(0) + levyRepository.findFirstByOrderByIdDesc().get().getSumm());
        }

        model.addAttribute("name", name);
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().get().getSumm());
        model.addAttribute("methods", last.get().getMethods());

        if (paymentRepository.countPaymentsByPayerAndOrderaId(name, levyRepository.findFirstByOrderByIdDesc().get().getId()) != 0) {
            String repeatError = "Оплата за текущий сбор зафиксирован раннее";
            model.addAttribute("error", repeatError);
            return new ModelAndView("paid", model);
        }

        if (method == null) {
            String error = "Не введен метод оплаты";
            model.addAttribute("error", error);
            return new ModelAndView("paid", model);
        }

        model.addAttribute("payments", Collections.EMPTY_LIST);

        Payment payment = new Payment();
        payment.setMethod(method);
        payment.setPayer(name);
        payment.setOrderaId(levyRepository.findFirstByOrderByIdDesc().get().getId());
        payment.setAmount(levyRepository.findFirstByOrderByIdDesc().get().getSumm());

        long d = System.currentTimeMillis();
        Date date = new Date(d);
        payment.setDate(date);
        paymentRepository.save(payment);

        model.addAttribute("name", name);
        model.addAttribute("payments", paymentRepository.findPaymentsByOrderaId(levyRepository.findFirstByOrderByIdDesc().get().getId()));
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().get().getSumm());
        return new ModelAndView("fastlevy", model);
    }
}