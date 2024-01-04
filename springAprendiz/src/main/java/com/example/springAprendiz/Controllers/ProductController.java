package com.example.springAprendiz.Controllers;
import com.example.springAprendiz.Dtos.ProductRecordDto;
import com.example.springAprendiz.Repositories.ProductRepository;
import com.example.springAprendiz.models.ProductModel;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
            // ponto de injeção
    ProductRepository productRepository;

    @GetMapping("/listProducts")
    public ResponseEntity <List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        // recebe o productDTO que é a requisição que vem em JSON
        // o requestBody é o body da requisição e o @valid serve pra confirmar a validação feita no mesmo

        var productModel = new ProductModel();
        // essa porra aqui de copiar propriedades simplesmente pega o que ta vindo na requisicção
        // e converte pra product model
        // o primeiro argumento é o que será convertido e o segundo é para o que sera convertido
        BeanUtils.copyProperties(productRecordDto, productModel);

        // aqui se ele foi criado de fato responde com um status CREATED pra ter cetreza que deu tudo certo
        // e no body enviamos o que foi salvo para o cliente
        return  ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
}
