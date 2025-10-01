package com.currencybecon.currency_conversion.serviceImpl;

import com.currencybecon.currency_conversion.dto.Currency;
import com.currencybecon.currency_conversion.dto.CurrencyBeaconResponse;
import com.currencybecon.currency_conversion.dto.CurrencyResponse;
import com.currencybecon.currency_conversion.entity.ConversionRate;
import com.currencybecon.currency_conversion.entity.CurrencyConversionRequest;
import com.currencybecon.currency_conversion.repository.ConversionRateRepository;
import com.currencybecon.currency_conversion.service.CurrencyBeaconClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CurrencyBeaconClient implements CurrencyBeaconClientService {

    private static final String API_KEY = "Mai44E7fPvS63JlMgbruZsshqpWD1rQu";
    private static final String BASE_URL = "https://api.currencybeacon.com/v1";
    private static final String ENDPOINT_CONVERT = "/convert";
    private static final Duration CACHE_DURATION = Duration.ofHours(1);

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    private final WebClient webClient = WebClient
            .builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY)
            .build();

    @Override
    public CurrencyBeaconResponse convertCurrency(CurrencyConversionRequest request) {
        String source = request.getFromCurrency().toUpperCase();
        String target = request.getToCurrency().toUpperCase();

        Optional<ConversionRate> cachedRateOpt =
                conversionRateRepository.findBySourceCurrencyAndTargetCurrency(source, target);

        double rate;
        if (cachedRateOpt.isPresent()) {
            ConversionRate cachedRate = cachedRateOpt.get();
            boolean isValidCache = Duration.between(cachedRate.getFetchedAt(), LocalDateTime.now())
                    .compareTo(CACHE_DURATION) < 0;
            if (isValidCache) {
                rate = cachedRate.getRate();
            } else {
                rate = fetchAndStoreNewRate(source, target, cachedRate);
            }
        } else {
            rate = fetchAndStoreNewRate(source, target, null);
        }

        double convertedAmount = rate * request.getAmount();

        return CurrencyBeaconResponse.builder()
                .from(source)
                .to(target)
                .amount(request.getAmount())
                .value(convertedAmount)
                .date(LocalDateTime.now().toLocalDate().toString())
                .timestamp(System.currentTimeMillis() / 1000L)
                .build();
    }

    private double fetchAndStoreNewRate(String source, String target, ConversionRate existing) {

        CurrencyBeaconResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ENDPOINT_CONVERT)
                        .queryParam("from", source)
                        .queryParam("to", target)
                        .queryParam("amount", 1)
                        .build())
                .retrieve()
                .bodyToMono(CurrencyBeaconResponse.class)
                .block();

        if (response == null || response.getResponse() == null) {
            throw new RuntimeException("Failed to fetch data from CurrencyBeacon API");
        }

        double rate = response.getResponse().getValue();

        if (existing != null) {
            existing.setRate(rate);
            existing.setFetchedAt(LocalDateTime.now());
            conversionRateRepository.save(existing);
        } else {
            ConversionRate newRate = ConversionRate.builder()
                    .sourceCurrency(source)
                    .targetCurrency(target)
                    .rate(rate)
                    .fetchedAt(LocalDateTime.now())
                    .build();
            conversionRateRepository.save(newRate);
        }
        return rate;
    }

    @Override
    public List<Currency> getCurrencyList() {
        CurrencyResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/currencies")
                        .build())
                .retrieve()
                .bodyToMono(CurrencyResponse.class)
                .block();

        return response != null ? response.getResponse() : List.of();
    }

}

