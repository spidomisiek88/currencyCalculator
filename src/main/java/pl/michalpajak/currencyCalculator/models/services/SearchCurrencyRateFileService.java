package pl.michalpajak.currencyCalculator.models.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchCurrencyRateFileService {

    public List<String> getFileNamesFromDateRange(LocalDate beginDate, LocalDate endDate) {

        List<String> fileNames = new ArrayList<String>();
        int year = beginDate.getYear();
        boolean endOfRange = true;

        while (endOfRange) {
            for (String fileName : getFileNamesListFromFileFromOneYear(year)) {
                if (endDate.isBefore(LocalDate.parse(fileName.substring(5), DateTimeFormatter.ofPattern("yyMMdd")))) {
                    endOfRange = false;
                } else if (beginDate.isBefore(LocalDate.parse(fileName.substring(5), DateTimeFormatter.ofPattern("yyMMdd"))) ||
                        beginDate.isEqual(LocalDate.parse(fileName.substring(5), DateTimeFormatter.ofPattern("yyMMdd")))){
                   fileNames.add(fileName);
                }
            }

            year++;
        }

        return fileNames;
    }

    public List<String> getFileNamesListFromFileFromOneYear(int year) {

        List<String> fileNamesListFromFileFromOneYear = new ArrayList<String>();

        URL url = null;
        URLConnection connection = null;
        InputStream inputStream = null;

        try {
            if (LocalDate.now().getYear() == year)
                url = new URL("https://www.nbp.pl/kursy/xml/dir.txt");
            else
                url = new URL("https://www.nbp.pl/kursy/xml/dir" + year + ".txt");

            connection = url.openConnection();
            inputStream = connection.getInputStream();
            fileNamesListFromFileFromOneYear = readFromInputStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileNamesListFromFileFromOneYear;
    }

    private List<String> readFromInputStream(InputStream inputStream) throws IOException{
        List<String> filesLineList = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("\uFEFF"))
                    line = line.substring(1);
                if (line.startsWith("c"))
                    filesLineList.add(line);
            }
        }
        return filesLineList;
    }
}
