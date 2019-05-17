package pl.michalpajak.currencyCalculator.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michalpajak.currencyCalculator.models.CurrencyDto;

import java.math.BigDecimal;
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

        for (double rate : rateList) {
            result += rate;
        }

        result = result / rateList.size();

        return new BigDecimal(result).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double standardDeviation(List<Double> rateList) {
        double result = 0.00;
        double average = average(rateList);

        for (Double rate : rateList) {
            result += Math.pow((rate - average), 2);
        }

        result = Math.sqrt(result / rateList.size());

        return new BigDecimal(result).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
