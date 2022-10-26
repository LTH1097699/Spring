package com.springbook.conveter.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.user.RoleDTO;
import com.springbook.dto.user.ShipAddressDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.entity.book.TagEntity;
import com.springbook.entity.user.RoleEntity;
import com.springbook.entity.user.UserEntity;
import com.springbook.repository.user.RoleRepository;
@Component
public class UserConverter {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Autowired
	private RoleConverter roleConverter;
	
	@Autowired
	private ShipAddressConverter shipAddressConverter;
	
	public UserDTO toDTO(UserEntity entity) {	
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setUserName(entity.getEmail());
		dto.setFullName(entity.getFullName());
		dto.setShipAddress(shipAddressConverter.toDTOList(entity.getShipAddress()));
		dto.setRole(roleConverter.toDTO(entity.getRole()) );
		dto.setRoleCode(dto.getRole().getCode());
		dto.setFullNameValidator(entity.getFullName());
		return dto;
	}
	
	public UserEntity toEntity(UserDTO userDTO) {
		RoleEntity roleEntity = roleRepository.findOneByCode(userDTO.getRoleCode()); 
		UserEntity userEntity = new UserEntity();
		userEntity.setRole(roleEntity);
		userEntity.setEmail(userDTO.getUserName());
		userEntity.setFullName(userDTO.getFullName());
		return userEntity;
	}
	public UserEntity toEntity(UserEntity userEntity,UserDTO userDTO) {
		RoleEntity roleEntity = roleRepository.findOneByCode(userDTO.getRoleCode());
		if(StringUtils.hasText(userDTO.getPassword())) {
			userEntity.setPassword(encode.encode(userDTO.getPassword()));
		}
		
		userEntity.setFullName(userDTO.getFullName());
		userEntity.setRole(roleEntity);
		
		return userEntity;
	}

}
