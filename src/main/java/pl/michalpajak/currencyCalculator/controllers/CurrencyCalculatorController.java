package pl.michalpajak.currencyCalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.michalpajak.currencyCalculator.services.CurrencyCalculatorService;
import pl.michalpajak.currencyCalculator.services.ParserCurrencyRateTableService;
import pl.michalpajak.currencyCalculator.services.SearchCurrencyRateFileService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class CurrencyCalculatorController {

    private final CurrencyCalculatorService currencyCalculatorService;
    private ParserCurrencyRateTableService parserCurrencyRateTableService;
    private SearchCurrencyRateFileService searchCurrencyRateFileService;

    @Autowired
    public CurrencyCalculatorController(CurrencyCalculatorService currencyCalculatorService,
                                        ParserCurrencyRateTableService parserCurrencyRateTableService,
                                        SearchCurrencyRateFileService searchCurrencyRateFileService) {

        this.currencyCalculatorService = currencyCalculatorService;
        this.parserCurrencyRateTableService = parserCurrencyRateTableService;
        this.searchCurrencyRateFileService = searchCurrencyRateFileService;
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

//    @GetMapping("/{currencyCode}/{beginDate}/{endDate}")
//    public String averageAndStandardDeviationCalculation(){
//        return "currency_rate_calculation_view";
//    }

    @GetMapping("/{currencyCode}/{beginDate}/{endDate}")
    public String averageAndStandardDeviationCalculation(@PathVariable("currencyCode") String currencyCode,
                                                         @PathVariable("beginDate") String beginDate,
                                                         @PathVariable("endDate") String endDate,
                                                         Model model) {

        parserCurrencyRateTableService.createCurrencyRateTablesList(searchCurrencyRateFileService
                .getFileNamesFromDateRange(LocalDate.parse(beginDate, DateTimeFormatter.ISO_DATE),
                        LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE)));

        model.addAttribute("average", currencyCalculatorService.average(
                parserCurrencyRateTableService.getListBuyRare(currencyCode)));
        model.addAttribute("standardDeviation", currencyCalculatorService.standardDeviation(
                parserCurrencyRateTableService.getListSalleRare(currencyCode)));

        return "currency_rate_calculation_view";
    }
}
