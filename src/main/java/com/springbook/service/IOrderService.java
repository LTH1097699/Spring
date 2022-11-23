package com.springbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.springbook.dto.cart.CartDTO;
import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.order.OrderDetailDTO;

public interface IOrderService {
	public OrderDTO getUserInfo(long userId);
	public OrderDTO addOrder( OrderDTO orderDTO,Long userId);
	
	public OrderDTO addOrder( OrderDTO orderDTO);
	
	
	OrderDTO addDetailOrder( OrderDTO orderDTO, HashMap<Long, CartDTO> cart);
	
	public List<OrderDTO> findAll(Pageable pageable);
	public Integer getTotalItems();
	
	public OrderDTO findByUserEmail(String name, Pageable pageable);
	
	
	public OrderDTO findOneById(Long id);
	public void deleteOrderDetail(Long id);
	public void deleteOrder(Long id);
	public OrderDTO editOrder(OrderDTO orderDTO);

	public List<OrderDTO> findByCreateByDESCLimit3(String email);
	public List<OrderDTO> findAllForExportExcel(String status,String start,String end);
	public List<OrderDetailDTO> findAllOrderDetailsStatusShip(Pageable pageable);
	public Map<Integer, List<OrderDTO>> search(String status, String start, String end, Pageable pageable);
	List<OrderDetailDTO> initializeOrderDetail(List<Long> ids, Long userId);
	OrderDTO addDetailOrderByAdmin(OrderDTO orderDTO,OrderDTO newOrderDTO);
	OrderDTO addOrderInAdmin(OrderDTO orderDTO);
	OrderDTO returnValidateOrderShip(OrderDTO order);
}
