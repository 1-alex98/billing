package com.billing.app.domain.types.application;

import com.billing.app.domain.types.core.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.CreateTypePort;
import com.billing.app.domain.types.core.ports.incoming.InitializeTypesPort;
import com.billing.app.domain.types.core.ports.incoming.ReadTypesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/types")
@RequiredArgsConstructor
public class ProductTypesController {
    private final CreateTypePort createTypePort;
    private final InitializeTypesPort initializeTypesPort;
    private final ReadTypesPort readTypesPort;

    @PostMapping
    public ResponseEntity<ProductType> createType(@RequestBody ProductType productType){
        return ResponseEntity.ok(createTypePort.createProductType(productType));
    }
    @PostMapping("/initialization")
    public void init(){
        initializeTypesPort.initialize();
    }

    @GetMapping
    public ResponseEntity<Set<ProductType>> createType(){
        return ResponseEntity.ok(readTypesPort.readAllTypes());
    }
}