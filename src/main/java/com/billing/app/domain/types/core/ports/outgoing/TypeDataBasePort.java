package com.billing.app.domain.types.core.ports.outgoing;

import com.billing.app.domain.types.core.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDataBasePort extends JpaRepository<ProductType, String> {

}
