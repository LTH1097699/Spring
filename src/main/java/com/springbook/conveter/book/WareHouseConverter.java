package com.springbook.conveter.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.springbook.dto.book.WareHouseDTO;
import com.springbook.entity.book.WareHouseEntity;
@Component
public class WareHouseConverter {
	
	public WareHouseDTO toDTO(WareHouseEntity entity) {
		WareHouseDTO dto = new WareHouseDTO();	
		dto.setId(entity.getId());
		dto.setCode(entity.getCode());
		dto.setName(entity.getName());
		dto.setAddress(entity.getAddress());
		return dto;
	}
	
	public List<WareHouseDTO> toDTOList(List<WareHouseEntity> entities){
		List<WareHouseDTO> dtos = new ArrayList<>();
		for(WareHouseEntity entity:entities) {
			WareHouseDTO dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
	
	public WareHouseEntity toEntity(WareHouseDTO dto) {
		WareHouseEntity entity = new WareHouseEntity();
		entity.setCode(dto.getCode());
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		return entity;
	}
	
	public WareHouseEntity toEntity(WareHouseEntity entity, WareHouseDTO dto) {	
		entity.setCode(dto.getCode());
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		return entity;
	}
	
	public Map<String, String> toMap(List<WareHouseEntity> entities){
		Map<String , String> map = new HashMap<>();
		for(WareHouseEntity entity:entities) {
			map.put(entity.getCode(), entity.getName());
		}
		return map;
	}
}
