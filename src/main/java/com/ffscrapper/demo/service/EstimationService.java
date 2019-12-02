package com.ffscrapper.demo.service;

import com.ffscrapper.demo.entity.Event;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EstimationService {
    static double getJson(Event event) {
        double estimation;
        double imp;
                if (event.getForecast() != null) {
                    event.setForecast(changeToNumbers(event.getForecast()));
                }
                if (event.getPrevious() != null) {
                    event.setPrevious(changeToNumbers(event.getPrevious()));
                }
                if (event.getActual() != null) {
                    event.setActual(changeToNumbers(event.getActual()));
                }
                    switch (event.getImpact()) {
                        case "Medium Impact Expected":
                            imp = 1.1;
                            break;
                        case "High Impact Expected":
                            imp = 1.2;
                            break;
                        default:
                            imp = 1;
                    }

                        estimation = percentActualBetterThanForecast(event.getActual(), event.getForecast(), event.getPrevious(), imp);


        return estimation;
    }

    private static String changeToNumbers(String number) {

        if (number.contains("K")) {
            number = number.replace("K", "");
            number = String.valueOf(Double.parseDouble(number) * 1000L);
        }
        if (number.contains("M")) {
            number = number.replace("M", "");
            number = String.valueOf(Double.parseDouble(number) * 1000000L);
        }
        if (number.contains("B")) {
            number = number.replace("B", "");
            number = String.valueOf(Double.parseDouble(number) * 1000000000L);
        }
        if (number.contains("T")) {
            number = number.replace("T", "");
            number = String.valueOf(Double.parseDouble(number) * 1000000000000L);
        }

        number = number.replaceAll("(\\d-\\d-\\d)", "");
        Pattern regexSearch = Pattern.compile("(-?[0-9]+\\.?[0-9]+)");
        Matcher matcher = regexSearch.matcher(number);

        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "";
        }
    }

    private static double percentActualBetterThanForecast(String act, String fore, String prev, double importance) {

        if (act.equals("")) {
            return 0;
        } else if (!fore.equals("")) {
            return getEstimation(Double.parseDouble(fore), Double.parseDouble(act), importance);
        } else if (!prev.equals("")) {
            return getEstimation(Double.parseDouble(act), Double.parseDouble(prev), importance);
        } else {
            return 0;
        }
    }

//    private static double actualBetterThanForecast(String act, String fore, String prev, JSONArray gradesF, JSONArray gradesP) {
//        if (act.equals("")) {
//            return 0;
//        } else if (!fore.equals("")) {
//            return getGrade(gradesF, ((Float.parseFloat(act) - Float.parseFloat(fore)) / Math.abs(Float.parseFloat(fore))));
//        } else if (!prev.equals("")) {
//            return getGrade(gradesP, ((Float.parseFloat(act) - Float.parseFloat(prev) / Float.parseFloat(prev))));
//        } else {
//            return 0;
//        }
//    }
//
//    private static double percentActualLessThanForecast(String act, String fore, String prev, double importance) {
//        if (act.equals("")) {
//            return 0;
//        } else if (!fore.equals("")) {
//            return getEstimation(Double.parseDouble(act), Double.parseDouble(fore), importance);
//        } else if (!prev.equals("")) {
//            return getEstimation(Double.parseDouble(prev), Double.parseDouble(act), importance);
//        } else {
//            return 0;
//        }
//    }

    private static double getEstimation(double num1, double num2, double importance) {
        double est;
        est = ((Math.abs(num1) - Math.abs(num2))) / Math.max(Math.abs(num1), Math.abs(num2)) * 100 * importance;

        if (Double.isNaN(est) || Double.isInfinite(est)) {
            return 0;
        } else {
            return round(est, 2);
        }
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
