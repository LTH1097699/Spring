package com.springbook.conveter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springbook.dto.user.ShipAddressDTO;
import com.springbook.entity.user.ShipAddressEntity;

@Component
public class ShipAddressConverter {
	
	public ShipAddressDTO toDTO(ShipAddressEntity entity) {
		ShipAddressDTO dto = new ShipAddressDTO();
		dto.setId(entity.getId());
		dto.setProvince(entity.getProvince());
		dto.setDistrict(entity.getDistrict());
		dto.setWard(entity.getWard());
		dto.setAddress(entity.getAddress());
		dto.setNumber(entity.getNumber());
		dto.setStr(dto.getAddress() + ", "+dto.getWard()+", "+dto.getDistrict()+", "+dto.getProvince());
		
		
		return dto;
	}
	
	public List<ShipAddressDTO> toDTOList(List<ShipAddressEntity> entities){
		List<ShipAddressDTO> dtos = new ArrayList<>();
		for(ShipAddressEntity entity:entities) {
			ShipAddressDTO dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
	
	
	public ShipAddressEntity toEntity(ShipAddressDTO dto){
		ShipAddressEntity entity = new ShipAddressEntity();
		entity.setAddress(dto.getAddress());
		entity.setDistrict(dto.getDistrict());
		entity.setProvince(dto.getProvince());
		entity.setNumber(dto.getNumber());
		entity.setWard(dto.getWard());
		return entity;
	}
	
//	public List<ShipAddressEntity> toEntityList(List<ShipAddressDTO> dtos){
//		for() {
//	}
	
	public ShipAddressEntity toEntity(ShipAddressEntity entity,ShipAddressDTO dto){
		entity.setAddress(dto.getAddress());
		entity.setDistrict(dto.getDistrict());
		entity.setProvince(dto.getProvince());
		entity.setNumber(dto.getNumber());
		entity.setWard(dto.getWard());
		return entity;
	}
}
