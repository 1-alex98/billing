package com.billing.app.domain.billing.core.ports.outgoing;

import java.util.Optional;

public interface ReadTaxesPort {
    Optional<Double> getCustomsRate(String typeId);
    Optional<Double> getValueAddedTaxRateRate(String typeId);
}
