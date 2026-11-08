
package scholarproj.fst.service;

import scholarproj.fst.dto.ProductRequest;
import scholarproj.fst.dto.ProductResponse;
import scholarproj.fst.exception.ResourceNotFoundException;
import scholarproj.fst.exception.InvalidQuantityException;
import scholarproj.fst.model.Product;
import scholarproj.fst.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    // Constructor injection
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // CREATE a product
    public ProductResponse createProduct(ProductRequest request) {

        validateQuantity(request.quantity());

        Product product = new Product();

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        Product saved = repository.save(product);

        return mapToResponse(saved);
    }

    // GET all products
    public List<ProductResponse> getAllProducts() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET a product by ID
    public ProductResponse getProductById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produit introuvable avec ID : " + id
                        ));

        return mapToResponse(product);
    }

    // UPDATE a product
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        validateQuantity(request.quantity());

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produit introuvable avec ID : " + id
                        ));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        Product updated = repository.save(product);

        return mapToResponse(updated);
    }

    // DELETE a product
    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produit introuvable avec ID : " + id
                        ));

        repository.deleteById(product.getId());
    }

    // SEARCH products by name
    public List<ProductResponse> searchProductsByName(String name) {

        return repository.findByName(name)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Validate product quantity
    private void validateQuantity(Integer quantity) {

        if (quantity != null && quantity > 1000) {
            throw new InvalidQuantityException(
                    "La quantité ne peut pas dépasser 1000"
            );
        }
    }

    // Convert Product model into ProductResponse DTO
    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
