package com.springbook.repository.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springbook.entity.cart.DetailCartEntity;

public interface DetailCartRepository extends JpaRepository<DetailCartEntity, Long> {
	DetailCartEntity findOneByCartIdAndBookId(Long cartId, Long bookId);
	List<DetailCartEntity> findByCartId(Long cartId);
	
	@Modifying
	@Query(value = "DELETE FROM detailcart d WHERE d.bookid = :bookid AND d.cartid = :cartid", nativeQuery = true)
	void deleteByCartIdAndBookId(@Param("cartid") Long cartId,@Param("bookid") Long bookId);
}
