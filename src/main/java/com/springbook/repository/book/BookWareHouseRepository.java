package com.springbook.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.entity.book.BookWarehouseEntity;
import com.springbook.entity.order.OrderDetailEntity;

public interface BookWareHouseRepository extends JpaRepository<BookWarehouseEntity, Long>{
	List<BookWarehouseEntity> findByBookId(Long bookid);
	BookWarehouseEntity findByBookIdAndWareHouseId(Long bookid, Long warehouseid);
	
	//find by warehouse id
	List<BookWarehouseEntity> findByWareHouseId(Long warehouseid);
	
	@Query(value = "SELECT book_warehouse.* FROM book_warehouse INNER JOIN book ON book_warehouse.bookid = book.id "
			+ "WHERE book_warehouse.warehouseid = ?1 AND (book.title LIKE %?2% OR book.bookcode LIKE %?2% )", nativeQuery = true)
	List<BookWarehouseEntity> findAllByBookKeyWordAndWareHouseId(Long warehouseid, String keyword);
	
	@Query(value = "SELECT book_warehouse.* FROM book_warehouse INNER JOIN book ON book_warehouse.bookid = book.id "
			+ "WHERE book.title LIKE %?1% OR book.bookcode LIKE %?1% ", nativeQuery = true)
	List<BookWarehouseEntity> findAllByBookKeyWord(String keyword);
	
	
}
