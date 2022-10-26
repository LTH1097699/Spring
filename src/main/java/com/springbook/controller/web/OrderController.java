package com.springbook.controller.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.hpsf.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springbook.dto.cart.CartDTO;
import com.springbook.dto.order.OrderDTO;
import com.springbook.service.IBookService;
import com.springbook.service.ICartService;
import com.springbook.service.IOrderService;
import com.springbook.service.IUserService;
import com.springbook.utils.SecurityUtils;
import com.springbook.validator.OrderValidator;

@Controller(value = "orderControllerInWeb")
public class OrderController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ICartService cartService;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IBookService bookService;
	
	@Autowired
	private OrderValidator orderValidator;

	private List<Long> id;
	
	@InitBinder("result")
	private void initBinder(WebDataBinder dataBinder) {
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	@ResponseBody 
	public void getIdBookCheckOut(Principal user,HttpSession session, @RequestBody Map<String, String> myMap ) {
		List<Long> ids = new ArrayList<>();
	
		if(user !=null) {
			session.removeAttribute("cart");
			for(Map.Entry<String, String> m: myMap.entrySet()) {
				ids.add(Long.valueOf(m.getKey()));
				cartService.editCart(Long.valueOf(m.getKey()), Integer.parseInt(m.getValue()), user);
			}
		}else {
			@SuppressWarnings("unchecked")
			HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>) session.getAttribute("cart");
			HashMap<Long, CartDTO> oldCart = cart;
			for(Map.Entry<String, String> m: myMap.entrySet()) {
				ids.add(Long.valueOf(m.getKey()));
				cart = cartService.editCart(Long.valueOf(m.getKey()),Integer.parseInt(m.getValue()), cart);
				session.setAttribute("cart", cart);
				session.setAttribute("oldCart", oldCart);
				session.setAttribute("TotalQuantitycart", cartService.totalQuantity(cart));
				session.setAttribute("TotalPricecart", cartService.totalPrice(cart));
			}
			
		}	
		id = ids;
	}
	
	@RequestMapping(value = "/home/order", method = RequestMethod.GET)
	public ModelAndView checkOut(HttpSession session, HttpServletRequest request, Principal user) {
		@SuppressWarnings("unchecked")
		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}
		String path = servletContext.getRealPath("/");
//		URL resource = OrderController.class.getClassLoader().getResource("local1.json");
		File file = new File(path+"/template/local1.json");
		String result = null;

		try {
			result = new String(Files.readAllBytes(Paths.get(file.toString())));
		} catch (IOException e) {

			e.printStackTrace();
		}
		OrderDTO orderDTO = new OrderDTO();
		ModelAndView mav = new ModelAndView("web/carts/checkout.jsp");
		mav.addObject("jsonn", result);
		if (user != null) {
			long userId = SecurityUtils.getPrincipal().getId();
			orderDTO = orderService.getUserInfo(userId);

			cart = cartService.findAllbyListId(cart, userId, id);
			mav.addObject("order", orderDTO);
			mav.addObject("cart", cart);
			mav.addObject("totalPrice", cartService.totalPrice(cart));

		} else {
			HashMap<Long, CartDTO> oldCard = cart;
			cart = cartService.findAllbyListId(cart, id);
			mav.addObject("order", orderDTO);
			session.setAttribute("cart", cart);
			session.setAttribute("oldCart", oldCard);
			session.setAttribute("totalPrice", cartService.totalPrice(cart));
		}
		return mav;
	}

	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public ModelAndView addOrder(HttpSession session, HttpServletRequest request, Principal user,
			@Valid @ModelAttribute("order") OrderDTO order,BindingResult result) {
		orderValidator.validate(order, result);
		@SuppressWarnings("unchecked")
		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}
		
		if(result.hasErrors()) {
			String path = servletContext.getRealPath("/");
			File file = new File(path+"/template/local1.json");
			String resultt = null;
			try {
				resultt = new String(Files.readAllBytes(Paths.get(file.toString())));
			} catch (IOException e) {e.printStackTrace();}
			ModelAndView mav = new ModelAndView("web/carts/checkout.jsp");
			mav.addObject("jsonn", resultt);
			if (user != null) {
				long userId = SecurityUtils.getPrincipal().getId();
				cart = cartService.findAllbyListId(cart, userId, id);
				mav.addObject("order", order);
				mav.addObject("cart", cart);
				mav.addObject("totalPrice", cartService.totalPrice(cart));
			} else {
				cart = cartService.findAllbyListId(cart, id);
				mav.addObject("order", order);
				session.setAttribute("cart", cart);
				session.setAttribute("totalPrice", cartService.totalPrice(cart));
			}
			return mav;
		}else {
			if (user != null) {
				session.removeAttribute("cart");
				Long userId = SecurityUtils.getPrincipal().getId();

				if (order.getShipAddress().getId() == null) {
					userService.createShipAddress(userId, order);
				}
				// tạo mới đơn hàng với thông người dùng và thông tin địa chỉ
				OrderDTO orderDTO = orderService.addOrder(order, userId);
				// lấy danh sách book theo id
				cart = cartService.findAllbyListId(cart, userId, id);

				OrderDTO orderDTOs = orderService.addDetailOrder(orderDTO, cart);
				List<Long> bookListId = bookService.updateQuantity(null, orderDTOs.getId());
				for (Long idbook : bookListId) {
					bookService.updateQuantity(idbook, null);
				}
				cartService.deleteDetailCart(cart, userId);
			} else {
				
				//check email in of not user in system by with ask them have email and want to login
				
				// check email
				@SuppressWarnings("unchecked")
				HashMap<Long, CartDTO> oldcart = (HashMap<Long, CartDTO>) session.getAttribute("oldCart");
				OrderDTO orderDTO = orderService.addOrder(order);
				cart = cartService.findAllbyListId(cart, id);
				OrderDTO orderDTOs = orderService.addDetailOrder(orderDTO, cart);
				List<Long> bookListId = bookService.updateQuantity(null, orderDTOs.getId());
				for (Long idbook : bookListId) {
					bookService.updateQuantity(idbook, null);
				}
				cart = cartService.deleteDetailCart(cart, oldcart);
				session.setAttribute("cart", cart);
				session.removeAttribute("oldCart");
			}
			return new ModelAndView("redirect:/home/cart/list");
		}
		

	}

}
