package pl.michalpajak.currencyCalculator.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.michalpajak.currencyCalculator.models.CurrencyRate;
import pl.michalpajak.currencyCalculator.models.CurrencyRateTable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class ParserCurrencyRateTableService {

    private Document document;
    private List<CurrencyRateTable> currencyRateTablesList;

    public void createCurrencyRateTablesList() {

    }

    public void createDocument(String fileName) {

        try {
            URL url = new URL("http://www.nbp.pl/kursy/xml/" + fileName + ".xml");
            URLConnection connection = url.openConnection();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(connection.getInputStream());
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CurrencyRateTable createCurrencyRateTable() {
        CurrencyRateTable currencyRateTable = new CurrencyRateTable();

        currencyRateTable.setTableNumber(document.getElementsByTagName("numer_tabeli").item(0).getTextContent());
        currencyRateTable.setNotationDate(LocalDate.parse(
                document.getElementsByTagName("data_notowania").item(0)
                        .getTextContent(), DateTimeFormatter.ISO_DATE));
        currencyRateTable.setPublicationDate(LocalDate.parse(
                document.getElementsByTagName("data_publikacji").item(0)
                        .getTextContent(), DateTimeFormatter.ISO_DATE));
        currencyRateTable.setCurrencyRateList(createCurrencyRateList());

        return currencyRateTable;
    }

    private List<CurrencyRate> createCurrencyRateList() {
        List<CurrencyRate> currencyRateList = new ArrayList<>();

        NodeList positionNodes = document.getElementsByTagName("pozycja");

        for (int i = 0; i < positionNodes.getLength(); i++) {
            Node node = positionNodes.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType())
                currencyRateList.add(createCurrencyRate(node));
        }

        return currencyRateList;
    }

    private CurrencyRate createCurrencyRate(Node node) {
        Element element = (Element) node;

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCurrencyName(element.getElementsByTagName("nazwa_waluty").item(0).getTextContent());
        currencyRate.setConverter(Integer.parseInt(element.getElementsByTagName("przelicznik")
                .item(0).getTextContent()));
        currencyRate.setCurrencyISOCode(element.getElementsByTagName("kod_waluty")
                .item(0).getTextContent());
        currencyRate.setBuyingRate(Double.parseDouble(element.getElementsByTagName("kurs_kupna")
                .item(0).getTextContent().replace(',', '.')));
        currencyRate.setSellingRate(Double.parseDouble(element.getElementsByTagName("kurs_sprzedazy")
                .item(0).getTextContent().replace(',', '.')));

        return currencyRate;
    }
}
