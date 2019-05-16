package pl.michalpajak.currencyCalculator.models.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michalpajak.currencyCalculator.models.CurrencyDto;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class CurrencyCalculatorService {

    public CurrencyDto.Rate getRateForCurrency(String currency){
        return Objects.requireNonNull(getRestTemplate().getForObject("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "?format=json", CurrencyDto.class))
                .getCurrencyDataList()
                .get(0);
    }


    public double exchangePLNToCurrency(double cash, String currency){
        return cash / getRateForCurrency(currency).getMid() ;
    }

    public double averageCurrencyRate(String currencyCode, LocalDate beginDateInterval, LocalDate endDateInterval) {
        double result = 0.00;

        return result;
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
