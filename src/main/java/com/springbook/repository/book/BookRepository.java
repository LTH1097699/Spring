package com.springbook.repository.book;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springbook.entity.book.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
	BookEntity findOneByBookCode(String bookCode);
	Page<BookEntity> findByTitleContaining(String title, Pageable pageable);
	BookEntity findOneById(Long id);
	
	@Query(value = "SELECT * FROM book ORDER BY createdate DESC LIMIT 12", nativeQuery = true)
	List<BookEntity> findAllOrderByCreateDateDescLimit12();
	
	@Query(
	value = "SELECT book.* FROM book INNER JOIN tag on book.tag_id=tag.id AND tag.codetag ='nb' ORDER BY book.modifieddate DESC LIMIT 6", 
	nativeQuery = true)
	List<BookEntity> findByCodeTagOrderByModifiedDateDescLimit6();
	
	boolean existsByBookCode(String bookCode);
	
	@Query(value ="SELECT * FROM book a WHERE a.status=1 AND a.bookcode LIKE %?1%"
            + " OR a.title LIKE %?1%"
            + " OR CONCAT(a.price,'') LIKE %?1%",nativeQuery = true)
	List<BookEntity> search(String keyword);
	List<BookEntity> findByCategory_idInAndStatus(List<Long> categoryKey,short status);
	List<BookEntity> findByAuthor_idInAndStatus(List<Long> authorKey,short status);
	List<BookEntity> findByPublisher_idInAndStatus(List<Long> publisherKey,short status);
	
	List<BookEntity> findByCategory_idInAndAuthor_idInAndPublisher_idInAndStatus(List<Long> categoryKey,List<Long> authorKey,List<Long> publisherKey,short status);
	
	List<BookEntity> findByCategory_idInAndAuthor_idInAndStatus(List<Long> categoryKey,List<Long> authorKey,short status);
	List<BookEntity> findByCategory_idInAndPublisher_idInAndStatus(List<Long> categoryKey,List<Long> publisherKey,short status);
	List<BookEntity> findByAuthor_idInAndPublisher_idInAndStatus(List<Long> authorKey,List<Long> publisherKey,short status);
	
	Page<BookEntity> findAllByStatus(short status, Pageable pageable);
}
