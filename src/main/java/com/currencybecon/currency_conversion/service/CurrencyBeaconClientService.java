package com.currencybecon.currency_conversion.service;

import com.currencybecon.currency_conversion.dto.Currency;
import com.currencybecon.currency_conversion.dto.CurrencyBeaconResponse;
import com.currencybecon.currency_conversion.entity.CurrencyConversionRequest;

import java.util.List;

public interface CurrencyBeaconClientService {
    CurrencyBeaconResponse convertCurrency(CurrencyConversionRequest currencyConversionRequest);

    List<Currency> getCurrencyList();
}
