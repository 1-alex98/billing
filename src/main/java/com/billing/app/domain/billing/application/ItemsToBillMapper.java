package com.billing.app.domain.billing.application;

import com.billing.app.domain.billing.application.model.RequestBillItem;
import com.billing.app.domain.billing.core.model.Bill;
import com.billing.app.domain.billing.core.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ItemsToBillMapper {
    Bill map(List<RequestBillItem> items){
        final var domainItems = items.stream()
                .map(requestBillItem -> new Item(
                        requestBillItem.name(),
                        requestBillItem.quantity(),
                        requestBillItem.price(),
                        requestBillItem.imported(),
                        requestBillItem.typeId()
                    )
                )
                .collect(Collectors.toList());
        return new Bill(domainItems);
    }
}
