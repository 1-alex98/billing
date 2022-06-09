package com.billing.app.domain.billing.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private Set<Item> items;
    private float sumRawPrice;
    private float sumTaxes;
    private float sumAfterTaxesPrice;
}
