package com.springbook.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.springbook.contanst.Constant;
import com.springbook.conveter.book.BookConverter;
import com.springbook.dto.book.BookDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.BookWarehouseEntity;
import com.springbook.entity.cart.CartEntity;
import com.springbook.entity.order.OrderDetailEntity;
import com.springbook.entity.order.OrderEntity;
import com.springbook.repository.book.BookRepository;
import com.springbook.repository.book.BookWareHouseRepository;
import com.springbook.repository.order.DetailOrderRepository;
import com.springbook.repository.order.OrderRepository;
import com.springbook.service.IBookService;

@Service
public class BookService implements IBookService {

	@Autowired
	private BookRepository repository;

	@Autowired
	private BookConverter bookConverter;

	@Autowired
	private BookWareHouseRepository bookWareHouseRepository;

	@Autowired
	private DetailOrderRepository detailOrderRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<BookDTO> findAll(Pageable pageable, short status) {
		List<BookDTO> bookDTOs = new ArrayList<>();
		List<BookEntity> bookEntities;
		if (status != 0) {
			bookEntities = repository.findAllByStatus(status, pageable).getContent();
		} else {
			bookEntities = repository.findAll(pageable).getContent();
		}

		for (BookEntity entity : bookEntities) {
			BookDTO bookDTO = bookConverter.toDTO(entity);

			bookDTOs.add(bookDTO);
		}
		return bookDTOs;
	}

	@Override
	public List<BookDTO> findAll(short status) {
		List<BookDTO> bookDTOs = new ArrayList<>();
		List<BookEntity> bookEntities;
		
		bookEntities = repository.findAll();

		for (BookEntity entity : bookEntities) {
			BookDTO bookDTO = bookConverter.toDTO(entity);

			bookDTOs.add(bookDTO);
		}
		return bookDTOs;
	}

	@Override
	@Transactional
	public BookDTO insert(BookDTO bookdto) {
		BookEntity bookEntity = bookConverter.toEntity(bookdto);
		return bookConverter.toDTO(repository.save(bookEntity));
	}

	@Override
	@Transactional
	public BookDTO update(BookDTO bookdto) {

		BookEntity oldBookEntity = repository.findOne(bookdto.getId());
		BookEntity bookEntity = bookConverter.toEntity(oldBookEntity, bookdto);

		repository.save(bookEntity);
		return bookConverter.toDTO(bookEntity);
	}

	@Override
	public int getTotalItem() {
		return (int) repository.count();
	}

	@Override
	public BookDTO findOneById(Long id) {
		BookEntity bookEntity = repository.findOneById(id);
		return bookConverter.toDTO(bookEntity);
	}

	@Override
	@Transactional
	public void delete(long id) throws SQLIntegrityConstraintViolationException, ConstraintViolationException {
		repository.delete(id);
	}

	@Override
	public List<BookDTO> findByCodeTagOrderByModifiedDateDescLimit6() {
		List<BookDTO> bookDTOs = new ArrayList<>();
		List<BookEntity> entities = repository.findByCodeTagOrderByModifiedDateDescLimit6();
		for (BookEntity entity : entities) {
			BookDTO bookDTO = bookConverter.toDTO(entity);
			bookDTOs.add(bookDTO);
		}
		return bookDTOs;
	}

	@Override
	public BookDTO search(String name, List<Long> categoryKey, List<Long> authorKey, List<Long> publisherKey,
			Pageable pageable) {
		BookDTO book = new BookDTO();
		List<BookDTO> bookDTOs = new ArrayList<>();
		List<BookEntity> entities = getListSearch(name, categoryKey, authorKey, publisherKey);
		if(pageable!= null) {
			int totalItem = entities.size();
			List<BookEntity> pageList = entities.stream().skip(pageable.getPageSize() * pageable.getPageNumber())
					.limit(pageable.getPageSize()).collect(Collectors.toList());
			// get page result
			Page<BookEntity> page = new PageImpl<>(pageList, pageable, totalItem);
			for (BookEntity entity : page.getContent()) {
				BookDTO bookDTO = bookConverter.toDTO(entity);
				bookDTOs.add(bookDTO);
			}
			book.setListResult(bookDTOs);
			book.setTotalItem(entities.size());
			return book;
		}else {
			for (BookEntity entity :entities) {
				BookDTO bookDTO = bookConverter.toDTO(entity);
				bookDTOs.add(bookDTO);
			}
			book.setListResult(bookDTOs);
			book.setTotalItem(entities.size());
			return book;
		}
		
	}
	
	@Override
	public List<BookDTO> searchInAdmin(String keyword,Pageable pageable) {
		List<BookDTO> bookDTOs = new ArrayList<>();
		List<BookEntity> entities = repository.search(keyword);
		if(pageable!= null) {	
			List<BookEntity> pageList = entities.stream().skip(pageable.getPageSize() * pageable.getPageNumber())
					.limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<BookEntity> page = new PageImpl<>(pageList, pageable, entities.size());	
			entities = page.getContent();
		}
		for (BookEntity entity :entities) {
			BookDTO bookDTO = bookConverter.toDTO(entity);
			bookDTOs.add(bookDTO);
		}
		return bookDTOs;
	}
	@Override
	public int totalItemSearch(String keyword) {
		List<BookEntity> entities = repository.search(keyword);
		return entities.size();
	}
	

	@Override
	public List<Long> updateQuantity(Long bookId, Long orderId) {
		if (bookId != null) {
			updateQuantityBookAtWareHouse(bookId);
			return Collections.emptyList();
		}
		if (orderId != null) {
			return updateQuantityWareHouseAtOrder(orderId);
		} else {
			return Collections.emptyList();
		}
	}

	public void updateQuantityBookAtWareHouse(Long bookId) {
		BookEntity bookEntity = repository.findOne(bookId);
		List<BookWarehouseEntity> bookWarehouseEntities = bookWareHouseRepository.findByBookId(bookId);

		int quantityWareHouse = 0;
		int quantityOrder = 0;
		// Total warehouse quantity of book
		for (BookWarehouseEntity entity : bookWarehouseEntities) {
			quantityWareHouse += entity.getQuantity();
		}
		// Total orderDetail of book
		List<OrderDetailEntity> orderDetailEntities = detailOrderRepository.findAllByBookId(bookId);
		if (orderDetailEntities != null) {
			for (OrderDetailEntity entity : orderDetailEntities) {
				OrderEntity orderEntity = orderRepository.findOne(entity.getOrder().getId());
				if (orderEntity.getStatus() != Constant.SHIP_STATUS && orderEntity.getStatus() != Constant.CANCEL_STATUS
						&& orderEntity.getStatus() != Constant.REFUND_STATUS) {
					quantityOrder += entity.getQuantity();
				}
			}
		}
		int quantityBook = quantityWareHouse - quantityOrder;
		bookEntity.setQuantity(quantityBook);
		repository.save(bookEntity);
	}

	public List<Long> updateQuantityWareHouseAtOrder(Long orderId) {
		// All detail orders by orderId
		OrderEntity orderEntity = orderRepository.findOne(orderId);
		List<OrderDetailEntity> orderDetailEntities = detailOrderRepository.findAllByOrderId(orderId);
		List<Long> bookIdList = new ArrayList<>();

		for (OrderDetailEntity entity : orderDetailEntities) {
			bookIdList.add(entity.getBook().getId());
			if (orderEntity.getStatus() == Constant.SHIP_STATUS) {
				BookWarehouseEntity bookWarehouseEntity = bookWareHouseRepository
						.findByBookIdAndWareHouseId(entity.getBook().getId(), entity.getIdWareHouse());
				int quantityWarehouse = bookWarehouseEntity.getQuantity();
				bookWarehouseEntity.setQuantity(quantityWarehouse - entity.getQuantity());
				bookWareHouseRepository.save(bookWarehouseEntity);
			}
		}
		return bookIdList;
	}

	public int checkEmpty(String name, List<Long> categoryKey, List<Long> authorKey, List<Long> publisherKey) {
		int a = 0;
		if (StringUtils.hasText(name) && CollectionUtils.isEmpty(categoryKey) && CollectionUtils.isEmpty(authorKey)
				&& CollectionUtils.isEmpty(publisherKey)) {
			a = 1;
		}
		if (!StringUtils.hasText(name) && !CollectionUtils.isEmpty(categoryKey) && CollectionUtils.isEmpty(authorKey)
				&& CollectionUtils.isEmpty(publisherKey)) {
			a = 2;
		}
		if (!StringUtils.hasText(name) && CollectionUtils.isEmpty(categoryKey) && !CollectionUtils.isEmpty(authorKey)
				&& CollectionUtils.isEmpty(publisherKey)) {
			a = 3;
		}
		if (!StringUtils.hasText(name) && CollectionUtils.isEmpty(categoryKey) && CollectionUtils.isEmpty(authorKey)
				&& !CollectionUtils.isEmpty(publisherKey)) {
			a = 4;
		}
		if (!StringUtils.hasText(name) && !CollectionUtils.isEmpty(categoryKey) && CollectionUtils.isEmpty(authorKey)
				&& !CollectionUtils.isEmpty(publisherKey)) {
			a = 11;
		}
		if (!StringUtils.hasText(name) && !CollectionUtils.isEmpty(categoryKey) && !CollectionUtils.isEmpty(authorKey)
				&& CollectionUtils.isEmpty(publisherKey)) {
			a = 12;
		}
		if (!StringUtils.hasText(name) && !CollectionUtils.isEmpty(categoryKey) && !CollectionUtils.isEmpty(authorKey)
				&& !CollectionUtils.isEmpty(publisherKey)) {
			a = 13;
		}
		if (!StringUtils.hasText(name) && CollectionUtils.isEmpty(categoryKey) && !CollectionUtils.isEmpty(authorKey)
				&& !CollectionUtils.isEmpty(publisherKey)) {
			a = 14;
		}
		if (StringUtils.hasText(name) && CollectionUtils.isEmpty(categoryKey) && !CollectionUtils.isEmpty(authorKey)
				&& !CollectionUtils.isEmpty(publisherKey)) {
			a = 15;
		}
		return a;
	}

	public List<BookEntity> getListSearch(String name, List<Long> categoryKey, List<Long> authorKey,
			List<Long> publisherKey) {
		List<BookEntity> entities = new ArrayList<>();
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_NAME) {
			entities = repository.search(name);
		}
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_CATEGORY) {
			entities = repository.findByCategory_idInAndStatus(categoryKey, Constant.DISPLAY_STATUS);
		}
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_AUTHOR) {
			entities = repository.findByCategory_idInAndStatus(authorKey, Constant.DISPLAY_STATUS);
		}
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_PUBLISHER) {
			entities = repository.findByAuthor_idInAndStatus(publisherKey, Constant.DISPLAY_STATUS);
		}
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_CATEGORY_AND_PUBLISHER) {
			entities = repository.findByCategory_idInAndPublisher_idInAndStatus(categoryKey, publisherKey,
					Constant.DISPLAY_STATUS);
		}
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_CATEGORY_AND_AUTHOR) {
			entities = repository.findByCategory_idInAndAuthor_idInAndStatus(categoryKey, authorKey,
					Constant.DISPLAY_STATUS);
		}
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_CATEGORY_AND_AUTHOR_AND_PUBLISHER) {
			entities = repository.findByCategory_idInAndAuthor_idInAndPublisher_idInAndStatus(categoryKey, authorKey,
					publisherKey, Constant.DISPLAY_STATUS);
		}
		if (checkEmpty(name, categoryKey, authorKey, publisherKey) == Constant.ONLY_AUTHOR_AND_PUBLISHER) {
			entities = repository.findByAuthor_idInAndPublisher_idInAndStatus(authorKey, publisherKey,
					Constant.DISPLAY_STATUS);
		}
		return entities;
	}
	

	

}
