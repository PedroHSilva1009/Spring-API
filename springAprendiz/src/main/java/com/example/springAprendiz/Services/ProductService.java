package com.example.springAprendiz.Services;

import com.example.springAprendiz.models.ProductModel;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public ProductModel validateAmountAndStatus(ProductModel product){

        if(product.getProductStatus()==null){
            product.setProductStatus(1);
        }
        if(product.getAmount()==null){
            product.setAmount(0);
        }

        return product;
    }
}
