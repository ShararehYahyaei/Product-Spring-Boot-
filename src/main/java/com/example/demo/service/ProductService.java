package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Product saveProduct(Product product) {
        if (isExisted(product.getName())) {
            throw new RuntimeException("This is an existing product");
        } else {
            return repo.save(product);
        }
    }

    public List<Product> saveProducts(List<Product> products) {
        return repo.saveAll(products);
    }

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product getProductByName(String name) {
        return repo.findByName(name);
    }

    public void deleteProduct(String name) {
        Product product = repo.findByName(name);
        if (product != null) {
            repo.delete(product);
        } else {
            throw new RuntimeException("This product does not exist");
        }
    }

    public Product updateProduct(Product product) {
        //todo first this product is existed with this id or not
        //todo if it is  I have to check this name is already existed or not
        //todo if is not duplicate this object can update it

        Optional<Product> newProduct = repo.findById(product.getId());
        if (newProduct.isPresent()) {
            if (isExisted(product.getName())) {
                newProduct.get().setPrice(product.getPrice());
                newProduct.get().setQuantity(product.getQuantity());
                return repo.save(newProduct.get());
            } else {
                newProduct.get().setName(product.getName());
                newProduct.get().setPrice(product.getPrice());
                newProduct.get().setQuantity(product.getQuantity());
                return repo.save(newProduct.get());
            }


        } else {
            throw new RuntimeException("This product does not exist");
        }
    }

    public boolean isExisted(String name) {
        return repo.existsByName(name);
    }
}
