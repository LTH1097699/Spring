package com.springbook.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.user.ShipAddressDTO;
import com.springbook.dto.user.UserDTO;

public interface IUserService {
	UserDTO insert(UserDTO userDTO);
	UserDTO adminInsert(UserDTO userDTO);
	UserDTO update(UserDTO userDTO);

	int existedUser(String username);
	
	UserDTO findOneById(Long id);

	List<UserDTO> findAll(Pageable pageable);

	Integer getTotalItems();
	List<UserDTO> findByNameTagContaining(String name, Pageable pageable);
	
	
	ShipAddressDTO createShipAddress(Long userId, OrderDTO orderDTO);
	ShipAddressDTO saveShipAddress(Long userId, ShipAddressDTO shipAddress);
	List<ShipAddressDTO> findAllShipAdress(Long userId);
	ShipAddressDTO findOneAddress(Long addressId);
	void deleteShipAddress(Long shipAddressId);
	UserDTO convertValidateUser(UserDTO dto);
	void delete(Long id) throws SQLIntegrityConstraintViolationException, ConstraintViolationException;
}
