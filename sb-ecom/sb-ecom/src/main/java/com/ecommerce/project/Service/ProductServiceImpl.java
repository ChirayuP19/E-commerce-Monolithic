package com.ecommerce.project.Service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId,ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        Product product=modelMapper.map(productDTO,Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        double spicalPrice = product.getPrice()-
                ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(spicalPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        List<Product> findByCategoryOrderByPriceAsc = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = findByCategoryOrderByPriceAsc.stream()
                .map(x -> modelMapper.map(x, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> byProductNameLikeIgnoreCase = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOS = byProductNameLikeIgnoreCase.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID", productId));

        Optional.ofNullable(productDTO.getProductName())
                .ifPresent(product::setProductName);

        Optional.ofNullable(productDTO.getDescription())
                .ifPresent(product::setDescription);

        Optional.ofNullable(productDTO.getQuantity())
                .ifPresent(product::setQuantity);

        boolean priceChanged = false;
        boolean discountChanged = false;

        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
            priceChanged = true;
        }
        if (productDTO.getDiscount() != null) {
            product.setDiscount(productDTO.getDiscount());
            discountChanged = true;
        }
        if (priceChanged || discountChanged) {

            Double price = product.getPrice();
            Double discount = product.getDiscount();

            if (price != null && discount != null) {
                double specialPrice =
                        price - ((discount * 0.01) * price);

                product.setSpecialPrice(specialPrice);
            }
        }
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID", productId));
        productRepository.deleteById(productId);
        return modelMapper.map(product,ProductDTO.class);
    }


}
