package com.springbook.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.entity.user.ContactEntity;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
	
	@Query(value ="SELECT * FROM contact a WHERE a.nameuser LIKE %?1%"
			+ " OR a.title LIKE %?1%"
            + " OR a.email LIKE %?1%",nativeQuery = true)
	List<ContactEntity> search(String keyword);
}
