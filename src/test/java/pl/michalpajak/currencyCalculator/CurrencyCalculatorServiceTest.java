package pl.michalpajak.currencyCalculator;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.michalpajak.currencyCalculator.services.CurrencyCalculatorService;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void shouldCalculateAverage() {
        List<Double> buyRateList = Arrays.asList(4.1301, 4.1621, 4.1530, 4.1569);
        double expectedResult = 4.1505;

        Assertions.assertEquals(expectedResult, currencyCalculatorService.average(buyRateList));
    }

    @Test
    public void shouldCalculateStandardDeviation() {
        List<Double> salleRateList = Arrays.asList(4.2135, 4.2461, 4.2370, 4.2409);
        double expectedResult = 0.0125;

        Assertions.assertEquals(expectedResult, currencyCalculatorService.standardDeviation(salleRateList));
    }
}
