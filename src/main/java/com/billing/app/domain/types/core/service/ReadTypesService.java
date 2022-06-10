package com.billing.app.domain.types.core.service;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.ReadTaxRatesPort;
import com.billing.app.domain.types.core.ports.incoming.ReadTypesPort;
import com.billing.app.domain.types.core.ports.outgoing.TypeDataBasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.billing.app.domain.types.core.service.StandardTypesService.OTHER_ID;

@Service
@RequiredArgsConstructor
public class ReadTypesService implements ReadTypesPort, ReadTaxRatesPort {

    private final TypeDataBasePort typeDataBasePort;

    @Override
    public Set<ProductType> readAllTypes() {
        return new HashSet<>(typeDataBasePort.findAll());
    }

    @Override
    public Optional<Double> getCustomsRate(@Nullable String typeId) {
        final var typeIdOrDefault = Optional.ofNullable(typeId).orElse(OTHER_ID);
        return typeDataBasePort.findById(typeIdOrDefault).map(ProductType::getCustoms);
    }

    @Override
    public Optional<Double> getTaxRate(@Nullable String typeId) {
        final var typeIdOrDefault = Optional.ofNullable(typeId).orElse(OTHER_ID);
        return typeDataBasePort.findById(typeIdOrDefault).map(ProductType::getValueAddedTax);
    }
}
