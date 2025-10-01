package com.currencybecon.currency_conversion.controller;

import com.currencybecon.currency_conversion.dto.CurrencyBeaconResponse;
import com.currencybecon.currency_conversion.entity.CurrencyConversionRequest;
import com.currencybecon.currency_conversion.service.CurrencyBeaconClientService;
import com.currencybecon.currency_conversion.serviceImpl.CurrencyBeaconClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CurrencyConversionController {

    @Autowired
    private CurrencyBeaconClient currencyBeaconClient;

    @Autowired
    private CurrencyBeaconClientService currencyBeaconClientService;


    @GetMapping("/convert")
    public String getCurrencies(Model model) {
        model.addAttribute("currencyConversionRequest", new CurrencyConversionRequest());
        model.addAttribute("currencies", currencyBeaconClientService.getCurrencyList());
        return "currency_converter";
    }

    @PostMapping("/convert")
    public String convert(@ModelAttribute("currencyConversionRequest") CurrencyConversionRequest currencyConversionRequest, Model model) {
        ;
        model.addAttribute("currencies", currencyBeaconClientService.getCurrencyList());
        CurrencyBeaconResponse response = currencyBeaconClientService.convertCurrency(currencyConversionRequest);
        model.addAttribute("conversionResult", response);
        return "currency_converter";
    }
}
