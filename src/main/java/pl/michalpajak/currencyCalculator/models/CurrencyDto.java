package pl.michalpajak.currencyCalculator.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDto {

    @JsonProperty("rates")
    private List<Rate> currencyDataList;

    @Data
    public static class Rate {
        private double mid;
    }
}
