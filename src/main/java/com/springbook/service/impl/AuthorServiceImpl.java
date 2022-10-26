package com.springbook.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.springbook.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbook.conveter.book.AuthorConverter;
import com.springbook.dto.book.AuthorDTO;
import com.springbook.entity.book.AuthorEntity;
import com.springbook.repository.book.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
	
	private final AuthorRepository authorRepository;
	private final AuthorConverter authorConverter;
	
	@Override
	public Map<String, String> findAll() {
		Map<String, String> authorMap = new HashMap<>();
		
		List<AuthorEntity> entities = authorRepository.findAll();
		for(AuthorEntity entity: entities) {
			authorMap.put(entity.getCodeAuthor(), entity.getNameAuthor());
		}
		return authorMap;
	}

	@Override
	public Map<Long, String> findAllId() {
		Map<Long, String> authorMap = new HashMap<>();
		
		List<AuthorEntity> entities = authorRepository.findAll();
		for(AuthorEntity entity: entities) {
			authorMap.put(entity.getId(), entity.getNameAuthor());
		}
		return authorMap;
	}

	@Override
	public int countTotalItems() {
		return (int) authorRepository.count();
	}

	@Override
	public Page<AuthorEntity> findAll(final Pageable pageable) {
		return authorRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public AuthorDTO insert(AuthorDTO authorDTO){
		AuthorEntity authorEntity = authorConverter.toEntity(authorDTO);
		authorRepository.save(authorEntity);	
		return authorConverter.toDTO(authorEntity);
	}

	@Override
	@Transactional
	public AuthorDTO update(AuthorDTO authorDTO) {
		AuthorEntity oldAuthorEntity = authorRepository.findOne(authorDTO.getId());
		AuthorEntity authorEntity = authorConverter.toEntity(oldAuthorEntity, authorDTO);
		authorEntity = authorRepository.save(authorEntity);
		return authorConverter.toDTO(authorEntity);
	}

	@Override
	public AuthorDTO findById(Long id) {
		AuthorEntity authorEntity = authorRepository.findOne(id);
		return authorConverter.toDTO(authorEntity);
	}

	@Override
	public void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException {
		authorRepository.delete(id);
	}
	
	@Override
	public List<AuthorDTO> search(final String keyword, final Pageable pageable){
		List<AuthorDTO> authorDTOs = new ArrayList<>();
		List<AuthorEntity> entities = authorRepository.search(keyword);
		
		if(pageable != null) {
			List<AuthorEntity> pageList = entities.stream().skip((long) pageable.getPageSize() *pageable.getPageNumber()).
					limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<AuthorEntity> page = new PageImpl<>(pageList,pageable,entities.size());
			entities = page.getContent();
		}
		
		for(AuthorEntity entity:entities) {
			AuthorDTO authorDTO = AuthorConverter.toDTO(entity);
			authorDTOs.add(authorDTO);		
		}
		return authorDTOs;
	}
	
	@Override
	public int totalItemSearch(final String keyword){
		return authorRepository.search(keyword).size();
	}

}
