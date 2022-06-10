package com.billing.app.domain.billing.core.ports.incoming;

import com.billing.app.domain.billing.application.model.RequestBillItem;
import com.billing.app.domain.billing.core.model.Bill;

import java.util.Set;

public interface BillCreationPort {
    Bill bill(Bill unfinishedBill);
}
