package org.coworking.ilsq.controller;

import org.coworking.ilsq.entity.Account;
import org.coworking.ilsq.entity.Levy;
import org.coworking.ilsq.repository.AccountRepository;
import org.coworking.ilsq.repository.LevyRepository;
import org.coworking.ilsq.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LevyRepository levyRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping(path = "/add")
    public ModelAndView addNewAccount(@RequestParam(name = "name") String login, @RequestParam(name = "pass") String password, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        if (accountRepository.findByLogin(login) != null) {
            return new ModelAndView("register", model);
        }
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setRole("user");
        accountRepository.save(account);
        return new ModelAndView("login", model);
    }

    @PostMapping(path = "/enter")
    public ModelAndView enterAccount(@RequestParam(name = "name") String login, @RequestParam(name = "pass") String password, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        model.addAttribute("payments", Collections.EMPTY_LIST);
        model.addAttribute("levies", Collections.EMPTY_LIST);
        model.addAttribute("collected", 0);
        model.addAttribute("prop", 0);

        Account account = accountRepository.findByLogin(login);
        if (account == null) {
            return new ModelAndView("login", model);
        }
        if (!account.getPassword().equals(password)) {
            return new ModelAndView("login", model);
        }

        Optional<Levy> last = levyRepository.findFirstByOrderByIdDesc();
        if (last.isPresent()) {
            model.addAttribute("prop", last.get().getProp());
            model.addAttribute("collected", paymentRepository.amountSum(last.get().getId()));
        } else {

        }

        model.addAttribute("name", login);
        last.ifPresent(levy -> model.addAttribute("payments", paymentRepository.findPaymentsByOrderaId(levy.getId())));
//        if (!model.containsAttribute("payments")) {
//            model.addAttribute("payments", null);
//        }
        levyRepository.findFirstByOrderByIdDesc().ifPresent(levy -> model.addAttribute("summ", levy.getSumm()));

        if (accountRepository.findByLogin(login).getRole().equals("admin")) {
            model.addAttribute("levies", levyRepository.findAll());
            return new ModelAndView("administrator", model);
        }

        return new ModelAndView("fastlevy", model);
    }
}