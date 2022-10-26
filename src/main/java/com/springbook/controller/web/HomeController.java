//package com.springbook.controller.web;
//
//import java.security.Principal;
//import java.util.HashMap;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.springbook.dto.book.BookDTO;
//import com.springbook.dto.cart.CartDTO;
//import com.springbook.service.IBookService;
//import com.springbook.service.ICartService;
//import com.springbook.service.ICategoryService;
//import com.springbook.utils.SecurityUtils;
//
//@Controller(value = "homeControllerInWeb")
//public class HomeController {
//	@Autowired
//	private IBookService bookService;
//	@Autowired
//	private ICategoryService categoryService;
//	
//	@Autowired
//	private ICartService cartService;
//
//	@RequestMapping(value = "/home", method = RequestMethod.GET)
//	public ModelAndView BookList(HttpSession session,@RequestParam(name = "name", required = false) String name, Principal user) {
//		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>)session.getAttribute("cart");
//		BookDTO bookDTO = new BookDTO();
//		BookDTO bookDTOs = new BookDTO();
//		ModelAndView mav = new ModelAndView("web/books/category_book.jsp");
//		bookDTO.setListResult(bookService.findAllWebHome());
//		bookDTOs.setListResult(bookService.findByCodeTagOrderByModifiedDateDescLimit6());
//		
//		mav.addObject("model", bookDTO);
//		mav.addObject("modelnewrelease", bookDTOs);
//		
//		
//		if(cart== null) {
//			cart = new HashMap<>();
//		}
//		
//		if(user!= null) {
//			Long user_id = SecurityUtils.getPrincipal().getId();
//			if(cart!=null) {
//				
//				
//				cartService.transferToDB(cart, user_id);
//				session.removeAttribute("cart");
//			}else {
//				cartService.checkUserCart(user_id);
//				session.removeAttribute("cart");
//			}
//			
//		}
//		return mav;
//	}
//	
//	
//	@RequestMapping(value ="/*")
//	public void test(Model model) {
//		model.addAttribute("category_menu", categoryService.findAll());
//	}
//	
//}
