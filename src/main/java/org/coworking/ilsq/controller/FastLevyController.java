package org.coworking.ilsq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/fastlevy")
public class FastLevyController {

    @Autowired
    private FastLevyController fastLevyController;
}
