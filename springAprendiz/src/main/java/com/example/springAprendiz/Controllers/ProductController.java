package com.example.springAprendiz.Controllers;
import com.example.springAprendiz.Dtos.ProductRecordDto;
import com.example.springAprendiz.Repositories.ProductRepository;
import com.example.springAprendiz.Services.ProductService;
import com.example.springAprendiz.models.ProductModel;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {
    @Autowired
            // ponto de injeção
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity <List<ProductModel>> getAllProducts(){
        List <ProductModel> productList = productRepository.findAll();

        if(!productList.isEmpty()){
            for (ProductModel product:
                 productList) {
                UUID id =  product.getProductId();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productFound = productRepository.findById(id);

        if (productFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");
        }

        productRepository.delete(productFound.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable(value = "id") UUID id,
                                                     @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> productFound = productRepository.findById(id);
        if(productFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exists");
        }

        var productModel = productFound.get();

        BeanUtils.copyProperties(productRecordDto, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> productFound = productRepository.findById(id);

        if(productFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productFound.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(productFound.get());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        // recebe o productDTO que é a requisição que vem em JSON
        // o requestBody é o body da requisição e o @valid serve pra confirmar a validação feita no mesmo

        var productModel = new ProductModel();
        var productService = new ProductService();
        // essa porra aqui de copiar propriedades simplesmente pega o que ta vindo na requisicção
        // e converte pra product model
        // o primeiro argumento é o que será convertido e o segundo é para o que sera convertido
        BeanUtils.copyProperties(productRecordDto, productModel);
        productModel = productService.validateAmountAndStatus(productModel);
        // aqui se ele foi criado de fato responde com um status CREATED pra ter cetreza que deu tudo certo
        // e no body enviamos o que foi salvo para o cliente


        return  ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
}
