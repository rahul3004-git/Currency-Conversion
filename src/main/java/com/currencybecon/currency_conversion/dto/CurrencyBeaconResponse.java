package com.currencybecon.currency_conversion.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyBeaconResponse {

    private Meta meta;
    private Response response;
    private long timestamp;
    private String date;
    private String from;
    private String to;
    private int amount;
    private double value;

}
