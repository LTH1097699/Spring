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

import com.springbook.conveter.book.PublisherConverter;
import com.springbook.dto.book.PublisherDTO;
import com.springbook.entity.book.PublisherEntity;
import com.springbook.repository.book.PublisherRepository;
import com.springbook.service.IPublisherService;

@Service
public class PublisherService implements IPublisherService {
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private PublisherConverter publisherConverter;

	@Override
	public Map<String, String> findAll() {
		Map<String, String> publisherMap = new HashMap<>();	
		List<PublisherEntity> entities = publisherRepository.findAll();
		for(PublisherEntity entity: entities) {
			publisherMap.put(entity.getCodePublisher(), entity.getNamePublisher());
		}
		return publisherMap;
	}
	
	@Override
	public Map<Long, String> findAllId() {
		Map<Long, String> publisherMap = new HashMap<>();	
		List<PublisherEntity> entities = publisherRepository.findAll();
		for(PublisherEntity entity: entities) {
			publisherMap.put(entity.getId(), entity.getNamePublisher());
		}
		return publisherMap;
	}

	@Override
	public int getTotalItems() {	
		return (int) publisherRepository.count();
	}

	@Override
	public List<PublisherDTO> findAll(Pageable pageable) {
		List<PublisherDTO> publisherDTOs = new ArrayList<>();	
		List<PublisherEntity> entities = publisherRepository.findAll(pageable).getContent();
		for(PublisherEntity entity: entities) {
			PublisherDTO publisherDTO = publisherConverter.toDTO(entity);
			publisherDTOs.add(publisherDTO);
		}
		return publisherDTOs;
	}

	@Override
	@Transactional
	public PublisherDTO insert(PublisherDTO publisherDTO) {
		PublisherEntity publisherEntity = publisherConverter.toEntity(publisherDTO);
		publisherEntity = publisherRepository.save(publisherEntity);	
		return publisherConverter.toDTO(publisherEntity);
	}

	@Override
	@Transactional
	public PublisherDTO update(PublisherDTO publisherDTO) {
		PublisherEntity oldPublisherEntity = publisherRepository.findOne(publisherDTO.getId());
		PublisherEntity publisherEntity = publisherConverter.toEntity(oldPublisherEntity, publisherDTO);
		publisherEntity = publisherRepository.save(publisherEntity);
		return publisherConverter.toDTO(publisherEntity);
	}
	@Override
	public PublisherDTO findById(Long id) {	
		PublisherEntity publisherEntity = publisherRepository.findOne(id);
		PublisherDTO publisherDTO = publisherConverter.toDTO(publisherEntity);
		return publisherDTO;
	}

	@Override
	@Transactional
	public void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException {
		publisherRepository.delete(id);	
	}

	@Override
	public List<PublisherDTO> search(String keyword,Pageable pageable){
		List<PublisherDTO> publisherDTOs = new ArrayList<>();
		List<PublisherEntity> entities = publisherRepository.search(keyword);
		
		if(pageable != null) {
			List<PublisherEntity> pageList=entities.stream().skip(pageable.getPageNumber()*pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<PublisherEntity> page = new PageImpl<>(pageList,pageable,entities.size());
			entities = page.getContent();
		}
		for(PublisherEntity entity:entities) {
			PublisherDTO publisherDTO = publisherConverter.toDTO(entity);
			publisherDTOs.add(publisherDTO);		
		}
		return publisherDTOs;
	}
	@Override
	public int totalitemSearch(String keyword) {
		List<PublisherEntity> entities = publisherRepository.search(keyword);
		return entities.size();
	}
}
