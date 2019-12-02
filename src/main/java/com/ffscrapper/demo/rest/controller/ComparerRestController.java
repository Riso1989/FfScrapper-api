package com.ffscrapper.demo.rest.controller;

import com.ffscrapper.demo.entity.Event;
import com.ffscrapper.demo.rest.repository.ComparerRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Service
public class ComparerRestController {




//    @RequestMapping(path = "events/getEvents", produces = "application/json")
//    public ResponseEntity<List<Event>> getEvent() {
//        List<Event> events = new ArrayList<>();
//        FFScrapperService ffs = new FFScrapperService();
//        events = ffs.getEvents();
//        events.forEach( eventDto -> {
//        });
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Access-Control-Allow-Origin", "*");
//        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(events, responseHeaders, HttpStatus.ACCEPTED);
//
//    }
}
