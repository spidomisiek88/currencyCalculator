package pl.michalpajak.currencyCalculator.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michalpajak.currencyCalculator.models.CurrencyDto;

import java.util.List;
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

    public double average(List<Double> rateList) {
        double result = 0.00;

        for (double buyRate : rateList) {
            result += buyRate;
        }

        return result / rateList.size();
    }

    public double standardDeviation(List<Double> rateList) {
        return Math.sqrt(average(rateList));
    }


    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
