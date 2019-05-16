package pl.michalpajak.currencyCalculator.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CurrencyRateTable {

    private String tableNumber;
    private LocalDate notationDate;
    private LocalDate publicationDate;
    private List<CurrencyRate> currencyRateList;
}
