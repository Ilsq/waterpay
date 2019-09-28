package hello;


import entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/login")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewAccount(@RequestParam(name = "name") String login, @RequestParam(name = "pass") String password) {
        Account n = new Account();
        n.setLogin(login);
        n.setPassword(password);
        accountRepository.save(n);
        return "saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
