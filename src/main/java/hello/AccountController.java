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
        if (accountRepository.findByLogin(login) != null) {
            return "User with this name is registered";
        }
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        accountRepository.save(account);
        return "saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}