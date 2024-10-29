package com.sparta.myselectshop.service;

import com.sparta.myselectshop.Repository.ProductRepository;
import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.naver.dto.ItemDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public static final int MIN_MY_PRICE = 100;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productRepository.save(new Product(requestDto));
        return new ProductResponseDto(product);
    }

    @Transactional
    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice(); //100원 이상이여야 함.
        if (myprice < MIN_MY_PRICE){
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다.최소" + MIN_MY_PRICE+ "원 이상으로 설정해주세요");
        }
        Product product = productRepository.findById(id).orElseThrow(()->
                new NullPointerException("해당 상품을 찾을 수 없습니다")
        );

        product.update(requestDto);

        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> responseDtoList = new ArrayList<>();

        for (Product product : products) { //향상된 for 문
            responseDtoList.add(new ProductResponseDto(product)); //productResponseDto에 있는 product 생성자
        }

        return responseDtoList;

    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product =productRepository.findById(id).orElseThrow(()->
                new NullPointerException("해당 상품은 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
    }
}
