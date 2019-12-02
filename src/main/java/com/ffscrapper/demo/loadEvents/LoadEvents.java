package com.ffscrapper.demo.loadEvents;

import com.ffscrapper.demo.service.EventService;
import com.ffscrapper.demo.service.OutlookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadEvents {

    @Bean
    CommandLineRunner initEvents(EventService eventService, OutlookService outlookService) {
        return args -> {
            eventService.generateEvents();
            outlookService.scrapContent();
        };
    }
}
