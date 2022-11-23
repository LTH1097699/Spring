package com.springbook.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springbook.entity.book.TagEntity;
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
	TagEntity findOneByCodeTag(String codeTag);	
	boolean existsByCodeTag(String codeTag);
	@Query(value ="SELECT * FROM tag t WHERE t.codetag LIKE %?1%"
            + " OR t.nametag LIKE %?1%",nativeQuery = true)
	List<TagEntity> search(String keyword);
}
