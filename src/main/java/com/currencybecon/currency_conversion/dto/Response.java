package com.currencybecon.currency_conversion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {
    private long timestamp;
    private String date;
    private String from;
    private String to;
    private int amount;
    private double value;
}
