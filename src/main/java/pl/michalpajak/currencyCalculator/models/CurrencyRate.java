package pl.michalpajak.currencyCalculator.models;

import lombok.Data;

@Data
public class CurrencyRate {

    private String currencyName;
    private int converter;
    private String currencyISOCode;
    private double buyingRate;
    private double sellingRate;


}
