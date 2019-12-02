package com.ffscrapper.demo.service;

import com.ffscrapper.demo.entity.Event;
import com.ffscrapper.demo.rest.repository.ComparerRestRepository;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EventService {

    private ComparerRestRepository comparerRestRepository;
    private String content;


    public EventService(ComparerRestRepository comparerRestRepository) {
        this.comparerRestRepository = comparerRestRepository;
        this.generateEvents();
    }


    public void generateEvents() {
        this.content = null;
        HttpURLConnection connection = null;
        String[] months = {"may", "jun", "jul"};
        for (int i = 0; i < months.length; i++) {
            if (!HttpService.pingURL("https://www.forexfactory.com/calendar.php?month=" + months[i] + ".2019", 1000)) {
                HttpService.setProxy();
            }
            try {
                connection = (HttpURLConnection) new URL("https://www.forexfactory.com/calendar.php?month=" + months[i] + ".2019").openConnection();
                Scanner scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                this.allIndexOf(content, "calendar__currency currency \" title=\"\">", months[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void allIndexOf(String content, String toSearch, String month) {
        String stringPos = "";
        String date = "";

        Pattern regexImpact = Pattern.compile("^[^\"]+");
        Pattern regexSearch = Pattern.compile("^[^<]+");
        String impactSearch = "calendar__impact-icon--screen\"> <span title=\"";
        String titleSearch = "<span class=\"calendar__event-title\">";
        String forecastSearch = "<td class=\"calendar__cell calendar__forecast forecast\">";
        String previousSearch = "<td class=\"calendar__cell calendar__previous previous\">";
        String actualSearch = "<td class=\"calendar__cell calendar__actual actual\">";
        String worse = "<span class=\"worse\">";
        String better = "<span class=\"better\">";


        for (int pos = content.indexOf(toSearch); pos != -1; pos = content.indexOf(toSearch, pos + 1)) {
            stringPos = content.substring((pos + toSearch.length()), (pos + toSearch.length() + 2500));
            stringPos = stringPos.replaceAll(worse, "");
            stringPos = stringPos.replaceAll(better, "");
            stringPos = stringPos.replaceAll("<span class=\"revised(.+?)\" title=\"Revised From(.+?)>", "");

            Event event = new Event();

            event.setCountry(stringPos.substring(1, 4));
            event.setImpact(this.substringSearch(stringPos, impactSearch, regexImpact));
            event.setTitle(this.substringSearch(stringPos, titleSearch, regexSearch));
            event.setDate(date);
            event.setActual(this.substringSearch(stringPos, actualSearch, regexSearch));
            event.setPrevious(this.substringSearch(stringPos, previousSearch, regexSearch));
            event.setForecast(this.substringSearch(stringPos, forecastSearch, regexSearch));
            event.setDate(month);
            event.setEstimation(EstimationService.getJson(event));
            this.comparerRestRepository.save(event);
        }
    }

    private String substringSearch(String str, String substring, Pattern regex) {
        Matcher matcher = regex.matcher(str.substring((str.indexOf(substring) + substring.length()), (str.indexOf(substring) + substring.length() + 100)));
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "";
        }
    }
}
