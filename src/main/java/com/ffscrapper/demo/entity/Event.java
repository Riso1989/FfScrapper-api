package com.ffscrapper.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
public class Event {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String date;
    private String forecast;
    private String previous;
    private String actual;
    private String impact;
    private String country;
    private double estimation;

    public Event() {}
}
