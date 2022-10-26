package com.springbook.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springbook.conveter.book.BookConverter;
import com.springbook.conveter.book.WareHouseConverter;
import com.springbook.conveter.order.OrderConverter;
import com.springbook.dto.book.BookWareHouseDTO;
import com.springbook.dto.book.WareHouseDTO;
import com.springbook.dto.order.OrderDetailDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.BookWarehouseEntity;

import com.springbook.entity.book.WareHouseEntity;
import com.springbook.entity.order.OrderDetailEntity;
import com.springbook.repository.book.BookRepository;
import com.springbook.repository.book.BookWareHouseRepository;

import com.springbook.repository.book.WareHouseRepository;
import com.springbook.repository.order.DetailOrderRepository;
import com.springbook.repository.order.OrderRepository;

@Service
public class WareHouseService {
	@Autowired
	private WareHouseConverter wareHouseConverter;

	@Autowired
	private WareHouseRepository wareHouseRepository;

	@Autowired
	private BookConverter bookConverter;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookWareHouseRepository bookWareHouseRepository;

	@Autowired
	private DetailOrderRepository detailOrderRepository;

	@Autowired
	private OrderConverter orderConverter;

	public Map<String, String> findAllMap() {
		List<WareHouseEntity> entities = wareHouseRepository.findAll();
		return wareHouseConverter.toMap(entities);
	}

	public int getTotalItems() {
		return (int) wareHouseRepository.count();
	}

	public List<WareHouseDTO> findAll(Pageable pageable) {
		List<WareHouseEntity> entities = wareHouseRepository.findAll(pageable).getContent();
		return wareHouseConverter.toDTOList(entities);
	}

	public List<WareHouseDTO> findAll() {
		List<WareHouseEntity> entities = wareHouseRepository.findAll();
		return wareHouseConverter.toDTOList(entities);
	}

	public List<WareHouseDTO> findAllByIds(Long[] ids) {
		List<WareHouseEntity> entities = new ArrayList<>();
		for (Long id : ids) {
			WareHouseEntity entity = wareHouseRepository.findOne(id);
			entities.add(entity);
		}
		return wareHouseConverter.toDTOList(entities);
	}

	public WareHouseDTO save(WareHouseDTO dto) {
		WareHouseEntity entity = new WareHouseEntity();
		if (dto.getId() != null) {
			WareHouseEntity oldEntity = wareHouseRepository.findOne(dto.getId());
			entity = wareHouseConverter.toEntity(oldEntity, dto);
		} else {
			entity = wareHouseConverter.toEntity(dto);
		}
		return wareHouseConverter.toDTO(wareHouseRepository.save(entity));
	}

	public void delete(Long id) throws SQLIntegrityConstraintViolationException, ConstraintViolationException {
		wareHouseRepository.delete(id);
	}

	public List<WareHouseDTO> search(String keyword,Pageable pageable) {
		List<WareHouseEntity> entities = wareHouseRepository.search(keyword);
		if(pageable != null) {
			List<WareHouseEntity> pageList = entities.stream().skip(pageable.getPageSize()*pageable.getPageNumber()).limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<WareHouseEntity> page = new PageImpl<>(pageList,pageable,entities.size());
			entities = page.getContent();
		}
		return wareHouseConverter.toDTOList(entities);
	}
	
	public int totalItemSearch(String keyword) {
		List<WareHouseEntity> entities = wareHouseRepository.search(keyword);
		return entities.size();
	}

	public WareHouseDTO findById(Long id) {
		WareHouseEntity entity = wareHouseRepository.findOne(id);
		return wareHouseConverter.toDTO(entity);
	}

	public void insertQuantityWareHouse(List<String> list, Long bookId) {
		Map<String, String> mapWareHouseAndQuantity = bookConverter.OnedTo2dArray(list);
		BookEntity bookEntity = bookRepository.findOne(bookId);
		int quantity = 0;
		for (Map.Entry<String, String> mapItem : mapWareHouseAndQuantity.entrySet()) {
			quantity += Integer.parseInt(mapItem.getValue());
			WareHouseEntity wareHouseEntity = wareHouseRepository.findOneByCode(mapItem.getKey());
			BookWarehouseEntity bookWareHouse = new BookWarehouseEntity();

			bookWareHouse.setQuantity(Integer.parseInt(mapItem.getValue()));
			bookWareHouse.setBook(bookEntity);
			bookWareHouse.setWareHouse(wareHouseEntity);
			bookWareHouseRepository.save(bookWareHouse);
		}
		bookEntity.setQuantity(quantity);
		bookRepository.save(bookEntity);
	}

	public Map<String, Integer> getBookWareHouse(Long bookId) {
		Map<String, Integer> map = new HashMap<>();

		List<BookWarehouseEntity> entitises = bookWareHouseRepository.findByBookId(bookId);
		for (BookWarehouseEntity entity : entitises) {
			map.put(entity.getWareHouse().getCode(), entity.getQuantity());
		}
		return map;
	}

	public void updateBookWareHouse(List<String> list, Long bookId) {

		Map<String, String> map = bookConverter.OnedTo2dArray(list);
		BookEntity bookEntity = bookRepository.findOne(bookId);
		// get list warehose with id stock
		List<BookWarehouseEntity> entities = bookWareHouseRepository.findByBookId(bookId);

		Map<String, String> update = new HashMap<>();
		// get những id trùng với map => những id trùng cần update
		for (Map.Entry<String, String> m : map.entrySet()) {
			for (BookWarehouseEntity entity : entities) {
				if (Objects.equals(m.getKey(), entity.getWareHouse().getCode())) {
					update.put(m.getKey(), m.getValue());
				}
			}
		}
		// remove id trùng ra khỏi list entity => warehouse cần xóa
		update.forEach((t, u) -> {
			for (int i = entities.size() - 1; i >= 0; i--) {
				if (Objects.equals(t, entities.get(i).getWareHouse().getCode())) {
					entities.remove(i);
				}
			}
		});
		// remove id trùng ra khỏi map => warehouse cần thêm
		update.forEach((t, u) -> {
			map.keySet().removeIf(a -> Objects.equals(t, a));
		});
		// quantity stock
		int quantity = 0;
		// update
		for (Map.Entry<String, String> m : update.entrySet()) {
			quantity += Integer.parseInt(m.getValue());
			WareHouseEntity wareHouseEntity = wareHouseRepository.findOneByCode(m.getKey());
			BookWarehouseEntity bookWarehouse = bookWareHouseRepository.findByBookIdAndWareHouseId(bookId,
					wareHouseEntity.getId());
			bookWarehouse.setQuantity(Integer.parseInt(m.getValue()));
			bookWareHouseRepository.save(bookWarehouse);
		}
		// delete
		for (BookWarehouseEntity entity : entities) {
			bookWareHouseRepository.delete(entity);
		}

		// add
		for (Map.Entry<String, String> m : map.entrySet()) {
			quantity += Integer.parseInt(m.getValue());
			BookWarehouseEntity bookWarehouse = new BookWarehouseEntity();
			WareHouseEntity wareHouseEntity = wareHouseRepository.findOneByCode(m.getKey());
			bookWarehouse.setBook(bookEntity);
			bookWarehouse.setWareHouse(wareHouseEntity);
			bookWarehouse.setQuantity(Integer.parseInt(m.getValue()));
			bookWareHouseRepository.save(bookWarehouse);
		}
		// cập nhật lại so luong
		bookEntity.setQuantity(quantity);
		bookRepository.save(bookEntity);

	}

	public BookWareHouseDTO searchBookInWareHouse(Long wareHouseId, String keyword, Pageable pageable) {
		BookWareHouseDTO dto = new BookWareHouseDTO();
		List<BookWareHouseDTO> bookWareHouseDTOs = new ArrayList<>();
		List<BookWarehouseEntity> bookWarehouseEntity = new ArrayList<>();
		if (wareHouseId != null && !"".equals(keyword)) {
			bookWarehouseEntity = bookWareHouseRepository.findAllByBookKeyWordAndWareHouseId(wareHouseId, keyword);
			System.out.println("find with 2 condition");
		}
		if (wareHouseId != null && "".equals(keyword) ) {
			bookWarehouseEntity = bookWareHouseRepository.findByWareHouseId(wareHouseId);
			System.out.println("find with just wareHouse Id");
		}
		if (wareHouseId == null && !"".equals(keyword) ) {
			System.out.println("find with just Keyword");
			bookWarehouseEntity = bookWareHouseRepository.findAllByBookKeyWord(keyword);
		}
		if(wareHouseId == null && "".equals(keyword)) {
			bookWarehouseEntity = bookWareHouseRepository.findAll();
		}
		int totalItem = bookWarehouseEntity.size();
		if(pageable!= null) {
			Page<BookWarehouseEntity> page = null;
			List<BookWarehouseEntity> subList = bookWarehouseEntity.stream().skip(pageable.getPageNumber()*pageable.getPageSize()).
					limit(pageable.getPageSize()).collect(Collectors.toList());
			page = new PageImpl<>(subList,pageable,totalItem);
			
			bookWarehouseEntity = page.getContent();
			dto.setLimit(pageable.getPageSize());
			dto.setPage(pageable.getPageNumber()+1);
		}
		
	
		for (BookWarehouseEntity entity : bookWarehouseEntity) {
			BookWareHouseDTO dtoo = new BookWareHouseDTO();
			dtoo.setBook(bookConverter.toDTO(entity.getBook()));
			dtoo.setWareHouse(wareHouseConverter.toDTO(entity.getWareHouse()));
			dtoo.setId(entity.getId());
			dtoo.setQuantity(entity.getQuantity());
			bookWareHouseDTOs.add(dtoo);
		}
		
		dto.setListResult(bookWareHouseDTOs);
		dto.setTotalItem(totalItem);
		return dto;
	}

	public List<BookWareHouseDTO> allListBookInWareHouse(Pageable pageable) {
		List<BookWareHouseDTO> bookWareHouseDTOs = new ArrayList<>();
		List<BookWarehouseEntity> bookWarehouseEntity = bookWareHouseRepository.findAll(pageable).getContent();
		for (BookWarehouseEntity entity : bookWarehouseEntity) {
			BookWareHouseDTO dto = new BookWareHouseDTO();
			dto.setBook(bookConverter.toDTO(entity.getBook()));
			dto.setWareHouse(wareHouseConverter.toDTO(entity.getWareHouse()));
			dto.setId(entity.getId());
			dto.setQuantity(entity.getQuantity());

			bookWareHouseDTOs.add(dto);
		}
		return bookWareHouseDTOs;
	}

	public int getTotalItemBookWarehouse() {
		return (int) bookWareHouseRepository.count();
	}

	public BookWareHouseDTO getOneBookWareHouseById(Long bookwarehouseid) {
		BookWareHouseDTO dto = new BookWareHouseDTO();
		BookWarehouseEntity entity = bookWareHouseRepository.findOne(bookwarehouseid);
		dto.setQuantity(entity.getQuantity());
		dto.setBook(bookConverter.toDTO(entity.getBook()));
		dto.setWareHouse(wareHouseConverter.toDTO(entity.getWareHouse()));
		dto.setId(entity.getId());
		return dto;
	}

	public BookWareHouseDTO updateBookWareHouse(Long id, BookWareHouseDTO dto) {
		BookWarehouseEntity entity = bookWareHouseRepository.findOne(id);
		entity.setQuantity(dto.getQuantity());
		bookWareHouseRepository.save(entity);

		return null;
	}

	// ===============================================================================================================
	public OrderDetailDTO searchOrderInWareHouse(Long wareHouseId, String keyword,Pageable pageable) {
		OrderDetailDTO dto = new OrderDetailDTO();
		List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
		List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
		
		if (wareHouseId != null && !"".equals(keyword)) {
			orderDetailEntities = detailOrderRepository.searchByOrderIdOrBookCodeOrBookTitleAndWareHouseId(wareHouseId,
					keyword);
		}
		if (wareHouseId != null && "".equals(keyword)) {
			orderDetailEntities = detailOrderRepository.searchByIdWareHouse(wareHouseId);
		}
		if (wareHouseId == null && !"".equals(keyword)) {
			orderDetailEntities = detailOrderRepository.searchByOrderIdOrBookCodeOrBookTitle(keyword);
		}
		if(wareHouseId == null && "".equals(keyword)) {
			orderDetailEntities = detailOrderRepository.searchAll();
		}
		
		int totalItem = orderDetailEntities.size();
		if(pageable!= null) {
			Page<OrderDetailEntity> page = null;
			List<OrderDetailEntity> subList = orderDetailEntities.stream().skip(pageable.getPageNumber()*pageable.getPageSize()).
					limit(pageable.getPageSize()).collect(Collectors.toList());
			page = new PageImpl<>(subList,pageable,orderDetailEntities.size());
			
			orderDetailEntities = page.getContent();
			dto.setLimit(pageable.getPageSize());
			dto.setPage(pageable.getPageNumber()+1);
		}
		if(!ObjectUtils.isEmpty(orderDetailEntities)) {
			for (OrderDetailEntity entity : orderDetailEntities) {
				OrderDetailDTO dtoo = new OrderDetailDTO();
				dtoo.setBook(bookConverter.toDTO(entity.getBook()));
				dtoo.setQuantity(entity.getQuantity());
				dtoo.setOrder(orderConverter.toDTO(entity.getOrder()));
				WareHouseEntity wareHouseEntity = wareHouseRepository.findOne(entity.getIdWareHouse());
				dtoo.setWareHouseDTO(wareHouseConverter.toDTO(wareHouseEntity));
				dtoo.setId(entity.getId());
			
				orderDetailDTOs.add(dtoo);
			}
		}
		
		dto.setListResult(orderDetailDTOs);
		
		dto.setTotalItem(totalItem);
		
		return dto;
	}

}
