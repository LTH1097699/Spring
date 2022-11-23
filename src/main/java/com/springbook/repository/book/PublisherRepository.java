package com.springbook.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springbook.entity.book.PublisherEntity;
@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Long> {
	PublisherEntity findOneByCodePublisher(String codePublisher);
	boolean existsByCodePublisher(String codePublisher);
	
	@Query(value ="SELECT * FROM publisher a WHERE a.code_publisher LIKE %?1%"
            + " OR a.name_publisher LIKE %?1%",nativeQuery = true)
	List<PublisherEntity> search(String keyword);
}
