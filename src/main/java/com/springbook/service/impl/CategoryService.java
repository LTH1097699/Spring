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

import com.springbook.dto.book.CategoryDTO;
import com.springbook.entity.book.CategoryEntity;
import com.springbook.repository.book.CategoryRepository;
import com.springbook.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Map<String, String> findAll() {
		Map<String, String> categoryMap = new HashMap<>();
		List<CategoryEntity> entities = categoryRepository.findAll();
		for(CategoryEntity entity: entities) {
			categoryMap.put(entity.getCodeCategory(), entity.getNameCategory());
		}	
		return categoryMap;
	}
	
	@Override
	public Map<Long, String> findAllId() {
		Map<Long, String> categoryMap = new HashMap<>();
		List<CategoryEntity> entities = categoryRepository.findAll();
		for(CategoryEntity entity: entities) {
			categoryMap.put(entity.getId(), entity.getNameCategory());
		}	
		return categoryMap;
	}
	
	
	@Override
	public List<CategoryDTO> findAllList() {
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		List<CategoryEntity> entities = categoryRepository.findAll();
		for(CategoryEntity entity: entities) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(entity.getId());
			categoryDTO.setCodeCategory(entity.getCodeCategory());
			categoryDTO.setNameCategory(entity.getNameCategory());
			categoryDTOs.add(categoryDTO);
		}	
		return categoryDTOs;
	}

	@Override
	public int getTotalItems() {
		return (int) categoryRepository.count();
	}

	@Override
	public List<CategoryDTO> findAll(Pageable pageable) {
		List<CategoryDTO> categoryDTOs = new ArrayList<>();	
		List<CategoryEntity> entities = categoryRepository.findAll(pageable).getContent();
		for(CategoryEntity entity: entities) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(entity.getId());
			categoryDTO.setCodeCategory(entity.getCodeCategory());
			categoryDTO.setNameCategory(entity.getNameCategory());		
			categoryDTOs.add(categoryDTO);
		}
		return categoryDTOs;
	}

	@Override
	@Transactional
	public CategoryDTO insert(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCodeCategory(categoryDTO.getCodeCategory());
		categoryEntity.setNameCategory(categoryDTO.getNameCategory());	
		categoryRepository.save(categoryEntity);
		return categoryDTO;
	}
	
	@Override
	@Transactional
	public CategoryDTO update(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity = categoryRepository.findOne(categoryDTO.getId());	
		categoryEntity.setCodeCategory(categoryDTO.getCodeCategory());
		categoryEntity.setNameCategory(categoryDTO.getNameCategory());	
		categoryRepository.save(categoryEntity);
		return categoryDTO;
	}

	@Override
	public CategoryDTO findById(Long id) {
		CategoryDTO categoryDTO = new CategoryDTO();
		CategoryEntity categoryEntity = categoryRepository.findOne(id);
		categoryDTO.setId(categoryEntity.getId());
		categoryDTO.setCodeCategory(categoryEntity.getCodeCategory());
		categoryDTO.setNameCategory(categoryEntity.getNameCategory());
		return categoryDTO;
	}

	@Override
	@Transactional
	public void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException {
		categoryRepository.delete(id);
	}

	@Override
	public List<CategoryDTO> search(String keyword,Pageable pageable){
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		List<CategoryEntity> entities = categoryRepository.search(keyword);
		
		if(pageable != null) {
			List<CategoryEntity> pageList = entities.stream().skip(pageable.getPageNumber()*pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<CategoryEntity> page = new PageImpl<>(pageList,pageable,entities.size());
			entities = page.getContent();
 		}
		for(CategoryEntity entity:entities) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(entity.getId());
			categoryDTO.setCodeCategory(entity.getCodeCategory());
			categoryDTO.setNameCategory(entity.getNameCategory());
			categoryDTOs.add(categoryDTO);		
		}
		return categoryDTOs;
	}
	@Override
	public int totalItemSearch(String keyword) {
		List<CategoryEntity> entities = categoryRepository.search(keyword);
		return (int) entities.size();
	}


	

}
