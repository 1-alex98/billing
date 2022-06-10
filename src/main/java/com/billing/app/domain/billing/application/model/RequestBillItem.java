package com.billing.app.domain.billing.application.model;

import org.springframework.lang.Nullable;

public record RequestBillItem(@Nullable String typeId, String name, int quantity, int price, boolean imported) {
}
