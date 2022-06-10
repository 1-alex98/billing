package com.billing.app.domain.billing.application;

import com.billing.app.domain.billing.application.model.RequestBillItem;
import com.billing.app.domain.billing.core.model.Bill;
import com.billing.app.domain.billing.core.ports.incoming.BillCreationPort;
import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.CreateTypePort;
import com.billing.app.domain.types.core.ports.incoming.ReadTypesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillingController {

    private final BillCreationPort billCreationPort;
    private final ItemsToBillMapper itemsToBillMapper;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<Bill> getBillFromParsedData(@RequestBody List<RequestBillItem> billItems){
        return ResponseEntity.ok(getBill(billItems));
    }
    @PostMapping(produces = {"application/pdf"})
    public void getPDFBillFromParsedData(@RequestBody List<RequestBillItem> billItems){
        throw new RuntimeException("Not implemented");
    }

    private Bill getBill(List<RequestBillItem> billItems) {
        return billCreationPort.bill(itemsToBillMapper.map(billItems));
    }
}