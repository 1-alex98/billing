package com.billing.app.domain.billing.core.service;

import com.billing.app.domain.billing.core.ports.outgoing.ReadTaxesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillCreationServiceTest {

    private BillCreationService instance;

    @Mock
    private ReadTaxesPort readTaxesPort;

    @BeforeEach
    public void setUp(){
        instance = new BillCreationService(readTaxesPort);
    }

    @Test
    void given_importedItem_when_taxIsCalculates_should_alsoApplyCustoms() {
        when(readTaxesPort.getCustomsRate("wood"))
                .thenReturn(Optional.of(0.05));
        when(readTaxesPort.getValueAddedTaxRateRate("wood"))
                .thenReturn(Optional.of(0.10));
        assertThat(instance.calculateTax("wood", 1000, true))
                .isEqualTo(150);

    }

    @Test
    void given_notImportedItem_when_taxIsCalculates_should_onlyApplyNormalTax() {
        when(readTaxesPort.getValueAddedTaxRateRate("wood"))
                .thenReturn(Optional.of(0.10));
        assertThat(instance.calculateTax("wood", 1000, false))
                .isEqualTo(100);
    }

    @Test
    void given_notRoundedValues_when_roundedAsSalesTax_should_beRoundedToNearestFiveCents() {
        assertThat(instance.roundSaleTax(892)).isEqualTo(890);
        assertThat(instance.roundSaleTax(893)).isEqualTo(895);
        assertThat(instance.roundSaleTax(823)).isEqualTo(825);
        assertThat(instance.roundSaleTax(822)).isEqualTo(820);
        assertThat(instance.roundSaleTax(4000)).isEqualTo(4000);
    }
}