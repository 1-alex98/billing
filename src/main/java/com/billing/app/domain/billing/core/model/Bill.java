package com.billing.app.domain.billing.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

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
