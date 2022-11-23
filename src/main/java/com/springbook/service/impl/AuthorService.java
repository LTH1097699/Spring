package com.springbook.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.springbook.service.IAuthorService;
@Service
public class AuthorService implements IAuthorService{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private AuthorConverter authorConverter;
	
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
	public int getTotalItems() {	
		return (int) authorRepository.count();
	}

	@Override
	public List<AuthorDTO> findAll(Pageable pageable) {
		List<AuthorDTO> authorDTOs = new ArrayList<>();
		
		List<AuthorEntity> entities = authorRepository.findAll(pageable).getContent();
		for(AuthorEntity entity: entities) {
			AuthorDTO authorDTO = authorConverter.toDTO(entity);
			authorDTOs.add(authorDTO);
		}
		return authorDTOs;
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
	public List<AuthorDTO> search(String keyword,Pageable pageable){
		List<AuthorDTO> authorDTOs = new ArrayList<>();
		List<AuthorEntity> entities = authorRepository.search(keyword);
		
		if(pageable != null) {
			List<AuthorEntity> pageList = entities.stream().skip(pageable.getPageSize()*pageable.getPageNumber()).
					limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<AuthorEntity> page = new PageImpl<>(pageList,pageable,entities.size());
			entities = page.getContent();
		}
		
		for(AuthorEntity entity:entities) {
			AuthorDTO authorDTO = authorConverter.toDTO(entity);
			authorDTOs.add(authorDTO);		
		}
		return authorDTOs;
	}
	
	@Override
	public int totalItemSearch(String keyword){
		List<AuthorEntity> entities = authorRepository.search(keyword);
		return (int) entities.size();
	
	}


}
