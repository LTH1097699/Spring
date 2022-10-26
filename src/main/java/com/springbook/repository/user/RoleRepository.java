package com.springbook.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.entity.book.AuthorEntity;
import com.springbook.entity.user.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	RoleEntity findOneByCode(String code);
	boolean existsByCode(String code);
	
	@Query(value ="SELECT * FROM role a WHERE a.code LIKE %?1%"
            + " OR a.name LIKE %?1%",nativeQuery = true)
	List<RoleEntity> search(String keyword);
}
