package com.springbook.conveter.user;

import org.springframework.stereotype.Component;

import com.springbook.dto.user.RoleDTO;
import com.springbook.entity.user.RoleEntity;
@Component
public class RoleConverter {
	private static RoleConverter INSTANCE;
	
	public static RoleConverter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleConverter();
        }
        return INSTANCE;
    }
	
	public RoleDTO toDTO(RoleEntity role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setCode(role.getCode());
        dto.setCodeName(role.getNameRole());
        return dto;
    }
	
	public RoleEntity toEntity(RoleEntity roleEntity, RoleDTO roleDTO) {
		roleEntity.setCode(roleDTO.getCode());
		roleEntity.setNameRole(roleDTO.getCodeName());
		return roleEntity;
	}
	
	
}
