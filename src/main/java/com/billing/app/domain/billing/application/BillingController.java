package com.billing.app.domain.billing.application;

import com.billing.app.domain.billing.application.model.RequestBillItem;
import com.billing.app.domain.billing.core.model.Bill;
import com.billing.app.domain.billing.core.ports.incoming.BillCreationPort;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillingController {

    private final BillCreationPort billCreationPort;
    private final ItemsToBillMapper itemsToBillMapper;
    private final BillToPDFMapper billToPDFMapper;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<Bill> getBillFromParsedData(@RequestBody List<RequestBillItem> billItems) {
        return ResponseEntity.ok(getBill(billItems));
    }

    @PostMapping(produces = {"application/pdf"})
    public void getPDFBillFromParsedData(@RequestBody List<RequestBillItem> billItems, HttpServletResponse response) throws DocumentException, IOException {
        billToPDFMapper.generatePDF(response.getOutputStream(), getBill(billItems));

    }

    private Bill getBill(List<RequestBillItem> billItems) {
        return billCreationPort.bill(itemsToBillMapper.map(billItems));
    }
}