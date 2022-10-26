package com.springbook.controller.web;

import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springbook.dto.cart.CartDTO;
import com.springbook.service.IBookService;
import com.springbook.service.ICartService;
import com.springbook.utils.SecurityUtils;

@Controller(value = "cartControllerInWeb")
@RequiredArgsConstructor
public class CartController {
	
	private final ICartService cartService;
	private final IBookService bookService;
	
	@GetMapping(value = "/cart/add")
	@ResponseBody
	public String cartCreate(HttpSession session,
						@RequestParam(name = "id")Long id,
						HttpServletRequest request, Principal user) {
		
		@SuppressWarnings("unchecked")
		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>)session.getAttribute("cart");
		if(cart == null) {
			cart = new HashMap<>();
		}
		//kiểm tra người dùng đăng nhập
		if(user == null) {
			cart = cartService.addCart(id, cart);
			session.setAttribute("cart", cart);
		}else {
			session.removeAttribute("cart");
			cartService.addCart(id,user);
		}
		return "redirect:"+request.getHeader("Referer");
	}

	//lấy danh sách các sản phẩm có trong giỏ hàng
	@RequestMapping(value = "/home/cart/list", method = RequestMethod.GET)
	public ModelAndView cartList(HttpSession session,						  
							  HttpServletRequest request,Principal user) {
		ModelAndView mav = new ModelAndView("web/carts/list.jsp");
		@SuppressWarnings("unchecked")
		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>)session.getAttribute("cart");
		if(cart == null) {
			cart = new HashMap<>();
		}

		//kiểm tra người dùng đăng nhập
		if(user!=null) {
			session.removeAttribute("cart");
			Long userId = SecurityUtils.getPrincipal().getId();
			cart= cartService.findAll(cart, userId);
			mav.addObject("cart",cart);
			mav.addObject("TotalQuantitycart",cartService.totalQuantity(cart));
			mav.addObject("TotalPricecart",cartService.totalPrice(cart));
		}else {
			session.setAttribute("cart", cart);
			session.setAttribute("TotalQuantitycart", cartService.totalQuantity(cart));
			session.setAttribute("TotalPricecart", cartService.totalPrice(cart));
		}
		return mav;
	}
	//xử lý request cập nhập giỏ hàng
	@RequestMapping(value = "/cart/edit", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView editCart(HttpSession session,
			  					@RequestParam(name = "id")Long id,
			  					@RequestParam(name = "quantity")int quantity,
			  					HttpServletRequest request,
			  					Principal user) {
		ModelAndView mav = new ModelAndView("web/carts/list.jsp");
		@SuppressWarnings("unchecked")
		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>)session.getAttribute("cart");
		if(cart == null) {
			cart = new HashMap<>();
		}
		//kiểm tra người dùng đăng nhập
		if(user !=null) {
			session.removeAttribute("cart");
			cartService.editCart(id, quantity, user);
//			mav.addObject("TotalQuantitycart",cartService.totalQuantity(cart));
//			mav.addObject("TotalPricecart",cartService.totalPrice(cart));
		}else {
			cart = cartService.editCart(id,quantity, cart);
			session.setAttribute("cart", cart);
			session.setAttribute("TotalQuantitycart", cartService.totalQuantity(cart));
			session.setAttribute("TotalPricecart", cartService.totalPrice(cart));
		}	
		return mav;
	}
	//xử lý request xóa sản phẩm
	@RequestMapping(value = "/cart/delete", method = RequestMethod.GET)
	public String deleteCart(HttpSession session,
			  					  @RequestParam(name = "id")Long id,
			  					  HttpServletRequest request, Principal user) {
		@SuppressWarnings("unchecked")
		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>)session.getAttribute("cart");
		if(cart == null) {
			cart = new HashMap<>();
		}
		//kiểm tra người dùng đăng nhập
		if(user != null) {
			session.removeAttribute("cart");
			cartService.deleCart(id, user);	
			
		}else {
			cart = cartService.deleCart(id, cart);
			session.setAttribute("cart", cart);
			session.setAttribute("TotalQuantitycart", cartService.totalQuantity(cart));
			session.setAttribute("TotalPricecart", cartService.totalPrice(cart));
		}
		
		return "redirect:/home/cart/list";
	}

}
