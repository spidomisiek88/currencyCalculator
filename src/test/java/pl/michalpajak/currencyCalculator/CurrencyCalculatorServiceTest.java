package pl.michalpajak.currencyCalculator;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.michalpajak.currencyCalculator.services.CurrencyCalculatorService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CurrencyCalculatorServiceTest {

    @Autowired
    CurrencyCalculatorService currencyCalculatorService;

    @Test
    public void shouldGetRateForCurrency() {

        String currency = "EUR";
        double expectedRate = 4.3128;

        Assertions.assertEquals(expectedRate, currencyCalculatorService.getRateForCurrency(currency).getMid());
    }

    @Test
    public void shouldExchangePLNToCurrency() {

        String currency = "EUR";
        double cash = 1;
        double expectedResult = 0.23186792802819514;

        Assertions.assertEquals(expectedResult, currencyCalculatorService.exchangePLNToCurrency(cash,currency));
    }
}
