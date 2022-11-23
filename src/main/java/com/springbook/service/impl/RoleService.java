package com.springbook.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springbook.conveter.user.RoleConverter;
import com.springbook.dto.user.RoleDTO;
import com.springbook.entity.user.RoleEntity;
import com.springbook.repository.user.RoleRepository;
import com.springbook.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleConverter roleConverter;



	@Override
	public Map<String, String> findAll() {
		Map<String, String> roleMap = new HashMap<>();

		List<RoleEntity> entities = roleRepository.findAll();
		for (RoleEntity entity : entities) {
			roleMap.put(entity.getCode(), entity.getNameRole());
		}

		return roleMap;
	}

	public RoleDTO findRoleByCode(String code) {
		RoleDTO roleDTO = new RoleDTO();
		RoleEntity roleEntity = roleRepository.findOneByCode(code);
		roleDTO.setCode(roleEntity.getCode());
		roleDTO.setCodeName(roleEntity.getNameRole());

		return roleDTO;
	}

	@Override
	public int getTotalItems() {
		return (int) roleRepository.count();
	}

	@Override
	public List<RoleDTO> findAll(Pageable pageable) {
		List<RoleDTO> roleDTOs = new ArrayList<>();

		List<RoleEntity> entities = roleRepository.findAll(pageable).getContent();
		for (RoleEntity entity : entities) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(entity.getId());
			roleDTO.setCode(entity.getCode());
			roleDTO.setCodeName(entity.getNameRole());
			roleDTOs.add(roleDTO);
		}
		return roleDTOs;
	}

//	@Override
//	public RoleDTO insert(RoleDTO roleDTO) {
//		RoleEntity roleEntity = new RoleEntity();
//		roleEntity.setCode(roleDTO.getCode());
//		roleEntity.setNameRole(roleDTO.getCodeName());
//
//		return roleConverter.toDTO(roleRepository.save(roleEntity));
//	}


	public void createAllPermission(Long roleId) {
//		RoleEntity roleEntity = roleRepository.findOne(roleId);
//		for (int i = 1; i <= 8; i++) {
//			RolePermissionEntity permissionEntity = new RolePermissionEntity();
//			permissionEntity.setRoles(roleEntity);
//			permissionEntity.setCodePermission(i);
//			permissionEntity.setStatus(0);
//			permissonRepository.save(permissionEntity);
//		}
	}

//	@Override
//	public RoleDTO update(RoleDTO roleDTO) {
//		RoleEntity oldRoleEntity = roleRepository.findOne(roleDTO.getId());
//		RoleEntity roleEntity = roleConverter.toEntity(oldRoleEntity, roleDTO);
//		roleRepository.save(roleEntity);
//
//		return roleConverter.toDTO(roleEntity);
//	}

	@Override
	public RoleDTO findById(Long id) {
		RoleEntity roleEntity = roleRepository.findOne(id);
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(roleEntity.getId());
		roleDTO.setCode(roleEntity.getCode());
		roleDTO.setCodeName(roleEntity.getNameRole());
		return roleDTO;
	}

//	public List<Integer> getAllPermission(Long roleId) {
////		List<Integer> idPermission = permissonRepository.findAllByRoleIdAndStatusActive(roleId);
////
////		return idPermission;
//		return null;
//	}

//	@Override
//	public void delete(Long id) throws SQLIntegrityConstraintViolationException, ConstraintViolationException {
//		roleRepository.delete(id);
//	}

	public void setPermission(Long[] ids, Long idRole) {
//		RoleEntity roleEntity = roleRepository.findOne(idRole);
//		int[] a = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
//		for (int i = 0; i < a.length; i++) {
//			for (int j = 0; j < ids.length; j++) {
//				if (ids[j] == a[i]) {
//					a[i] = a[i - 1];
//					break;
//				}
//			}
//		}
//		ArrayList<Integer> arrTemp = new ArrayList<>();
//		for (int i = 0; i < a.length; i++) {
//			if (!arrTemp.contains(a[i])) {
//				arrTemp.add(a[i]);
//			}
//		}
//		arrTemp.remove(0);
//		for (int aa = 0; aa < arrTemp.size(); aa++) {
//
//			System.out.print(arrTemp.get(aa) + "-");
//		}
//
//		for (Long id : ids) {
//
//			RolePermissionEntity permissionEntity = permissonRepository.findByRoleIdAndCodePermission(idRole, id);
//
//			permissionEntity.setStatus(1);
//			permissonRepository.save(permissionEntity);
//
//		}
//		for (Integer i : arrTemp) {
//			RolePermissionEntity permissionEntity = permissonRepository.findByRoleIdAndCodePermission(idRole,
//					Long.valueOf(i));
//			permissionEntity.setStatus(0);
//			permissonRepository.save(permissionEntity);
//		}

	}

//	@Override
//	public List<RoleDTO> search(String keyword) {
//		List<RoleDTO> roleDTOs = new ArrayList<>();
//		List<RoleEntity> entities = roleRepository.search(keyword);
//		for(RoleEntity entity: entities) {
//			RoleDTO roleDTO = roleConverter.toDTO(entity);
//			roleDTOs.add(roleDTO);
//		}
//		return roleDTOs;
//	}
	
	
//	public RoleDTO getRole(Long id) {
//		RoleDTO roleDTO = new RoleDTO();
//		RoleEntity entity = roleRepository.findOne(id);
//		roleDTO = roleConverter.toDTO(entity);
//		List<PermissionDTO> dtos = new ArrayList<>();
//		for(PermissionEntity entity2:entity.getPermission()) {
//			dtos.add(new PermissionDTO(entity2.getCode(), entity2.getUrl()));	
//		}
//		roleDTO.setListPerrr(dtos);
//		
//		
//		
//		
//		return roleDTO;
//	}
	
}
