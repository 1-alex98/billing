package com.billing.app.domain.types.core.service;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.InitializeTypesPort;
import com.billing.app.domain.types.core.ports.outgoing.TypeDataBasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StandardTypesService implements InitializeTypesPort {

    private final TypeDataBasePort typeDataBasePort;
    public static final String OTHER_ID = "other";

    @Override
    public void initialize() {
        typeDataBasePort.save(new ProductType(
                OTHER_ID,
                "Other",
                "Products that do not fall into any other category",
                0.10,
                0.05
        ));
        typeDataBasePort.save(new ProductType(
                "books",
                "Books",
                "Books can be read",
                0,
                0.05
        ));
        typeDataBasePort.save(new ProductType(
                "meds",
                "medical products",
                "Needed for medicine",
                0,
                0.05
        ));
        typeDataBasePort.save(new ProductType(
                "food",
                "food",
                "food and other eatable things",
                0,
                0.05
        ));
    }
}
