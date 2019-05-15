package pl.michalpajak.currencyCalculator.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExchangeRate {

    private LocalDate effectiveDate;
    private double bid;
    private double ask;
}
