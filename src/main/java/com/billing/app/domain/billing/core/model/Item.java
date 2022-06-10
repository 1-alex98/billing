package com.billing.app.domain.billing.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Item {
    private final String name;
    private final int quantity;
    private final int price;
    private final boolean imported;
    private final String typeId;
    private int sumRawPrice;
    private int sumTaxes;
    private int sumAfterTaxesPrice;
}
