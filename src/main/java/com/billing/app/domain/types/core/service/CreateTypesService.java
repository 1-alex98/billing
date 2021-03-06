package com.billing.app.domain.types.core.service;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.CreateTypePort;
import com.billing.app.domain.types.core.ports.outgoing.TypeDataBasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTypesService implements CreateTypePort {

    public static final String OTHER_ID = "other";
    private final TypeDataBasePort typeDataBasePort;

    @Override
    public ProductType createProductType(ProductType productType) {
        return typeDataBasePort.save(productType);
    }
}
