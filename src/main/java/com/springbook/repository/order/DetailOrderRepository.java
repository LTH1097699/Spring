package com.springbook.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.dto.order.OrderDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.order.OrderDetailEntity;

public interface DetailOrderRepository extends JpaRepository<OrderDetailEntity, Long> {

	List<OrderDetailEntity> findAllByOrderId(Long orderid);

	OrderDetailEntity findByOrderIdAndBookId(Long orderid, Long bookid);

	List<OrderDetailEntity> findAllByBookId(Long bookid);

	@Query(value = "SELECT order_detail.* FROM order_detail INNER JOIN book ON order_detail.book_id = book.id "
			+ "INNER JOIN orderr ON order_detail.orderid = orderr.id "
			+ "WHERE orderr.status = 5 AND order_detail.idwarehouse = ?1 AND (book.title LIKE %?2% OR book.bookcode LIKE %?2% OR orderr.id LIKE %?2%)", nativeQuery = true)
	List<OrderDetailEntity> searchByOrderIdOrBookCodeOrBookTitleAndWareHouseId(Long idWareHouse, String keyword);

	@Query(value = "SELECT order_detail.* FROM order_detail INNER JOIN book ON order_detail.book_id = book.id "
			+ "INNER JOIN orderr ON order_detail.orderid = orderr.id "
			+ "WHERE  orderr.status=5 AND (book.title LIKE %?1% OR book.bookcode LIKE %?1% OR orderr.id LIKE %?1%)", nativeQuery = true)
	List<OrderDetailEntity> searchByOrderIdOrBookCodeOrBookTitle(String keyword);
	
	@Query(value = "SELECT * FROM order_detail INNER JOIN orderr ON order_detail.orderid = orderr.id "
			+"WHERE orderr.status=5 AND order_detail.idwarehouse = ?1 ",nativeQuery = true)
	List<OrderDetailEntity> searchByIdWareHouse(Long idWareHouse);
	
	@Query(value = "SELECT * FROM order_detail INNER JOIN orderr ON order_detail.orderid = orderr.id "
			+"WHERE orderr.status=5",nativeQuery = true)
	List<OrderDetailEntity> searchAll();

}
