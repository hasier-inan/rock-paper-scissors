package com.ciklum.lottoland.rockpaperscissors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main controller retrieve the unique single page application view
 */
@Controller
public class ApplicationController {

    @GetMapping(value = {"/*"})
    public String mainApp() {
        return "index";
    }
}
