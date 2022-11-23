package com.springbook.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.entity.book.WareHouseEntity;

public interface WareHouseRepository extends JpaRepository<WareHouseEntity, Long> {
	
	@Query(value ="SELECT * FROM warehouse t WHERE t.code LIKE %?1%"
            + " OR t.name LIKE %?1%",nativeQuery = true)
	List<WareHouseEntity> search(String keyword);

	boolean existsByCode(String code);

	WareHouseEntity findOneByCode(String code);
	
	@Query(value ="SELECT warehouse.* FROM warehouse INNER JOIN book_warehouse ON warehouse.id=book_warehouse.warehouseid "
			+ "WHERE book_warehouse.bookid = ?1",nativeQuery = true)
	List<WareHouseEntity> getListWareHouseOfBook(Long bookid);
	
}
