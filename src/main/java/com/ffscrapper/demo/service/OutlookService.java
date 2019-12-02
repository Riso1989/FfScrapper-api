package com.ffscrapper.demo.service;

import com.ffscrapper.demo.entity.Outlook;
import com.ffscrapper.demo.rest.repository.OutlookRestRepository;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OutlookService {
    String content = "";

    private OutlookRestRepository outlookRestRepository;

    public OutlookService(OutlookRestRepository outlookRestRepository) {
        this.outlookRestRepository = outlookRestRepository;
    }

    public void scrapContent() {
        List<String> majorPairs = Arrays.asList("EURUSD", "NZDUSD", "GBPUSD", "AUDUSD", "USDCAD", "USDCHF", "USDJPY");
        majorPairs.forEach( pair -> {
            this.content = "";
            HttpURLConnection connection = null;
            if (!HttpService.pingURL("https://www.myfxbook.com/community/outlook/" + pair, 1000)) {
                HttpService.setProxy();
            }
            try {
                connection = (HttpURLConnection) new URL("https://www.myfxbook.com/community/outlook/" + pair).openConnection();
                Scanner scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                this.content = scanner.next();
                scanner.close();
                this.generateOutlook(this.content, pair);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    private void generateOutlook(String content, String pair) {
        String searchPattern = "<td>Short</td>";
        int position = content.indexOf(searchPattern);
        content = content.substring(position, position + 600);
        content = content.replaceAll("\\/|tr|td|table|div|Lots|>|<| ", "");
        content = content + pair;
        this.setShortEntity(content);
    }

    private void setShortEntity(String content) {
        String[] splitByLines = content.split("\r\n");
        List<String> list = new ArrayList<String>(Arrays.asList(splitByLines));
        list.removeAll(Arrays.asList("", null));

        Outlook outlook = new Outlook();
        outlook.setShortAction(list.get(0));
        outlook.setShortPercentage(list.get(1));
        outlook.setShortVolume(splitByLines[2]);
        outlook.setShortPositions(list.get(3));

        outlook.setLongAction(list.get(4));
        outlook.setLongPercentage(list.get(5));
        outlook.setLongVolume(list.get(6));
        outlook.setLongPositions(list.get(7));
        outlook.setSymbol(list.get(8));

        outlookRestRepository.save(outlook);

    }

    private String substringSearch(String str, String substring, Pattern regex) {
        Matcher matcher = regex.matcher(str.substring((str.indexOf(substring)), (str.indexOf(substring) + substring.length() + 1000)));
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "";
        }
    }
}
