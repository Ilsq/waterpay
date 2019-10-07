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

    @PostMapping(path = "/")
    public ModelAndView goToLogin(ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        return new ModelAndView("redirect:/login", model);
    }


    @PostMapping(path = "/add")
    public ModelAndView addNewAccount(@RequestParam(name = "name") String login, @RequestParam(name = "pass") String password, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        if (accountRepository.findByLogin(login) != null) {
            return new ModelAndView("redirect:/register", model);
        }
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        accountRepository.save(account);
        return new ModelAndView("redirect:/login", model);
    }

    @PostMapping(path = "/enter")
    public ModelAndView enterAccount(@RequestParam(name = "name") String login, @RequestParam(name = "pass") String password, ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        if (accountRepository.findByLogin(login) == null) {
            return new ModelAndView("redirect:/login", model);
        }
        if (!accountRepository.findByLogin(login).getPassword().equals(password)) {
            return new ModelAndView("redirect:/login", model);
        }
        model.addAttribute("name", login);
        return new ModelAndView("redirect:/fastlevy", model);
    }
}