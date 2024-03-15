package com.ceramicsheaven.services;

import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.model.Product;
import com.ceramicsheaven.repositories.CategoryRepository;
import com.ceramicsheaven.repositories.ProductRepository;
import com.ceramicsheaven.requests.ProductRequest;
import com.ceramicsheaven.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(ProductRequest req) throws ProductException {
        Category category = categoryRepository.findByCategoryName(req.getCategoryName());
        Product product = new Product();
        Category newCategory;
        if (category==null){
            newCategory = new Category();
            newCategory.setCategoryName(req.getCategoryName());
            newCategory.setCreatedAt(LocalDateTime.now());
            newCategory.setCategoryDescription("new Category "+req.getCategoryName());
            newCategory.setUpdatedAt(LocalDateTime.now());
            product.setCategory(categoryRepository.save(newCategory));
        }else {
                    product.setCategory(category);
        }


        product.setTitle(req.getTitle());
        product.setDiscription(req.getDescription());
        product.setBrand(req.getBrand());
        product.setImageUrl(req.getImageUrl());
        product.setColor(req.getColor());
        product.setDiscountedPrice(req.getDiscountPrice());
        product.setDiscountedPercent(req.getDiscountPercent());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSizes());
        product.setQuantity(req.getQtyPerBox());
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product foundProduct;
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new ProductException("Product is not available");
        }
        foundProduct = product.get();
        foundProduct.getSizes().clear();
        productRepository.delete(foundProduct);

        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct==null)
            throw new ProductException("Product is not available for id "+productId);

        Category category = categoryRepository.findByCategoryName(product.getCategory().getCategoryName());

        if (category==null){
            throw new ProductException(product.getCategory().getCategoryName()+" category is not available");
        }

        if (product.getQuantity()<0){
            throw new ProductException("Quantity can not be negative");
        }
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDiscription(product.getDiscription());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setColor(product.getColor());
        existingProduct.setDiscountedPrice(product.getDiscountedPrice());
        existingProduct.setDiscountedPercent(product.getDiscountedPercent());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSizes(product.getSizes());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setQuantity(product.getQuantity());
        return productRepository.save(existingProduct);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent())
            return product.get();

        throw new ProductException("Product not found with id "+productId);
    }

    @Override
    public List<Product> findProductByCategory(Long categoryId) throws ProductException {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) throws ProductException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> productList = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if (!colors.isEmpty()) {
            productList = productList.stream()
                    .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if (!sizes.isEmpty()) {
            productList = productList.stream()
                    .filter(p -> p.getSizes().stream().anyMatch(s -> {
                        String[] dimensions = s.toString().split(",");
                        String sizeStr = dimensions[0].substring(dimensions[0].indexOf('=') + 1).trim() + "x" +
                                dimensions[1].substring(dimensions[1].indexOf('=') + 1).trim();
                        return sizes.contains(sizeStr);
                    }))
                    .collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_stock")) {
                productList = productList.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                productList = productList.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), productList.size());

        List<Product> pageContent = productList.subList(startIndex, endIndex);

        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, productList.size());

        return filteredProducts;
    }



    @Override
    public List<Product> findAllProducts() throws ProductException{
        return productRepository.findAll();
    }
}
