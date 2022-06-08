package com.billing.app.domain.types.core.ports.incoming;

import com.billing.app.domain.types.core.model.ProductType;

public interface CreateTypePort {
    ProductType createProductType(ProductType productType);
}
