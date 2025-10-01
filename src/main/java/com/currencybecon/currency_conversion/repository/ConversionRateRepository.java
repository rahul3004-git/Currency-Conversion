package com.currencybecon.currency_conversion.repository;

import com.currencybecon.currency_conversion.entity.ConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversionRateRepository extends JpaRepository<ConversionRate, Long> {

    Optional<ConversionRate> findBySourceCurrencyAndTargetCurrency(String source, String target);
}
