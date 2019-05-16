package pl.michalpajak.currencyCalculator;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.michalpajak.currencyCalculator.services.SearchCurrencyRateFileService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchCurrencyRateFileServiceTest {

    @Autowired
    SearchCurrencyRateFileService searchCurrencyRateFileService;

    @Test
    public void shouldGetFileNamesListFromFileFromOneYear() {
        List<String> expectedList = Arrays.asList("c001z190102", "c002z190103", "c003z190104", "c004z190107",
                "c005z190108", "c006z190109", "c007z190110", "c008z190111", "c009z190114", "c010z190115",
                "c011z190116", "c012z190117", "c013z190118", "c014z190121", "c015z190122", "c016z190123",
                "c017z190124", "c018z190125", "c019z190128", "c020z190129", "c021z190130", "c022z190131",
                "c023z190201", "c024z190204", "c025z190205", "c026z190206", "c027z190207", "c028z190208",
                "c029z190211", "c030z190212", "c031z190213", "c032z190214", "c033z190215", "c034z190218",
                "c035z190219", "c036z190220", "c037z190221", "c038z190222", "c039z190225", "c040z190226",
                "c041z190227", "c042z190228", "c043z190301", "c044z190304", "c045z190305", "c046z190306",
                "c047z190307", "c048z190308", "c049z190311", "c050z190312", "c051z190313", "c052z190314",
                "c053z190315", "c054z190318", "c055z190319", "c056z190320", "c057z190321", "c058z190322",
                "c059z190325", "c060z190326", "c061z190327", "c062z190328", "c063z190329", "c064z190401",
                "c065z190402", "c066z190403", "c067z190404", "c068z190405", "c069z190408", "c070z190409",
                "c071z190410", "c072z190411", "c073z190412", "c074z190415", "c075z190416", "c076z190417",
                "c077z190418", "c078z190419", "c079z190423", "c080z190424", "c081z190425", "c082z190426",
                "c083z190429", "c084z190430", "c085z190502", "c086z190506", "c087z190507", "c088z190508",
                "c089z190509", "c090z190510", "c091z190513", "c092z190514", "c093z190515", "c094z190516");

        Assertions.assertArrayEquals(expectedList.toArray(), searchCurrencyRateFileService
                .getFileNamesListFromFileFromOneYear(LocalDate.now().getYear()).toArray());
    }

    @Test
    public void shouldGetFileNamesFromDateRange() {
        List<String> expectedList = Arrays.asList("c062z190328", "c063z190329", "c064z190401",
                "c065z190402", "c066z190403", "c067z190404");

        Assertions.assertArrayEquals(expectedList.toArray(), searchCurrencyRateFileService
                .getFileNamesFromDateRange(LocalDate.parse("2019-03-28", DateTimeFormatter.ISO_DATE),
                        LocalDate.parse("2019-04-04", DateTimeFormatter.ISO_DATE)).toArray());
    }
}
