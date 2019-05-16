package pl.michalpajak.currencyCalculator;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import pl.michalpajak.currencyCalculator.models.CurrencyRate;
import pl.michalpajak.currencyCalculator.models.CurrencyRateTable;
import pl.michalpajak.currencyCalculator.models.services.ParserCurrencyRateTableService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ParserCurrencyRateTableServiceTest {

    @Autowired
    ParserCurrencyRateTableService parserCurrencyRateTableService;

    @Test
    public void shouldCreateDocument() {

        Document expectedDocument = createExpectedDocument();
        String expectedTableNumber = expectedDocument.getElementsByTagName("numer_tabeli")
                .item(0).getTextContent();

        parserCurrencyRateTableService.createDocument("c073z070413");
        String actualTableNumber = parserCurrencyRateTableService.getDocument().getElementsByTagName("numer_tabeli")
                .item(0).getTextContent();

        Assertions.assertEquals(expectedTableNumber, actualTableNumber);
    }

    @Test
    public void shouldCreateCurrencyRateTable() {

        CurrencyRateTable expectedCurrencyRateTable = createExpectedCurrencyRateTable();

        parserCurrencyRateTableService.createDocument("c073z070413");
        CurrencyRateTable actualCurrencyRateTable = parserCurrencyRateTableService.createCurrencyRateTable();

        Assertions.assertEquals(expectedCurrencyRateTable, actualCurrencyRateTable);
    }

    private CurrencyRateTable createExpectedCurrencyRateTable() {

        CurrencyRateTable currencyRateTable = new CurrencyRateTable();
        currencyRateTable.setTableNumber("73/C/NBP/2007");
        currencyRateTable.setNotationDate(LocalDate.parse("2007-04-12", DateTimeFormatter.ISO_DATE));
        currencyRateTable.setPublicationDate(LocalDate.parse("2007-04-13", DateTimeFormatter.ISO_DATE));
        currencyRateTable.setCurrencyRateList(createExpectedCurrencyRateList());

        return currencyRateTable;
    }

    private List<CurrencyRate> createExpectedCurrencyRateList() {
        List<CurrencyRate> currencyRateList = new ArrayList<>();

        currencyRateList.add(createExpectedCurrencyRate("dolar amerykański", 1 , "USD", 2.8210 , 2.8780));
        currencyRateList.add(createExpectedCurrencyRate("dolar australijski", 1 , "AUD", 2.3292, 2.3762));
        currencyRateList.add(createExpectedCurrencyRate("dolar kanadyjski", 1 , "CAD", 2.4799, 2.5301));
        currencyRateList.add(createExpectedCurrencyRate("euro", 1 , "EUR", 3.7976, 3.8744));
        currencyRateList.add(createExpectedCurrencyRate("forint (Węgry)", 100 , "HUF", 1.5457, 1.5769));
        currencyRateList.add(createExpectedCurrencyRate("frank szwajcarski", 1 , "CHF", 2.3163, 2.3631));
        currencyRateList.add(createExpectedCurrencyRate("funt szterling", 1 , "GBP", 5.5766, 5.6892));
        currencyRateList.add(createExpectedCurrencyRate("jen (Japonia)", 100 , "JPY", 2.3677, 2.4155));
        currencyRateList.add(createExpectedCurrencyRate("korona czeska", 1 , "CZK", 0.1358, 0.1386));
        currencyRateList.add(createExpectedCurrencyRate("korona duńska", 1 , "DKK", 0.5094, 0.5196));
        currencyRateList.add(createExpectedCurrencyRate("korona estońska", 1 , "EEK", 0.2427, 0.2477));
        currencyRateList.add(createExpectedCurrencyRate("korona norweska", 1 , "NOK", 0.4698, 0.4792));
        currencyRateList.add(createExpectedCurrencyRate("korona szwedzka", 1 , "SEK", 0.4101, 0.4183));
        currencyRateList.add(createExpectedCurrencyRate("SDR (MFW)", 1 , "XDR", 4.2883, 4.3749));

        return currencyRateList;
    }

    private CurrencyRate createExpectedCurrencyRate(String currencyName, int converter, String currencyCode, double buyingRate, double sellingRate) {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCurrencyName(currencyName);
        currencyRate.setConverter(converter);
        currencyRate.setCurrencyISOCode(currencyCode);
        currencyRate.setBuyingRate(buyingRate);
        currencyRate.setSellingRate(sellingRate);

        return currencyRate;
    }

    private Document createExpectedDocument() {
        File file = new File("c073z070413.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }
}
