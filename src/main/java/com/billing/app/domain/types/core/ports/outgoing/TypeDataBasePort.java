package com.billing.app.domain.types.core.ports.outgoing;

import com.billing.app.domain.types.core.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TypeDataBasePort extends JpaRepository<ProductType, String> {

}
