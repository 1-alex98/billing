package com.billing.app.domain.billing.application;

import com.billing.app.domain.billing.application.model.RequestBillItem;
import com.billing.app.domain.billing.core.model.Bill;
import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.CreateTypePort;
import com.billing.app.domain.types.core.ports.incoming.ReadTypesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillingController {

    private final CreateTypePort createTypePort;

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Bill> getBillFromParsedData(@RequestBody Set<RequestBillItem> billItems){
        return ResponseEntity.ok(new Bill());
    }
}