package com.example;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@NoArgsConstructor
public class ProductsController {
    @Autowired
    private ProductService productService ;
    @Autowired
    private ModelMapper modelMapper ;

    protected ProductsController(ProductService service  , ModelMapper mapper){
        this.productService = service;
        this.modelMapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> add (@RequestBody @Valid Products products){
        Products addedProducts = productService.add(products);
        URI uri = URI.create("/api/v1/products/" + addedProducts.getId());
        return ResponseEntity.created(uri).body(entity2Dto(addedProducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById (@PathVariable Integer id) {
        try {
            Products existProduct = productService.get(id);
            return ResponseEntity.ok(entity2Dto(existProduct));
        }catch (UserNotFoundException err){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts (){
        List<Products> products = productService.getAllProducts();
        return products.isEmpty()? ResponseEntity.noContent().build() :  ResponseEntity.ok().body(products);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct (@RequestBody @Valid Products products , @PathVariable Integer id ){
        try{
            productService.update(products , id);
            return ResponseEntity.ok(entity2Dto(products));
        }
        catch (UserNotFoundException err){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable Integer id){
        try{
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (UserNotFoundException err){
            return ResponseEntity.notFound().build();
        }
    }

    private ProductDtos entity2Dto (Products entity) {
        return modelMapper.map(entity , ProductDtos.class);
    }
}
