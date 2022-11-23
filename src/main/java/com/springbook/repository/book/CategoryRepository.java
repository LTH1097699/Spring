package com.springbook.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springbook.entity.book.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	CategoryEntity findOneByCodeCategory(String codeCategory);
	boolean existsByCodeCategory(String codeCategory);
	@Query(value ="SELECT * FROM category a WHERE a.code_category LIKE %?1%"
            + " OR a.name_category LIKE %?1%",nativeQuery = true)
	List<CategoryEntity> search(String keyword);
}
