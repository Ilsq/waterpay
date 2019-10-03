package hello;

import entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/login")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "/add")
    public ModelAndView addNewAccount(@RequestParam(name = "name") String login, @RequestParam(name = "pass") String password, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        if (accountRepository.findByLogin(login) != null) {
            return new ModelAndView("redirect:/register.html", model);
        }
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        accountRepository.save(account);
        return new ModelAndView("redirect:/login.html", model);
    }

//    @GetMapping(path = "/all")
//    public Iterable<Account> getAllAccounts() {
//        return accountRepository.findAll();
//    }

    @PostMapping(path = "/enter")
    public ModelAndView enterAccount(@RequestParam(name = "name") String login, @RequestParam(name = "pass") String password, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        if (accountRepository.findByLogin(login) == null) {
            return new ModelAndView("redirect:/login.html", model);
        }
        if (!accountRepository.findByLogin(login).getPassword().equals(password)) {
            return new ModelAndView("redirect:/login.html", model);
        }
        return new ModelAndView("redirect:/fastorder.html", model);
    }
}