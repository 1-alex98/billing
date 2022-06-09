package com.billing.app.domain.billing.application.model;

import lombok.Value;
import org.springframework.lang.Nullable;

public record RequestBillItem(@Nullable String typeId, String name, int quantity, double price, boolean imported) {
}
