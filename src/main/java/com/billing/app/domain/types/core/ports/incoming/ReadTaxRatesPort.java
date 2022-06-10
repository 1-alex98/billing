package com.billing.app.domain.types.core.ports.incoming;

import java.util.Optional;

public interface ReadTaxRatesPort {
    Optional<Double> getCustomsRate(String typeId);
    Optional<Double> getTaxRate(String typeId);
}
