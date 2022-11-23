package com.springbook.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.springbook.conveter.book.BookConverter;
import com.springbook.dto.book.BookDTO;
import com.springbook.dto.cart.CartDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.cart.CartEntity;
import com.springbook.entity.cart.DetailCartEntity;
import com.springbook.entity.user.UserEntity;
import com.springbook.repository.book.BookRepository;
import com.springbook.repository.cart.CartRepository;
import com.springbook.repository.cart.DetailCartRepository;
import com.springbook.repository.user.UserRepository;
import com.springbook.service.ICartService;
import com.springbook.utils.SecurityUtils;

@Service
public class CartService implements ICartService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private DetailCartRepository detailCartRepository;

	@Autowired
	private BookConverter bookConverter;

	@Autowired
	private UserRepository userRepository;

	@Override
	public HashMap<Long, CartDTO> addCart(long id, HashMap<Long, CartDTO> cart) {
		BookDTO bookDTO = new BookDTO();
		CartDTO itemCart = new CartDTO();

		BookEntity bookEntity = bookRepository.findOneById(id);
		bookDTO = bookConverter.toDTO(bookEntity);

		if (bookDTO != null && cart.containsKey(id)) {
			itemCart = cart.get(id);
			itemCart.setBook(bookDTO);
			itemCart.setQuantity(itemCart.getQuantity() + 1);
			itemCart.setTotalPrice(itemCart.getQuantity() * itemCart.getBook().getPrice());
		} else {
			itemCart.setBook(bookDTO);
			itemCart.setQuantity(1);
			itemCart.setTotalPrice(itemCart.getBook().getPrice());
		}
		cart.put(id, itemCart);
		return cart;
	}

	@Override
	@Transactional
	public CartDTO addCart(long id, Principal user) {
		Long user_id = SecurityUtils.getPrincipal().getId();
		CartEntity cartEntity = cartRepository.findOneByUserId(user_id);

		DetailCartEntity detailCartEntity = detailCartRepository.findOneByCartIdAndBookId(cartEntity.getId(), id);
		if (detailCartEntity != null) {
			detailCartEntity.setQuantity(detailCartEntity.getQuantity() + 1);

			detailCartRepository.save(detailCartEntity);
		} else {
			DetailCartEntity newdetailCartEntity = new DetailCartEntity();
			newdetailCartEntity.setCart(cartEntity);
			newdetailCartEntity.setBook(bookRepository.findOne(id));
			newdetailCartEntity.setQuantity(1);

			detailCartRepository.save(newdetailCartEntity);
		}

		return null;
	}

	@Override
	@Transactional
	public HashMap<Long, CartDTO> editCart(long id, int quantity, HashMap<Long, CartDTO> cart) {
		if (cart == null) {
			return cart;
		}
		CartDTO itemCart = new CartDTO();
		if (cart.containsKey(id)) {
			itemCart = cart.get(id);
			itemCart.setQuantity(quantity);
			itemCart.setTotalPrice(quantity * itemCart.getBook().getPrice());
		}
		cart.put(id, itemCart);
		return cart;
	}

	@Override
	public HashMap<Long, CartDTO> deleCart(long id, HashMap<Long, CartDTO> cart) {
		if (cart == null) {
			return cart;
		}
		if (cart.containsKey(id)) {
			cart.remove(id);
		}
		return cart;
	}

	@Override
	@Transactional
	public void deleCart(long id, Principal user) {
		Long user_id = SecurityUtils.getPrincipal().getId();
		CartEntity cartEntity = cartRepository.findOneByUserId(user_id);
		detailCartRepository.deleteByCartIdAndBookId(cartEntity.getId(), id);
	}

	@Override
	public int totalQuantity(HashMap<Long, CartDTO> cart) {
		int totalQuantity = 0;
		for (Map.Entry<Long, CartDTO> itemCard : cart.entrySet()) {
			totalQuantity += itemCard.getValue().getQuantity();
		}
		return totalQuantity;
	}

	@Override
	public double totalPrice(HashMap<Long, CartDTO> cart) {
		int totalPrice = 0;
		for (Map.Entry<Long, CartDTO> itemCard : cart.entrySet()) {
			totalPrice += itemCard.getValue().getTotalPrice();
		}
		return totalPrice;
	}

	@Override
	@Transactional
	public List<DetailCartEntity> transferToDB(HashMap<Long, CartDTO> cart, Long user_id) {
		List<DetailCartEntity> detailCartEntities = new ArrayList<>();
		for (Map.Entry<Long, CartDTO> itemCard : cart.entrySet()) {
			Long book_id = itemCard.getKey();
			int quantity = itemCard.getValue().getQuantity();

			CartEntity cartEntity = cartRepository.findOneByUserId(user_id);
			DetailCartEntity detailCartEntity = detailCartRepository.findOneByCartIdAndBookId(cartEntity.getId(),
					book_id);
			if (detailCartEntity != null) {
				detailCartEntity.setQuantity(detailCartEntity.getQuantity());
				detailCartRepository.save(detailCartEntity);
			} else {
				DetailCartEntity newdetailCartEntity = new DetailCartEntity();
				newdetailCartEntity.setCart(cartEntity);
				newdetailCartEntity.setBook(bookRepository.findOne(book_id));
				newdetailCartEntity.setQuantity(quantity);

				detailCartRepository.save(newdetailCartEntity);
			}
		}
		return detailCartEntities;
	}

	@Override
	public void checkUserCart(Long userId) {
		CartEntity cartEntity = cartRepository.findOneByUserId(userId);
		UserEntity userEntity = userRepository.findOne(userId);
		if (cartEntity == null) {
			CartEntity newCart = new CartEntity();
			newCart.setUser(userEntity);
			cartRepository.save(newCart);
		}
	}

	@Override
	public HashMap<Long, CartDTO> findAll(HashMap<Long, CartDTO> cart, Long userId) {

		CartEntity cartEntity = cartRepository.findOneByUserId(userId);
		if(!ObjectUtils.isEmpty(cartEntity)) {
			Long cartId = cartEntity.getId();
			List<DetailCartEntity> entities = detailCartRepository.findByCartId(cartId);
			if(!CollectionUtils.isEmpty(entities)) {
				
				for (DetailCartEntity entity : entities) {
					CartDTO itemCart = new CartDTO();
					BookEntity bookEntity = bookRepository.findOne(entity.getBook().getId());
					BookDTO bookDTO = bookConverter.toDTO(bookEntity);
					itemCart.setBook(bookDTO);
					itemCart.setQuantity(entity.getQuantity());
					itemCart.setTotalPrice(itemCart.getQuantity() * bookDTO.getPrice());

					cart.put(bookDTO.getId(), itemCart);
				}
			}
		}
		
		
		
		
		
		return cart;
	}

	@Override
	@Transactional
	public CartDTO editCart(long id, int quantity, Principal user) {
		Long userId = SecurityUtils.getPrincipal().getId();
		CartEntity cartEntity = cartRepository.findOneByUserId(userId);
		DetailCartEntity detailCartEntity = detailCartRepository.findOneByCartIdAndBookId(cartEntity.getId(), id);
		detailCartEntity.setQuantity(quantity);
		detailCartRepository.save(detailCartEntity);
		return null;
	}

	@Override
	public HashMap<Long, CartDTO> findAllbyListId(HashMap<Long, CartDTO> cart, Long user_id,List<Long> ids) {
		cart = new HashMap<>();
		CartEntity cartEntity = cartRepository.findOneByUserId(user_id);
		for (Long id : ids) {
			CartDTO itemCart = new CartDTO();
			BookEntity bookEntity = bookRepository.findOne(id);
			BookDTO bookDTO = bookConverter.toDTO(bookEntity);
			DetailCartEntity entity = detailCartRepository.findOneByCartIdAndBookId(cartEntity.getId(), id);

			itemCart.setBook(bookDTO);
			itemCart.setQuantity(entity.getQuantity());
			itemCart.setTotalPrice(itemCart.getQuantity() * bookDTO.getPrice());
			cart.put(id, itemCart);
		}
		return cart;
	}
	
	@Override
	public HashMap<Long, CartDTO> findAllbyListId(HashMap<Long, CartDTO> cart,  List<Long> ids) {
		HashMap<Long, CartDTO> cartCheckOut = new HashMap<>();
		for (Long id : ids) {
			CartDTO itemCart = new CartDTO();
			BookEntity bookEntity = bookRepository.findOne(id);
			BookDTO bookDTO = bookConverter.toDTO(bookEntity);
			
			if(bookDTO != null && cart.containsKey(id)) {
				itemCart = cart.get(id);
				itemCart.setQuantity(itemCart.getQuantity());
				itemCart.setTotalPrice(itemCart.getQuantity() * itemCart.getBook().getPrice());		
			}
			cartCheckOut.put(id, itemCart);
		}
		return cartCheckOut;
	}

	@Override
	@Transactional
	public void deleteDetailCart(HashMap<Long, CartDTO> cart, Long userId) {		
		CartEntity cartEntity = cartRepository.findOneByUserId(userId);
		Long cartId = cartEntity.getId();

		for (Map.Entry<Long, CartDTO> itemCard : cart.entrySet()) {
			Long bookId = itemCard.getKey();
			detailCartRepository.deleteByCartIdAndBookId(cartId, bookId);
			if(detailCartRepository.findOneByCartIdAndBookId(cartId, bookId) != null) {
				System.out.println("Xoa that bai" + cartId + "--"+bookId);
			}	
		}
	}
	
	
	@Override
	public HashMap<Long, CartDTO> deleteDetailCart(HashMap<Long, CartDTO> cart,HashMap<Long, CartDTO> oldCart) {
		List<Long> idCart= new ArrayList<>();
		cart.forEach((t, u) -> idCart.add(t));
		for(Long id : idCart) {
			if(oldCart.containsKey(id)) {
				oldCart.remove(id);
			}
		}
		return oldCart;
	}
	
	

	@Override
	public Map<Long, CartDTO> findAllByAccount(Long user_id) {
		HashMap<Long, CartDTO> cart = new HashMap<>();
		CartEntity cartEntity = cartRepository.findOneByUserId(user_id);
		Long cartId = cartEntity.getId();
		List<DetailCartEntity> entities = detailCartRepository.findByCartId(cartId);
		if(!entities.isEmpty()) {
			for (DetailCartEntity entity : entities) {
				CartDTO itemCart = new CartDTO();
				BookEntity bookEntity = bookRepository.findOne(entity.getBook().getId());
				BookDTO bookDTO = bookConverter.toDTO(bookEntity);
				itemCart.setBook(bookDTO);
				itemCart.setQuantity(entity.getQuantity());
				itemCart.setTotalPrice(itemCart.getQuantity() * bookDTO.getPrice());

				cart.put(bookDTO.getId(), itemCart);
			}
		}
		
		return cart;
	}
	

	@Override
	@Transactional
	public void deleteDetailCartInAdmin(List<Long> idBook, String email) {	
		UserEntity user = userRepository.findOneByEmail(email);
		CartEntity cartEntity = cartRepository.findOneByUserId(user.getId());
		Long cartId = cartEntity.getId();

		for (Long id : idBook) {
			DetailCartEntity detailCartEntity = detailCartRepository.findOneByCartIdAndBookId(cartId, id);
			if(!ObjectUtils.isEmpty(detailCartEntity)) {
				detailCartRepository.deleteByCartIdAndBookId(cartId, id);
			}
		}
		
		
	}

}
