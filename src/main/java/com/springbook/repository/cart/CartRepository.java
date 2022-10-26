package com.springbook.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbook.entity.book.AuthorEntity;
import com.springbook.entity.cart.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
	CartEntity findOneByUserId(Long user_id);
	
	
}
