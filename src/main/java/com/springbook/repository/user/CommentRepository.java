package com.springbook.repository.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	List<CommentEntity> findByBookId(Long bookid, Pageable pageable);
	List<CommentEntity> findByBookId(Long bookid);
}
