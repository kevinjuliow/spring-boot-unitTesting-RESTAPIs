package com.example;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@NoArgsConstructor
public class ProductsController {
    @Autowired
    private ProductService productService ;
    @Autowired
    private ModelMapper modelMapper ;

    @PostMapping
    public ResponseEntity<?> add (@RequestBody @Valid Products products){
        Products addedProducts = productService.add(products);
        URI uri = URI.create("/api/v1/products/" + addedProducts.getId());
        return ResponseEntity.created(uri).body(addedProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById (@PathVariable Integer id) { return null;}

}
