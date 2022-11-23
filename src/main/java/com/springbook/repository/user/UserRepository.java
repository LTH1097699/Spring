package com.springbook.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbook.dto.user.UserDTO;
import com.springbook.entity.user.UserEntity;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findOneByEmailAndStatus(String email, int status);
	boolean existsByEmail(String email);
	Page<UserEntity> findByFullNameContainingAndStatus(String name, Pageable pageable,int status);
	UserEntity findOneByEmail(String email);
	
	List<UserEntity> findByStatus(int status);
	
}
