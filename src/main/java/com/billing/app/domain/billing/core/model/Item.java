package com.billing.app.domain.billing.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Item {
    private final int quantity;
    private final float price;
    private final boolean imported;
    private final String typeId;
    private float sumRawPrice;
    private float sumTaxes;
    private float sumAfterTaxesPrice;
}