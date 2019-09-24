package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @PostMapping("/greeting")
    public String greetingPost(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model, @RequestParam(name="pass", required=false, defaultValue="default") String pass) {
        model.addAttribute("name", name);
        model.addAttribute("pass", pass);
        return "greeting";
    }
}
