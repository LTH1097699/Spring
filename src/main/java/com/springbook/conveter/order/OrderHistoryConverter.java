//package com.springbook.conveter.order;
//
//import org.springframework.stereotype.Component;
//
//import com.springbook.dto.order.OrderHistoryDTO;
//import com.springbook.entity.order.OrderHistoryEntity;
//@Component
//public class OrderHistoryConverter {
//	
//	public OrderHistoryDTO toDTO(OrderHistoryEntity entity) {
//		OrderHistoryDTO dto = new OrderHistoryDTO();
//		dto.setOrderid(entity.getOrderid());
//		dto.setEmail(entity.getEmail());
//		dto.setAddress(entity.getAddress());
//		dto.setFullname(entity.getFullname());
//		dto.setNumber(entity.getNumber());
//		dto.setShipDate(entity.getShipDate());
//		dto.setStatus(entity.getStatus());
//		dto.setTotalPrice(entity.getTotalPrice());
//		dto.setTotalProduct(entity.getTotalProduct());
//		dto.setTotalQuantity(entity.getTotalQuantity());
//		
//		return dto;
//		
//	}
//}
