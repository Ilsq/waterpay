package org.coworking.ilsq.controller;

import org.coworking.ilsq.entity.Levy;
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
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path = "/enternewlevy")
public class NewlevyController {

    @Autowired
    private LevyRepository levyRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public ModelAndView goToNewlevy(ModelMap model) {
        return new ModelAndView("newlevy", model);
    }

    @PostMapping(path = "/create")
    public ModelAndView createNewLevy(@RequestParam(name = "date") String dateInSpring, @RequestParam(name = "prop") String propInSpring, @RequestParam(name = "pay") String summInString, @RequestParam(name = "requisites") String methods, ModelMap model) {
        if (dateInSpring == "" || propInSpring == "" || summInString == "") {
            model.addAttribute("newlevyError", "Ошибка вызванная указанием неверных/неполных данных.");
            return new ModelAndView("newlevy", model);
        }

        Date date = Date.valueOf(dateInSpring);
        Integer prop = Integer.valueOf(propInSpring);
        Integer summ = Integer.valueOf(summInString);

        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        model.addAttribute("payments", Collections.EMPTY_LIST);

        if (prop <= 0) {
            model.addAttribute("newlevyError", "Ошибка вызванная указанием неверной суммы сбора.");
            return new ModelAndView("newlevy", model);
        }

        if (summ <= 0 || summ > prop) {
            model.addAttribute("newlevyError", "Ошибка вызванная указанием неверной суммы оплаты.");
            return new ModelAndView("newlevy", model);
        }

        if (methods == "") {
            model.addAttribute("newlevyError", "Не указаны реквизиты для оплаты.");
            return new ModelAndView("newlevy", model);
        }

        Levy levy = new Levy();
        levy.setDate(date);
        levy.setProp(prop);
        levy.setSumm(summ);
        levy.setMethods(methods);
        levyRepository.save(levy);

        Optional<Levy> last = levyRepository.findFirstByOrderByIdDesc();
        if (last.isPresent()) {
            model.addAttribute("prop", last.get().getProp());
            model.addAttribute("collected", paymentRepository.amountSum(last.get().getId()).orElse(0));
        }

        model.addAttribute("levies", levyRepository.findAll());
        return new ModelAndView("administrator", model);
    }
}