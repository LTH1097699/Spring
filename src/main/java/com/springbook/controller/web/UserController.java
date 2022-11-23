package com.springbook.controller.web;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.user.ShipAddressDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.service.IOrderService;
import com.springbook.service.IUserService;
import com.springbook.utils.MessageUtils;
import com.springbook.utils.SecurityUtils;
import com.springbook.validator.UserValidator;

@Controller(value = "userControllerInWeb")
public class UserController {
	@Autowired 
	private IUserService userService;
	
	@Autowired 
	private IOrderService orderService;
	
	@Autowired
	private UserValidator userValidator;

	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private MessageUtils messageUtils;
	
	@InitBinder("result")
	private void initUserBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@GetMapping(value = "/home/account")
	public String getOneUser(Model model) {
		Long id = SecurityUtils.getPrincipal().getId();
		UserDTO user = userService.findOneById(id);
		model.addAttribute("model",user);
		model.addAttribute("order", orderService.findByCreateByDESCLimit3(user.getUserName()));
		return "web/user/home.jsp";
	}
	
	@GetMapping(value = "/home/account/order")
	public String orderList(Model model,@RequestParam(name = "page",required = false) Integer page) {
		String email = SecurityUtils.getPrincipal().getUsername();
		OrderDTO order = new OrderDTO();
		if(page!=null) {
			order.setPage(page);
		}else {
			order.setPage(1);
		}
		order.setLimit(2);
		Pageable pageable = new PageRequest(order.getPage()-1,order.getLimit());
		order = orderService.findByUserEmail(email,pageable);
		order.setTotalPage((int) Math.ceil((double) order.getTotalItem()/order.getLimit()));
		model.addAttribute("order",order );
		return "web/user/userorder.jsp";
	}
	
	
	@GetMapping(value = "/home/account/order/{id}")
	public String orderDetail(@PathVariable(name = "id") Long orderId,Model model) {
		Long id = SecurityUtils.getPrincipal().getId();
		
		model.addAttribute("user",userService.findOneById(id));

		model.addAttribute("order",orderService.findOneById(orderId));
		return "web/user/orderdetail.jsp";
	}
	//address
	@GetMapping(value = "/home/account/address")
	public String addressList(Model model,HttpServletRequest request) {
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			model.addAttribute("message", message.get("message"));
			model.addAttribute("alert", message.get("alert"));
		}
		Long userid = SecurityUtils.getPrincipal().getId();
		model.addAttribute("model", userService.findAllShipAdress(userid));
		return "web/user/address/useraddress.jsp";
	}
	@GetMapping(value = "/home/account/address/create")
	public String addressCreate(Model model) {
		String path = servletContext.getRealPath("/");
		File file = new File(path+"/template/local1.json");
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(file.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("jsonn",result);
		model.addAttribute("model", new ShipAddressDTO());
		return "web/user/address/createaddress.jsp";
	}
	@PostMapping(value = "/home/account/address/create")
	public String addressCreate(@Valid @ModelAttribute(name = "model") ShipAddressDTO address, BindingResult adress, Model model) {
		if(adress.hasErrors()) {
			String path = servletContext.getRealPath("/");
			File file = new File(path+"/template/local1.json");
			String result = null;
			try {
				result = new String(Files.readAllBytes(Paths.get(file.toString())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addAttribute("jsonn",result);
			model.addAttribute("model", address);
			return "web/user/address/createaddress.jsp";
		}else {
			Long userid = SecurityUtils.getPrincipal().getId();
			userService.saveShipAddress(userid, address);	
			return "redirect:/home/account/address?message=create_success";
		}
	}
	@GetMapping(value = "/home/account/address/edit/{id}")
	public String addressEdit(@PathVariable(name = "id") Long id,Model model) {
		ShipAddressDTO dto = userService.findOneAddress(id);
		model.addAttribute("model", dto);
		return "web/user/address/editaddress.jsp";
	}
	@PostMapping(value = "/home/account/address/edit/{id}")
	public String addressEdit(@PathVariable(name = "id") Long id,@ModelAttribute(name = "model") ShipAddressDTO address) {
		Long userid = SecurityUtils.getPrincipal().getId();
		userService.saveShipAddress(userid, address);	
		return "redirect:/home/account/address?message=success_update";
	}
	@GetMapping(value = "/home/account/address/delete/{id}")
	public String addressDelete(@PathVariable(name = "id") Long id) {
		userService.deleteShipAddress(id);
		return "redirect:/home/account/address?message=success_delete";
	}
	
	//account
	@GetMapping(value = "/home/account/details")
	public String acountDetail(Model model, HttpServletRequest request) {
		Long id = SecurityUtils.getPrincipal().getId();
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			model.addAttribute("message", message.get("message"));
			model.addAttribute("alert", message.get("alert"));
		}
		model.addAttribute("model",userService.findOneById(id));
		return "web/user/useraccount.jsp";
	}
	@PostMapping(value = "/home/account/details")
	public String acountDetail(@Valid @ModelAttribute(name = "model") UserDTO user,BindingResult result,Model model,Error error) {
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			model.addAttribute("model",user);
			return "web/user/useraccount.jsp";
		}else {
			user.setFullName(user.getFullNameValidator());
			userService.update(user);
			return "redirect:/home/account/details?message=success_update";
		}
		
	}
	


}
