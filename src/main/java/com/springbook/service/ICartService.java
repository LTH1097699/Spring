package com.springbook.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springbook.dto.cart.CartDTO;
import com.springbook.entity.cart.DetailCartEntity;

public interface ICartService {
	HashMap<Long, CartDTO> addCart(long id, HashMap<Long, CartDTO> cart );
	HashMap<Long, CartDTO> editCart(long id,int quantity, HashMap<Long, CartDTO> cart );
	HashMap<Long, CartDTO> deleCart(long id, HashMap<Long, CartDTO> cart );
	
	CartDTO addCart(long id, Principal user );
	CartDTO editCart(long id,int quantity, Principal user);
	void deleCart(long id,Principal user);
	void deleteDetailCart(HashMap<Long, CartDTO> cart,Long userId);
	
	int totalQuantity( HashMap<Long, CartDTO> cart );
	double totalPrice( HashMap<Long, CartDTO> cart );
	void checkUserCart(Long user_id);
	
	List<DetailCartEntity> transferToDB(HashMap<Long, CartDTO> cart, Long user_id);
	
	HashMap<Long, CartDTO> findAll(HashMap<Long, CartDTO> cart,Long user_id);
	
	HashMap<Long, CartDTO> findAllbyListId(HashMap<Long, CartDTO> cart,Long user_id, List<Long> id);
	HashMap<Long, CartDTO> findAllbyListId(HashMap<Long, CartDTO> cart,  List<Long> id);
	Map<Long, CartDTO> findAllByAccount(Long user_id);

	HashMap<Long, CartDTO> deleteDetailCart(HashMap<Long, CartDTO> cart, HashMap<Long, CartDTO> oldCart);
	void deleteDetailCartInAdmin(List<Long> idBook, String email);
}
