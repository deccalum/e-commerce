package se.lexicon.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple web controller that redirects root requests to the H2 console.
 */
@Controller
public class HomeController {

    /**
     * Redirects the root route.
     *
     * @return redirect target for browser navigation
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/h2-console";
    }
}
