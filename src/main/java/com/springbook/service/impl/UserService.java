package com.springbook.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.springbook.conveter.order.OrderConverter;
import com.springbook.conveter.user.ShipAddressConverter;
import com.springbook.conveter.user.UserConverter;
import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.user.ShipAddressDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.entity.user.RoleEntity;
import com.springbook.entity.user.ShipAddressEntity;
import com.springbook.entity.user.UserEntity;
import com.springbook.repository.order.OrderRepository;
import com.springbook.repository.user.RoleRepository;
import com.springbook.repository.user.ShipAddressRepository;
import com.springbook.repository.user.UserRepository;
import com.springbook.service.IUserService;

@Service
public class UserService implements IUserService {

	public static final int ACTIVE_STATUS = 1;
	public static final int INACTIVE_STATUS = 0;

	@Autowired
	private ShipAddressConverter shipAddressConverter;
	@Autowired
	private ShipAddressRepository shipAddressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderConverter orderConverter;

	@Override
	public UserDTO insert(UserDTO userDTO) {
		RoleEntity roleEntity = roleRepository.findOneByCode("USER");
		UserEntity newUserEntity = new UserEntity();
		newUserEntity.setFullName(userDTO.getFullName());
		newUserEntity.setEmail(userDTO.getUserName());
		newUserEntity.setPassword(encode.encode(userDTO.getPassword()));

		newUserEntity.setStatus(ACTIVE_STATUS);
		newUserEntity.setRole(roleEntity);
		userRepository.save(newUserEntity);

		return userDTO;
	}

	@Override
	public UserDTO adminInsert(UserDTO userDTO) {
		RoleEntity roleEntity = roleRepository.findOneByCode(userDTO.getRoleCode());
		UserEntity newUserEntity = new UserEntity();
		newUserEntity.setFullName(userDTO.getFullName());
		newUserEntity.setEmail(userDTO.getUserName());
		newUserEntity.setPassword(encode.encode(userDTO.getPassword()));
		newUserEntity.setStatus(ACTIVE_STATUS);
		newUserEntity.setRole(roleEntity);
		userRepository.save(newUserEntity);
		return userDTO;
	}

	@Override
	@Transactional
	public UserDTO update(UserDTO userDTO) {
		UserEntity olduserEntity = userRepository.findOne(userDTO.getId());
		UserEntity userEntity = userConverter.toEntity(olduserEntity, userDTO);
		userEntity = userRepository.save(userEntity);
		return userConverter.toDTO(userEntity);
	}

	@Override
	public int existedUser(String username) {
		if (userRepository.existsByEmail(username) == true) {
			return 1;
		}
		return 0;
	}

	@Override
	public UserDTO findOneById(Long id) {
		UserEntity userEntity = userRepository.findOne(id);
		UserDTO userDTO = userConverter.toDTO(userEntity);
		List<OrderDTO> orderDTO = orderConverter.toDTOList(orderRepository.findAllByEmail(userDTO.getUserName()));
		userDTO.setOrders(orderDTO);
		return userDTO;
	}

	@Override
	public List<UserDTO> findAll(Pageable pageable) {
		List<UserDTO> userDTOs = new ArrayList<>();
		List<UserEntity> userEntities = userRepository.findByStatus(1);
		List<UserEntity> pagelist = userEntities.stream().skip(pageable.getPageNumber()*pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
		
		Page<UserEntity> page = new PageImpl<>(pagelist,pageable,userEntities.size());
		for (UserEntity entity : page.getContent()) {
			UserDTO userDTO = userConverter.toDTO(entity);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	@Override
	public Integer getTotalItems() {
		List<UserEntity> userEntities = userRepository.findByStatus(1);
		return userEntities.size();
	}

	@Override
	public List<UserDTO> findByNameTagContaining(String name, Pageable pageable) {
		List<UserDTO> userDTOs = new ArrayList<>();

		Page<UserEntity> entitiess = userRepository.findByFullNameContainingAndStatus(name, pageable,1);

		List<UserEntity> entities = entitiess.getContent();
		for (UserEntity entity : entities) {
			UserDTO userDTO = userConverter.toDTO(entity);
			userDTOs.add(userDTO);
		}

		return userDTOs;
	}

	@Override
	public ShipAddressDTO createShipAddress(Long userId, OrderDTO orderDTO) {
		ShipAddressEntity entity = new ShipAddressEntity();
		UserEntity userEntity = userRepository.findOne(userId);
		entity.setUser(userEntity);
		entity.setProvince(orderDTO.getShipAddress().getProvince());
		entity.setDistrict(orderDTO.getShipAddress().getDistrict());
		entity.setAddress(orderDTO.getShipAddress().getAddress());
		entity.setWard(orderDTO.getShipAddress().getWard());
		entity.setNumber(orderDTO.getShipAddress().getNumber());

		return shipAddressConverter.toDTO(shipAddressRepository.save(entity));

	}
	
	@Override
	public List<ShipAddressDTO> findAllShipAdress(Long userId) {	
		return shipAddressConverter.toDTOList(shipAddressRepository.findByUserId(userId));
	}
	
	
	@Override
	public ShipAddressDTO findOneAddress(Long addressId) {	
		return shipAddressConverter.toDTO(shipAddressRepository.findOne(addressId));
	}

	@Override
	public ShipAddressDTO saveShipAddress(Long userId,ShipAddressDTO shipAddress) {
		ShipAddressEntity entity;
		if(shipAddress.getId()!=null) {
			ShipAddressEntity oldEntity = shipAddressRepository.findOne(shipAddress.getId());
			entity = shipAddressConverter.toEntity(oldEntity, shipAddress);	
		}else {
			UserEntity userEntity = userRepository.findOne(userId);
			entity = shipAddressConverter.toEntity(shipAddress);
			entity.setUser(userEntity);
		}
		return shipAddressConverter.toDTO(shipAddressRepository.save(entity));
		
	}
	
	@Override
	public void deleteShipAddress(Long shipAddressId) {
		shipAddressRepository.delete(shipAddressId);
	}
	

	public UserDTO updateInWeb(Long id) {
		UserEntity userEntity = userRepository.findOne(id);
		UserDTO userDTO = userConverter.toDTO(userEntity);
		List<OrderDTO> orderDTO = orderConverter.toDTOList(orderRepository.findAllByEmail(userDTO.getUserName()));
		userDTO.setOrders(orderDTO);
		return userDTO;
	}
	
	@Override
	public UserDTO convertValidateUser(UserDTO dto) {
		List<ShipAddressEntity> addressEntities = shipAddressRepository.findByUserId(dto.getId());
		dto.setShipAddress(shipAddressConverter.toDTOList(addressEntities));
		return dto;
	}
	
	@Override
	@Transactional
	public void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException {
		UserEntity user =  userRepository.findOne(id) ;
		if(!ObjectUtils.isEmpty(user)) {
			user.setStatus(0);
		}
		
		userRepository.save(user);
	}

}
