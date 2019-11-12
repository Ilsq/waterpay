package org.coworking.ilsq.controller;

import org.coworking.ilsq.entity.Account;
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
        Account account = accountRepository.findByLogin(login);
        if (account == null) {
            return new ModelAndView("login", model);
        }
        if (!account.getPassword().equals(password)) {
            return new ModelAndView("login", model);
        }

        model.addAttribute("prop", levyRepository.findFirstByOrderByIdDesc().getProp());
        model.addAttribute("collected", paymentRepository.amountSum(levyRepository.findFirstByOrderByIdDesc().getId()));
        model.addAttribute("name", login);

        if (accountRepository.findByLogin(login).getRole().equals("admin")) {
            model.addAttribute("levies", levyRepository.findAll());
            return new ModelAndView("administrator", model);
        }
        model.addAttribute("payments", paymentRepository.findPaymentsByOrdera_id(levyRepository.findFirstByOrderByIdDesc().getId()));
        model.addAttribute("summ", levyRepository.findFirstByOrderByIdDesc().getSumm());

        return new ModelAndView("fastlevy", model);
    }
}