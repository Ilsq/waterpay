package hello;

        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @PostMapping("/login")
    public String loginPost(@RequestParam(name="name", required=false, defaultValue="null") String name, Model model, @RequestParam(name="pass", required=false, defaultValue="null") String pass) {
        model.addAttribute("name", name);
        model.addAttribute("pass", pass);
        return "login";
    }


}