package com.billing.app.domain.types.application;

import com.billing.app.domain.types.application.model.ProductType;
import com.billing.app.domain.types.core.ports.incoming.CreateTypeService;
import com.billing.app.domain.types.core.ports.incoming.ReadTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/types")
@RequiredArgsConstructor
public class ProductTypesController {



    private final CreateTypeService createTypeService;
    private final ReadTypesService readTypesService;

    @PostMapping()
    public ResponseEntity<ProductType> createType(@RequestBody ProductType productType){
        return ResponseEntity.ok(productType);
    }

    @GetMapping()
    public ResponseEntity<Set<ProductType>> createType(){
        return ResponseEntity.ok(Set.of(new ProductType("test", "test")));
    }
}