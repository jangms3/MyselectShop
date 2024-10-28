package com.sparta.myselectshop.Repository;

import com.sparta.myselectshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
