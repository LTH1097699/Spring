package com.springbook.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.springbook.dto.user.RoleDTO;

public interface IRoleService {
	RoleDTO findRoleByCode(String code);
	Map<String, String> findAll();
	int getTotalItems();
	List<RoleDTO> findAll(Pageable pageable);
	RoleDTO findById(Long id);
}
