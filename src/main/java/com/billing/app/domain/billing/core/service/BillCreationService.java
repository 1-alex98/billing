package com.billing.app.domain.billing.core.service;

import com.billing.app.domain.billing.core.model.Bill;
import com.billing.app.domain.billing.core.model.Item;
import com.billing.app.domain.billing.core.ports.incoming.BillCreationPort;
import com.billing.app.domain.billing.core.ports.outgoing.ReadTaxesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BillCreationService implements BillCreationPort {

    private final ReadTaxesPort readTaxesPort;
    @Override
    public Bill bill(Bill unfinishedBill) {
        unfinishedBill.getItems()
                .forEach(this::calculateItem);
        calculateSumsOnBill(unfinishedBill);
        return unfinishedBill;
    }

    private void calculateSumsOnBill(Bill bill) {
        bill.setSumRawPrice(
                bill.getItems().stream().mapToInt(Item::getSumRawPrice).sum()
        );
        bill.setSumTaxes(
                bill.getItems().stream().mapToInt(Item::getSumTaxes).sum()
        );
        bill.setSumAfterTaxesPrice(
                bill.getSumRawPrice() + bill.getSumTaxes()
        );
    }

    private void calculateItem(Item item) {
        item.setSumRawPrice(item.getQuantity()* item.getPrice());
        item.setSumTaxes(
            calculateTax(item.getTypeId(), item.getSumRawPrice(), item.isImported())
        );
        item.setSumAfterTaxesPrice(
                item.getSumTaxes() + item.getSumRawPrice()
        );
    }

    int calculateTax(String typeId, int sumRawPrice, boolean imported) {
        int sum = 0;
        if(imported){
            final var customsRate = readTaxesPort.getCustomsRate(typeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown type id %s".formatted(typeId)));
            sum += roundSaleTax(sumRawPrice * customsRate);
        }

        final var vatRate = readTaxesPort.getValueAddedTaxRateRate(typeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown type id %s".formatted(typeId)));
        sum += roundSaleTax(sumRawPrice * vatRate);
        return sum;
    }

    int roundSaleTax(double sum) {
        return Math.toIntExact(Math.round(sum / 5.0) * 5);
    }
}
