package com.billing.app.taxes.application;

import com.billing.app.taxes.application.model.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/types")
@RequiredArgsConstructor
public class ProductTypesController {


    @PostMapping()
    public ResponseEntity<ProductType> createType(@RequestBody ProductType productType){
        return ResponseEntity.ok(productType);
    }
}