package com.springbook.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.springbook.contanst.Constant;
import com.springbook.conveter.book.BookConverter;
import com.springbook.conveter.book.WareHouseConverter;
import com.springbook.conveter.order.OrderConverter;
import com.springbook.conveter.user.UserConverter;
import com.springbook.dto.book.WareHouseDTO;
import com.springbook.dto.cart.CartDTO;
import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.order.OrderDetailDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.BookWarehouseEntity;
import com.springbook.entity.book.WareHouseEntity;
import com.springbook.entity.cart.CartEntity;
import com.springbook.entity.cart.DetailCartEntity;
import com.springbook.entity.order.OrderDetailEntity;
import com.springbook.entity.order.OrderEntity;
import com.springbook.entity.user.ShipAddressEntity;
import com.springbook.entity.user.UserEntity;
import com.springbook.repository.book.BookRepository;
import com.springbook.repository.book.BookWareHouseRepository;
import com.springbook.repository.book.WareHouseRepository;
import com.springbook.repository.cart.CartRepository;
import com.springbook.repository.cart.DetailCartRepository;
import com.springbook.repository.order.DetailOrderRepository;
import com.springbook.repository.order.OrderRepository;
import com.springbook.repository.user.ShipAddressRepository;
import com.springbook.repository.user.UserRepository;
import com.springbook.service.IOrderService;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private ShipAddressRepository shipAddressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DetailOrderRepository detailOrderRepository;

	@Autowired
	private OrderConverter orderConverter;

	@Autowired
	private BookConverter bookConverter;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private WareHouseConverter wareHouseConverter;

	@Autowired
	private WareHouseRepository wareHouseRepository;

	@Autowired
	private BookWareHouseRepository bookWareHouseRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private DetailCartRepository detailCartRepository;

	@Override
	public OrderDTO getUserInfo(long userId) {
		OrderDTO orderDTO = new OrderDTO();
		UserEntity userEntity = userRepository.findOne(userId);
		orderDTO.setUser(userConverter.toDTO(userEntity));
		return orderDTO;
	}

	@Override
	public OrderDTO addOrder(OrderDTO orderDTO, Long userId) {
		UserEntity entity = userRepository.findOne(userId);
		OrderEntity orderEntity = new OrderEntity();
		if (orderDTO.getShipAddress().getId() == null) {

			orderEntity.setUser(entity);
			orderEntity.setEmail(entity.getEmail());
			orderEntity.setFullname(entity.getFullName());
			orderEntity.setStatus(Constant.PENDING_STATUS);

			orderEntity.setNumber(orderDTO.getShipAddress().getNumber());
			orderEntity.setAddress(orderDTO.getShipAddress().getAddress());
			orderEntity.setProvince(orderDTO.getShipAddress().getProvince());
			orderEntity.setDistrict(orderDTO.getShipAddress().getDistrict());
			orderEntity.setWard(orderDTO.getShipAddress().getWard());
		} else {
			ShipAddressEntity shipAddressEntity = shipAddressRepository.findOne(orderDTO.getShipAddress().getId());
			orderEntity.setUser(entity);
			orderEntity.setEmail(entity.getEmail());
			orderEntity.setFullname(entity.getFullName());

			orderEntity.setStatus(Constant.PENDING_STATUS);
			orderEntity.setNumber(shipAddressEntity.getNumber());
			orderEntity.setAddress(shipAddressEntity.getAddress());
			orderEntity.setProvince(shipAddressEntity.getProvince());
			orderEntity.setDistrict(shipAddressEntity.getDistrict());
			orderEntity.setWard(shipAddressEntity.getWard());
		}

		// update stock
		return orderConverter.toDTO(orderRepository.save(orderEntity));
	}

	@Override
	public OrderDTO addOrder(OrderDTO orderDTO) {
		OrderEntity orderEntity = orderConverter.toEntity(orderDTO);
		return orderConverter.toDTO(orderRepository.save(orderEntity));
	}

	@Override
	@Transactional
	public OrderDTO addDetailOrder(OrderDTO orderDTO, HashMap<Long, CartDTO> cart) {
		OrderEntity orderEntity = orderRepository.findOne(orderDTO.getId());
		List<OrderDetailEntity> detailEntities = new ArrayList<>();
		int totalQuantityOrder = 0;
		double totalPriceOrder = 0;
		int i = 0;

		for (Map.Entry<Long, CartDTO> itemCard : cart.entrySet()) {
			OrderDetailEntity orderDetailEntity = new OrderDetailEntity();

			i++;
			BookEntity bookEntity = bookRepository.findOne(itemCard.getKey());
			int quantity = itemCard.getValue().getQuantity();

			orderDetailEntity.setOrder(orderEntity);
			orderDetailEntity.setBook(bookEntity);
			orderDetailEntity.setQuantity(quantity);
			orderDetailEntity.setUnitPrice(bookEntity.getPrice());
			orderDetailEntity.setTotalPrice(quantity * bookEntity.getPrice());

			totalQuantityOrder += orderDetailEntity.getQuantity();
			totalPriceOrder += orderDetailEntity.getTotalPrice();

			detailOrderRepository.save(orderDetailEntity);
			detailEntities.add(orderDetailEntity);
		}
		orderEntity.setTotalQuantity(totalQuantityOrder);
		orderEntity.setTotalPrice(totalPriceOrder);
		orderEntity.setTotalProduct(i);

		return orderConverter.toDTO(orderRepository.save(orderEntity));
	}

	@Override
	public List<OrderDTO> findAll(Pageable pageable) {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		List<OrderEntity> entities = orderRepository.findAll(pageable).getContent();
		for (OrderEntity entity : entities) {
			OrderDTO orderDTO = orderConverter.toDTO(entity);

			orderDTOs.add(orderDTO);
		}
		return orderDTOs;
	}

	@Override
	public Integer getTotalItems() {
		return (int) orderRepository.count();
	}

	@Override
	public OrderDTO findOneById(Long id) {
		OrderEntity orderEntity = orderRepository.findOne(id);
		OrderDTO orderDTO = orderConverter.toDTO(orderEntity);
		List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

		for (OrderDetailEntity entity : orderEntity.getOrderDetails()) {
			OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
			orderDetailDTO.setBook(bookConverter.toDTO(entity.getBook()));
			orderDetailDTO.setId(entity.getId());
			orderDetailDTO.setQuantity(entity.getQuantity());
			orderDetailDTO.setUnitPrice(entity.getUnitPrice());
			orderDetailDTO.setTotalPrice(entity.getTotalPrice());
			
			if (entity.getIdWareHouse() != null) {
				WareHouseEntity wareHouseEntity = wareHouseRepository.findOne(entity.getIdWareHouse());
				orderDetailDTO.setWareHouseName(wareHouseEntity.getName());
				orderDetailDTO.setWareHouseId(entity.getIdWareHouse());
			}else {
				List<WareHouseDTO> wareHouseList = wareHouseConverter.toDTOList(wareHouseRepository.getListWareHouseOfBook(entity.getBook().getId()));
				orderDetailDTO.setWareHouse(wareHouseList);
			}
			orderDetailDTOs.add(orderDetailDTO);
		}
		orderDTO.setOrderDetails(orderDetailDTOs);

		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO editOrder(OrderDTO order) {
		OrderEntity entity = orderRepository.findOne(order.getId());
		entity.setNumber(order.getNumber());
		entity.setAddress(order.getAddress());
		entity.setProvince(order.getProvince());
		entity.setDistrict(order.getDistrict());
		entity.setWard(order.getWard());
		entity.setShipMethod(order.getShipMethod());
		entity.setStatus(order.getStatus());
		List<OrderDetailDTO> orderDetailDTOs = order.getOrderDetails();
		int totalQuantity = 0;
		double totalPrice = 0;
		for (OrderDetailDTO dto : orderDetailDTOs) {
			OrderDetailEntity orderDetailEntity = detailOrderRepository.findOne(dto.getId());
			if (order.getStatus() == Constant.SHIP_STATUS) {
				orderDetailEntity.setIdWareHouse(dto.getWareHouseId());

			} else if (order.getStatus() == Constant.REFUND_STATUS) {
				BookWarehouseEntity bookWarehouseEntity = bookWareHouseRepository.findByBookIdAndWareHouseId(
						orderDetailEntity.getBook().getId(), orderDetailEntity.getIdWareHouse());
				int quantitywarehouse = bookWarehouseEntity.getQuantity();
				bookWarehouseEntity.setQuantity(quantitywarehouse + orderDetailEntity.getQuantity());
				bookWareHouseRepository.save(bookWarehouseEntity);
			} else {

				totalQuantity += dto.getQuantity();
				totalPrice += dto.getQuantity() * orderDetailEntity.getUnitPrice();
				orderDetailEntity.setQuantity(dto.getQuantity());
				orderDetailEntity.setTotalPrice(dto.getQuantity() * orderDetailEntity.getUnitPrice());
				
				entity.setTotalQuantity(totalQuantity);
				entity.setTotalPrice(totalPrice);
			}

			detailOrderRepository.save(orderDetailEntity);
		}
		

		return orderConverter.toDTO(orderRepository.save(entity));

	}

	@Override
	@Transactional
	public void deleteOrderDetail(Long id) {
		detailOrderRepository.delete(id);
	}

	@Override
	@Transactional
	public void deleteOrder(Long id) {
		orderRepository.delete(id);
	}

	@Override
	public Map<Integer, List<OrderDTO>> search(String status, String start, String end, Pageable pageable) {
		int totalItem = 0;
		Map<Integer, List<OrderDTO>> map = new HashMap<>();
		List<OrderDTO> orderDTOs = new ArrayList<>();
		Page<OrderEntity> orderEntities = null;
		List<OrderEntity> result = new ArrayList<>();
		;
		if (StringUtils.hasText(status) && !StringUtils.hasText(start) && !StringUtils.hasText(end)) {
			int sta = Integer.parseInt(status);
			result = orderRepository.findByStatusOrderByCreateDateDesc(sta);
		}
		if (!StringUtils.hasText(status) && StringUtils.hasText(start) && StringUtils.hasText(end)) {
			if (start.equals(end)) {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(start);
				result = orderRepository.findByCreateDateBetween(strStart, strEnd);
			} else {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(end);
				result = orderRepository.findByCreateDateBetween(strStart, strEnd);
			}

		}
		if (StringUtils.hasText(status) && StringUtils.hasText(start) && StringUtils.hasText(end)) {
			int sta = Integer.parseInt(status);
			if (start.equals(end)) {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(start);
				result = orderRepository.findByStatusAndCreateDateBetween(sta, strStart, strEnd);
			} else {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(end);
				result = orderRepository.findByStatusAndCreateDateBetween(sta, strStart, strEnd);
			}
		}
		totalItem = result.size();
		List<OrderEntity> pageList = result.stream().skip(pageable.getPageSize() * pageable.getPageNumber())
				.limit(pageable.getPageSize()).collect(Collectors.toList());
		orderEntities = new PageImpl<>(pageList, pageable, totalItem);

		if (orderEntities.getContent().size() != 0) {
			List<OrderEntity> entities = orderEntities.getContent();
			for (OrderEntity entity : entities) {
				OrderDTO orderDTO = orderConverter.toDTO(entity);
				List<OrderDetailDTO> detailDTOs = new ArrayList<>();
				for (OrderDetailEntity detailEntity : entity.getOrderDetails()) {
					OrderDetailDTO orderDetailDTO = orderConverter.toDTODetails(detailEntity);
					detailDTOs.add(orderDetailDTO);
				}
				orderDTO.setOrderDetails(detailDTOs);
				orderDTOs.add(orderDTO);
			}
			map.put(totalItem, orderDTOs);
		}

		return map;
	}

	public String formatDateStart(String start) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		return df.format(date);
	}

	public String formatDateEnd(String end) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		return df.format(date);
	}

	@Override
	public OrderDTO findByUserEmail(String email, Pageable pageable) {
		OrderDTO order = new OrderDTO();
		List<OrderDTO> dtos = new ArrayList<>();
		List<OrderEntity> entities = orderRepository.findByEmail(email);
		
		Page<OrderEntity> pageEntity = null;
		if(pageable != null) {
			List<OrderEntity> pageList = entities.stream().skip(pageable.getPageSize()*pageable.getPageNumber()).
					limit(pageable.getPageSize()).collect(Collectors.toList());
			pageEntity = new PageImpl<>(pageList,pageable,entities.size());
			for (OrderEntity entity : pageEntity.getContent()) {
				OrderDTO dto = orderConverter.toDTO(entity);
				dto.setOrderDetails(orderConverter.toDTOListDetails(entity.getOrderDetails()));

				dtos.add(dto);
			}
			order.setPage(pageable.getPageNumber()+1);
			order.setLimit(pageable.getPageSize());
			
		}else {
			for (OrderEntity entity : entities) {
				OrderDTO dto = orderConverter.toDTO(entity);
				dto.setOrderDetails(orderConverter.toDTOListDetails(entity.getOrderDetails()));

				dtos.add(dto);
			}
		}
		order.setListResult(dtos);
		order.setTotalItem(entities.size());
		
		return order;
	}

	@Override
	public List<OrderDTO> findByCreateByDESCLimit3(String email) {
		List<OrderEntity> entities = orderRepository.findByCreateByDESCLimit3(email);
		return orderConverter.toDTOList(entities);
	}

	@Override
	public List<OrderDTO> findAllForExportExcel(String status, String start, String end) {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		List<OrderEntity> entities = new ArrayList<>();
		if (StringUtils.hasText(status) && !StringUtils.hasText(start) && !StringUtils.hasText(end)) {
			int sta = Integer.parseInt(status);
			entities = orderRepository.findByStatusOrderByCreateDateDesc(sta);
		}
		if (!StringUtils.hasText(status) && StringUtils.hasText(start) && StringUtils.hasText(end)) {
			if (start.equals(end)) {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(start);
				entities = orderRepository.findByCreateDateBetween(strStart, strEnd);
			} else {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(end);
				entities = orderRepository.findByCreateDateBetween(strStart, strEnd);
			}
		}
		if (StringUtils.hasText(status) && StringUtils.hasText(start) && StringUtils.hasText(end)) {
			int sta = Integer.parseInt(status);
			if (start.equals(end)) {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(start);
				entities = orderRepository.findByStatusAndCreateDateBetween(sta, strStart, strEnd);
			} else {
				String strStart = formatDateStart(start);
				String strEnd = formatDateEnd(end);
				entities = orderRepository.findByStatusAndCreateDateBetween(sta, strStart, strEnd);
			}
		}
		if (!StringUtils.hasText(status) && !StringUtils.hasText(start) && !StringUtils.hasText(end)) {
			entities = orderRepository.findAll();
		}
		for (OrderEntity entity : entities) {
			OrderDTO orderDTO = orderConverter.toDTO(entity);
			List<OrderDetailDTO> detailDTOs = new ArrayList<>();
			for (OrderDetailEntity detailEntity : entity.getOrderDetails()) {
				OrderDetailDTO orderDetailDTO = orderConverter.toDTODetails(detailEntity);
				detailDTOs.add(orderDetailDTO);
			}
			orderDTO.setOrderDetails(detailDTOs);
			orderDTOs.add(orderDTO);
		}
		return orderDTOs;
	}

	@Override
	public List<OrderDetailDTO> findAllOrderDetailsStatusShip(Pageable pageable) {

		List<OrderDetailDTO> detailDTOs = new ArrayList<>();
		List<OrderEntity> entities = orderRepository.findAll(pageable).getContent();
		for (OrderEntity entity : entities) {
			if (entity.getStatus() == Constant.SHIP_STATUS) {
				for (OrderDetailEntity detailEntity : entity.getOrderDetails()) {
					OrderDetailDTO orderDetailDTO = orderConverter.toDTODetails(detailEntity);

					detailDTOs.add(orderDetailDTO);
				}
			}
		}
		return detailDTOs;
	}

	@Override
	public List<OrderDetailDTO> initializeOrderDetail(List<Long> ids, Long userId) {
		List<OrderDetailDTO> dtos = new ArrayList<>();
		CartEntity entity = cartRepository.findOneByUserId(userId);
		for (Long id : ids) {
			DetailCartEntity detailCartEntity = detailCartRepository.findOneByCartIdAndBookId(entity.getId(), id);
			if (ObjectUtils.isEmpty(detailCartEntity)) {
				BookEntity bookEntity = bookRepository.findOne(id);
				OrderDetailDTO dto = new OrderDetailDTO();
				dto.setBook(bookConverter.toDTO(bookEntity));
				dto.setQuantity(1);
				dtos.add(dto);
			} else {
				OrderDetailDTO dto = new OrderDetailDTO();
				dto.setBook(bookConverter.toDTO(detailCartEntity.getBook()));
				dto.setQuantity(detailCartEntity.getQuantity());
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	public OrderDTO addOrderInAdmin(OrderDTO orderDTO) {
		UserEntity entity = userRepository.findOneByEmail(orderDTO.getEmail());
		OrderEntity orderEntity = new OrderEntity();

		ShipAddressEntity shipAddressEntity = shipAddressRepository.findOne(orderDTO.getAddressId());
		orderEntity.setUser(entity);
		orderEntity.setEmail(orderDTO.getEmail());
		orderEntity.setFullname(orderDTO.getUsername());

		orderEntity.setStatus(Constant.PENDING_STATUS);
		orderEntity.setNumber(shipAddressEntity.getNumber());
		orderEntity.setAddress(shipAddressEntity.getAddress());
		orderEntity.setProvince(shipAddressEntity.getProvince());
		orderEntity.setDistrict(shipAddressEntity.getDistrict());
		orderEntity.setWard(shipAddressEntity.getWard());

		return orderConverter.toDTO(orderRepository.save(orderEntity));
	}
	
	
	@Override
	public OrderDTO addDetailOrderByAdmin(OrderDTO orderDTO,OrderDTO newOrderDTO) {
		OrderEntity orderEntity = orderRepository.findOne(newOrderDTO.getId());
		
		
		int totalQuantityOrder = 0;
		double totalPriceOrder = 0;
		int i = 0;

		for (OrderDetailDTO item : orderDTO.getOrderDetails()) {
			i++;
			OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
			BookEntity bookEntity = bookRepository.findOne(item.getId());
			orderDetailEntity.setBook(bookEntity);
			orderDetailEntity.setOrder(orderEntity);
			
			orderDetailEntity.setQuantity(item.getQuantity());
			orderDetailEntity.setUnitPrice(bookEntity.getPrice());
			orderDetailEntity.setTotalPrice(item.getQuantity() * bookEntity.getPrice());

			detailOrderRepository.save(orderDetailEntity);
			
			totalQuantityOrder += orderDetailEntity.getQuantity();
			totalPriceOrder += orderDetailEntity.getTotalPrice();
			
		}
		orderEntity.setTotalQuantity(totalQuantityOrder);
		orderEntity.setTotalPrice(totalPriceOrder);
		orderEntity.setTotalProduct(i);

		return orderConverter.toDTO(orderRepository.save(orderEntity));
	}
	
	@Override
	public OrderDTO returnValidateOrderShip(OrderDTO order) {
		for(OrderDetailDTO item : order.getOrderDetails()) {
			List<WareHouseDTO> wareHouseList = wareHouseConverter.toDTOList(wareHouseRepository.getListWareHouseOfBook(item.getBook().getId()));
			item.setWareHouse(wareHouseList);
		}
		return order;
	}

}
