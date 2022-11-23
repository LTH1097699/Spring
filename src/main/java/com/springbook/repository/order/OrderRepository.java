package com.springbook.repository.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.entity.book.BookWarehouseEntity;
import com.springbook.entity.order.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

//	Page<OrderEntity> findByUsernameContaining(String username, Pageable pageable);
	
	List<OrderEntity> findByEmail(String email);
	
	List<OrderEntity> findAllByEmail(String email);
	
	@Query(value ="SELECT * FROM orderr a WHERE a.email LIKE %?1% ORDER BY a.createby DESC LIMIT 4",nativeQuery = true)
	List<OrderEntity> findByCreateByDESCLimit3(String email);
	
	
	@Query(value ="SELECT * FROM orderr o WHERE o.createdate BETWEEN ?1 AND ?2 ORDER BY o.createdate DESC ",nativeQuery = true)
	List<OrderEntity> findByCreateDateBetween(String start, String end);	

	@Query(value ="SELECT * FROM orderr o WHERE o.status=?1 AND o.createdate BETWEEN ?2 AND ?3 ORDER BY o.createdate DESC ",nativeQuery = true)
	List<OrderEntity> findByStatusAndCreateDateBetween(int status,String start, String end);
	
	
	List<OrderEntity> findByStatusOrderByCreateDateDesc(int status);
}
