package scholarproj.fst.controller;

import scholarproj.fst.dto.ProductRequest;
import scholarproj.fst.dto.ProductResponse;
import scholarproj.fst.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    // Constructor injection
    public ProductController(ProductService service) {
        this.service = service;
    }

    // CREATE - POST
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request) {

        ProductResponse response = service.createProduct(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL - GET
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    // SEARCH BY NAME - GET
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam String name) {

        return service.searchProductsByName(name);
    }

    // GET BY ID - GET (numbers only)
    @GetMapping("/{id:\\d+}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return service.updateProduct(id, request);
    }

    // DELETE - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        service.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}