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
public class Outlook {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;
    private String shortAction;
    private String shortPercentage;
    private String shortVolume;
    private String shortPositions;

    private String longAction;
    private String longPercentage;
    private String longVolume;
    private String longPositions;
}
