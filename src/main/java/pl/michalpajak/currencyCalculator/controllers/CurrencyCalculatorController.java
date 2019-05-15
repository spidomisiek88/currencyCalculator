package pl.michalpajak.currencyCalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.michalpajak.currencyCalculator.models.services.CurrencyCalculatorService;

@Controller
public class CurrencyCalculatorController {

    private final CurrencyCalculatorService currencyCalculatorService;

    @Autowired
    public CurrencyCalculatorController(CurrencyCalculatorService currencyCalculatorService) {
        this.currencyCalculatorService = currencyCalculatorService;
    }


    @GetMapping("/")
    public String currency(){
        return "currency_calculator_view";
    }

    @PostMapping("/")
    public String currency(@RequestParam("currency") String currency,
                           @RequestParam("pln") double pln,
                           Model model){
        model.addAttribute("result", currencyCalculatorService.exchangePLNToCurrency(pln, currency));
        return "currency_calculator_view";
    }
}
