package com.springbook.conveter.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springbook.contanst.Constant;
import com.springbook.conveter.book.BookConverter;
import com.springbook.conveter.book.WareHouseConverter;
import com.springbook.conveter.user.ShipAddressConverter;
import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.order.OrderDetailDTO;
import com.springbook.entity.book.WareHouseEntity;
import com.springbook.entity.order.OrderDetailEntity;
import com.springbook.entity.order.OrderEntity;
import com.springbook.repository.book.WareHouseRepository;
import com.springbook.repository.order.OrderRepository;
@Component
public class OrderConverter {
	@Autowired
	private ShipAddressConverter shipAddressConverter;
	
	@Autowired
	private BookConverter bookConverter;
	
	@Autowired
	private WareHouseConverter wareHouseConverter;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WareHouseRepository wareHouseRepository;
	
	public OrderDTO toDTO(OrderEntity entity) {
		OrderDTO dto = new OrderDTO();
		
		dto.setId(entity.getId());
		dto.setUsername(entity.getFullname());
		dto.setEmail(entity.getEmail());
		dto.setAddress(entity.getAddress());
		dto.setNumber(entity.getNumber());
		dto.setTotalQuantity(entity.getTotalQuantity());
		dto.setTotalPrice(entity.getTotalPrice());
		dto.setTotalProduct(entity.getTotalProduct());
		dto.setProvince(entity.getProvince());
		dto.setDistrict(entity.getDistrict());
		dto.setWard(entity.getWard());
		dto.setStatus(entity.getStatus());
		dto.setShipDate(entity.getCreateDate());
		dto.setShipMethod(entity.getShipMethod());
		dto.setPaymentMethod(entity.getPaymentMethod());
		return dto;
		
	}
	public List<OrderDTO> toDTOList(List<OrderEntity> entities){
		List<OrderDTO> dtos = new ArrayList<>();
		for(OrderEntity entity:entities) {
			OrderDTO dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
	
	
	public OrderEntity toEntity(OrderDTO dto) {
		OrderEntity entity = new OrderEntity();
		
		entity.setFullname(dto.getUsername());
		entity.setEmail(dto.getEmail());
		entity.setAddress(dto.getAddress());
		entity.setNumber(dto.getNumber());
		
		entity.setStatus(Constant.PENDING_STATUS);
		entity.setProvince(dto.getProvince());
		entity.setDistrict(dto.getDistrict());
		entity.setWard(dto.getWard());
		
		entity.setPaymentMethod(dto.getPaymentMethod());
		entity.setShipMethod(dto.getShipMethod());
		//set total price and quantity after addDetailOrder
		return entity;
	}
	
	public OrderEntity toEntity(OrderEntity orderEntity,OrderDTO orderDTO) {
		orderEntity.setFullname(orderDTO.getUsername());
		orderEntity.setEmail(orderDTO.getEmail());
		orderEntity.setAddress(orderDTO.getAddress());
		orderEntity.setNumber(orderDTO.getNumber());
		orderEntity.setPaymentMethod(orderDTO.getPaymentMethod());
		orderEntity.setShipMethod(orderDTO.getShipMethod());
		
		orderEntity.setTotalPrice(orderDTO.getTotalPrice());
		orderEntity.setTotalQuantity(orderDTO.getTotalQuantity());
		
		return orderEntity;
	}
	
	public OrderDetailDTO toDTODetails(OrderDetailEntity entity) {
		OrderEntity orderEntity = orderRepository.findOne(entity.getOrder().getId());
		OrderDetailDTO dto = new OrderDetailDTO();
		dto.setBook(bookConverter.toDTO(entity.getBook()));
		dto.setId(entity.getId());
		dto.setOrderId(entity.getOrder().getId());
		dto.setQuantity(entity.getQuantity());
		dto.setUnitPrice(entity.getUnitPrice());
		dto.setTotalPrice(entity.getTotalPrice());
		dto.setOrder(toDTO(entity.getOrder()));
		if(orderEntity.getStatus()==Constant.SHIP_STATUS) {
			WareHouseEntity wareHouseEntity = wareHouseRepository.findOne(entity.getIdWareHouse());
			dto.setWareHouseName(wareHouseEntity.getName());
			dto.setWareHouseId(entity.getIdWareHouse());
			dto.setWareHouseDTO(wareHouseConverter.toDTO(wareHouseEntity));
		}
		
		return dto;
	}
	
	public List<OrderDetailDTO> toDTOListDetails(List<OrderDetailEntity> entities){
		List<OrderDetailDTO> dtos = new ArrayList<>();
		for(OrderDetailEntity entity: entities) {
			OrderDetailDTO dto = toDTODetails(entity);
			dtos.add(dto);
		}
		return dtos;
	}
}
