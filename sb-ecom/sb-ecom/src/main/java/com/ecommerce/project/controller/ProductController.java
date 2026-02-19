package com.ecommerce.project.controller;

import com.ecommerce.project.Service.ProductService;
import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;

import com.ecommerce.project.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<?> addProduct(
            @Valid
            @RequestBody ProductDTO productDTO,
            @PathVariable Long categoryId){
        ProductDTO saveProductDTO =productService.addProduct(categoryId,productDTO);
        return new ResponseEntity<>(saveProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<?> getAllProduct(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false)String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false)String sortOrder){
        ProductResponse productResponse = productService.getAllProduct(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<?> getProductByCategory(@PathVariable Long categoryId,
              @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
             @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
             @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false)String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false)String sortOrder){
        ProductResponse productResponse=productService.searchByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<?> getProductByKeyword(@PathVariable String keyword,
             @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
             @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
             @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false)String sortBy,
             @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false)String sortOrder){
        ProductResponse productResponse=productService.searchProductByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }

    @PatchMapping("/admin/products/{productId}")
    public ResponseEntity<?>updateProduct(@Valid @RequestBody ProductDTO productDTO,
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

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<?> updateProductImage(@PathVariable Long productId,
                                                @RequestParam("image") MultipartFile image){
       ProductDTO updateProduct= productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }
}
