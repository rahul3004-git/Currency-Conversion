package com.currencybecon.currency_conversion.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Currency {

    private int id;
    private String name;
    private String short_code;
    private String code;
    private int precision;
    private int subunit;
    private String symbol;
    private boolean symbolFirst;
    private String decimalMark;
    private String thousandsSeparator;
}
