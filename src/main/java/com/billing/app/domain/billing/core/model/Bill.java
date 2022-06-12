package com.billing.app.domain.billing.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private List<Item> items;
    private int sumRawPrice;
    private int sumTaxes;
    private int sumAfterTaxesPrice;

    public Bill(List<Item> items) {
        this.items = items;
    }
}
