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

import com.springbook.conveter.book.TagConverter;
import com.springbook.dto.book.TagDTO;
import com.springbook.entity.book.TagEntity;
import com.springbook.repository.book.TagRepository;
import com.springbook.service.ITagService;

@Service
public class TagService implements ITagService {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TagConverter tagConverter;

	@Override
	public Map<String, String> findAll() {
		Map<String, String> tagMap = new HashMap<>();

		List<TagEntity> entities = tagRepository.findAll();
		for (TagEntity entity : entities) {
			tagMap.put(entity.getCodeTag(), entity.getNametag());
		}
		return tagMap;
	}

	@Override
	public int getTotalItems() {
		return (int) tagRepository.count();
	}

	@Override
	public List<TagDTO> findAll(Pageable pageable) {
		List<TagDTO> tagDTOs = new ArrayList<>();

		List<TagEntity> entities = tagRepository.findAll(pageable).getContent();
		for (TagEntity entity : entities) {
			TagDTO tagDTO = tagConverter.toDTO(entity);
			tagDTOs.add(tagDTO);
		}
		return tagDTOs;
	}

	@Override
	@Transactional
	public TagDTO insert(TagDTO tagDTO) {
		TagEntity tagEntity = tagConverter.toEntity(tagDTO);
		tagEntity = tagRepository.save(tagEntity);

		return tagConverter.toDTO(tagEntity);
	}

	@Override
	@Transactional
	public TagDTO update(TagDTO tagDTO) {
		TagEntity oldTagEntity = tagRepository.findOne(tagDTO.getId());
		TagEntity tagEntity = tagConverter.toEntity(oldTagEntity, tagDTO);
		tagEntity = tagRepository.save(tagEntity);
		return tagConverter.toDTO(tagEntity);
	}

	@Override
	@Transactional
	public void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException {
		tagRepository.delete(id);
	}

	@Override
	public List<TagDTO> search(String keyword,Pageable pageable){
		List<TagDTO> tagDTOs = new ArrayList<>();
		List<TagEntity> entities = tagRepository.search(keyword);
		if(pageable!= null) {
			List<TagEntity> pageList=entities.stream().skip(pageable.getPageNumber()*pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<TagEntity> page = new PageImpl<>(pageList,pageable,entities.size());
			entities = page.getContent();
		}
		for(TagEntity entity:entities) {
			TagDTO tagDTO = tagConverter.toDTO(entity);
			tagDTOs.add(tagDTO);		
		}
		return tagDTOs;
	}
	@Override
	public int totalItemSearch(String keyword) {
		List<TagEntity> entities = tagRepository.search(keyword);
		return entities.size();
	}

	@Override
	public TagDTO findById(Long id) {
		TagEntity tagEntity = tagRepository.findOne(id);
		TagDTO tagDTO = tagConverter.toDTO(tagEntity);
		return tagDTO;
	}

}
