package com.springbook.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.entity.book.AuthorEntity;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
	AuthorEntity findOneByCodeAuthor(String codeAuthor);
//	Page<AuthorEntity> findByNameAuthorContaining(String nameAuthor, Pageable pageable);
	boolean existsByCodeAuthor(String codeAuthor);
	
	@Query(value ="SELECT * FROM author a WHERE a.codeauthor LIKE %?1%"
            + " OR a.nameAuthor LIKE %?1%",nativeQuery = true)
	List<AuthorEntity> search(String keyword);
}
