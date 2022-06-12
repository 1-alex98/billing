package com.billing.app.domain.billing.core.ports.incoming;

import com.billing.app.domain.billing.core.model.Bill;

public interface BillCreationPort {
    Bill bill(Bill unfinishedBill);
}
