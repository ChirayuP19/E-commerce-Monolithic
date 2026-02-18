package com.ecommerce.project.controller;

import com.ecommerce.project.Service.ProductService;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;

import com.ecommerce.project.payload.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<?> addProduct(
            @RequestBody ProductDTO productDTO,
            @PathVariable Long categoryId){
        ProductDTO saveProductDTO =productService.addProduct(categoryId,productDTO);
        return new ResponseEntity<>(saveProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<?> getAllProduct(){
        ProductResponse productResponse = productService.getAllProduct();
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<?> getProductByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse=productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<?> getProductByKeyword(@PathVariable String keyword){
        ProductResponse productResponse=productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }

    @PatchMapping("/admin/products/{productId}")
    public ResponseEntity<?>updateProduct(@RequestBody ProductDTO productDTO,
                                          @PathVariable Long productId){
        ProductDTO updatedProductDTO=productService.updateProduct(productId,productDTO);
        return new ResponseEntity<>(updatedProductDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<?>updateAllProduct(@RequestBody ProductDTO productDTO,
                                          @PathVariable Long productId){
        ProductDTO updatedProductDTO=productService.updateProduct(productId,productDTO);
        return new ResponseEntity<>(updatedProductDTO,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long productId){
        ProductDTO deleteProduct=productService.deleteProduct(productId);
        return new ResponseEntity<>(deleteProduct,HttpStatus.OK);
    }
}
