package com.superxc.jxshop.controller;

import com.superxc.jxshop.entity.Product;
import com.superxc.jxshop.repository.ProductRepository;
import com.superxc.jxshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Value("${app.host}")
    private String host;

    @Value("${app.port}")
    private String port;

    /**
     * list products
     *
     * @return all products
     */
    @GetMapping()
    public List<Product> list(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String description) {
        // list all products
        if (name == null) {
            return productRepository.findAll();
        } else if (description == null) {
            return productRepository.findByName(name);
        } else {
            name = addPreAndPostPercent(name);
            description = addPreAndPostPercent(description);
            return productRepository.findByNameLikeAndAndDescriptionLike(name, description);
        }
    }

    private String addPreAndPostPercent(String string) {
        return "%" + string + "%";
    }

    /**
     * add a product
     *
     * @param product
     * @return
     */
    @PostMapping()
    public ResponseEntity<String> add(@RequestBody Product product) {
        Product savedProduct = productService.add(product);
        HttpHeaders headers = new HttpHeaders();

        headers.add("location", String.format("http://%s:%s/products/%d", host, port, savedProduct.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * get a product
     *
     * @param id product id
     * @return product
     */
    @GetMapping(value = "/{id}")
    public Product getById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.isPresent() ? optionalProduct.get() : null;
    }

    /**
     * update a product
     *
     * @param id product id
     * @param newProduct
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Product newProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            newProduct.setId(product.getId());
            productRepository.save(newProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
