package com.billing.app.domain.billing.infrastructure.adapter;

import com.billing.app.domain.billing.core.ports.outgoing.ReadTaxesPort;
import com.billing.app.domain.types.core.ports.incoming.ReadTaxRatesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReadTaxesAdapter implements ReadTaxesPort {

    private final ReadTaxRatesPort readTaxRatesPort;

    @Override
    public Optional<Double> getCustomsRate(@Nullable String typeId) {
        return readTaxRatesPort.getCustomsRate(typeId);
    }

    @Override
    public Optional<Double> getValueAddedTaxRateRate(@Nullable String typeId) {
        return readTaxRatesPort.getTaxRate(typeId);
    }
}
