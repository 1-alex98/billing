package com.billing.app.domain.types.core.service;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.CreateTypePort;
import com.billing.app.domain.types.core.ports.incoming.ReadTypesPort;
import com.billing.app.domain.types.core.ports.outgoing.TypeDataBasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadTypesService implements ReadTypesPort {

    private final TypeDataBasePort typeDataBasePort;

    @Override
    public Set<ProductType> readAllTypes() {
        return new HashSet<>(typeDataBasePort.findAll());
    }
}
