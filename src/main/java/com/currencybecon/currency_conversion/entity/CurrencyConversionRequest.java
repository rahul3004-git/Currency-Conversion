package com.currencybecon.currency_conversion.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyConversionRequest {

    private String fromCurrency;

    private String toCurrency;

    private Integer amount;
}
