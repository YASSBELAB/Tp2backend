package scholarproj.fst.repository;

import scholarproj.fst.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    // In-memory storage
    private final Map<Long, Product> products = new HashMap<>();

    // Automatic ID generator
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Save a product
    public Product save(Product product) {

        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
        }

        products.put(product.getId(), product);

        return product;
    }

    // Find a product by ID
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    // Find all products
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    // Delete a product by ID
    public void deleteById(Long id) {
        products.remove(id);
    }

    // Search products by name
    public List<Product> findByName(String name) {

        return products.values()
                .stream()
                .filter(product -> product.getName() != null &&
                        product.getName().toLowerCase()
                                .contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}