package com.springbook.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.user.ShipAddressDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.service.IOrderService;
import com.springbook.service.IRoleService;
import com.springbook.service.IUserService;
import com.springbook.utils.MessageUtils;
import com.springbook.validator.UserValidator;

@Controller(value = "userControllerInAdmin")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private IOrderService orderService;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("web/login.jsp");
		if (request.getParameter("message") != null) {
			if (request.getParameter("message").equals("success_sign_in")) {
				mav.addObject("message", "Tài khoản đã đăng kí thành công");
			}
		}
		if (request.getParameter("message") != null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/sign-in", method = RequestMethod.GET)
	public ModelAndView signInPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("web/sign_in.jsp");
		mav.addObject("model", new UserDTO());
		if (request.getParameter("message") != null) {
			if (request.getParameter("message").equals("account_exist")) {
				mav.addObject("message", "Tài khoản đã tồn tại");
				
			}
		}
		return mav;
	}
	
	@RequestMapping(value = "/sign-in", method = RequestMethod.POST)
	public String signInPage(@ModelAttribute(value = "model") UserDTO user,BindingResult result) {
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "web/sign_in.jsp";
		}else {
			userService.insert(user);
			return "redirect:/login?message=success_sign_in";
		}
		
	}

	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		return new ModelAndView("redirect:/login?accessDenied");
	}

	@RequestMapping(value = "/admin/user/list", method = RequestMethod.GET)
	public ModelAndView getlist(@RequestParam(name = "name", required = false) Integer page,
			@RequestParam(name = "name", required = false) String name,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/user/list.jsp");
		UserDTO userDTO = new UserDTO();
		if(page!= null) {
			userDTO.setPage(page);
		}else {
			userDTO.setPage(1);
		}
		userDTO.setLimit(10);

		Pageable pageable = new PageRequest(userDTO.getPage() - 1, userDTO.getLimit());

		if (StringUtils.hasText(name)) {
			userDTO.setListResult(userService.findByNameTagContaining(name, pageable));

			userDTO.setTotalItem(userDTO.getListResult().size());
			if (userDTO.getTotalItem() != 0) {
				userDTO.setTotalPage((int) Math.ceil((double) userDTO.getTotalItem() / userDTO.getLimit()));
			} else {
				userDTO.setListResult(userService.findAll(pageable));

				userDTO.setTotalItem(userService.getTotalItems());
				userDTO.setTotalPage((int) Math.ceil((double) userDTO.getTotalItem() / userDTO.getLimit()));
				mav.addObject("mess", "Khong ton tai");
			}

		} else {

			userDTO.setListResult(userService.findAll(pageable));

			userDTO.setTotalItem(userService.getTotalItems());
			userDTO.setTotalPage((int) Math.ceil((double) userDTO.getTotalItem() / userDTO.getLimit()));
		}
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		mav.addObject("model", userDTO);
		return mav;
	}

	@RequestMapping(value = "/admin/user/create", method = RequestMethod.GET)
	public ModelAndView userCreate() {
		String path = servletContext.getRealPath("/");
		File file = new File(path + "/template/local1.json");
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(file.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		ModelAndView mav = new ModelAndView("admin/user/create.jsp");
		mav.addObject("role", roleService.findAll());
		mav.addObject("model", new UserDTO());
		mav.addObject("jsonn", result);
		return mav;
	}

	@RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
	public ModelAndView userCreate(@Valid @ModelAttribute("model") UserDTO userDTO,BindingResult result ) {
		userValidator.validate(userDTO, result);
		if (result.hasErrors()) {
			String path = servletContext.getRealPath("/");
			File file = new File(path + "/template/local1.json");
			String jsonn = null;
			try {
				jsonn = new String(Files.readAllBytes(Paths.get(file.toString())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			ModelAndView mav = new ModelAndView("admin/user/create.jsp");
			mav.addObject("role", roleService.findAll());
			mav.addObject("jsonn", jsonn);
			return mav;
		} else {
			userService.adminInsert(userDTO);
			return new ModelAndView("redirect:/admin/user/list?name=&page=1&limit=10&message=success_save");
		}
	}

	@RequestMapping(value = "/admin/user/edit/{id}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/user/edit.jsp");

		UserDTO userDTO = userService.findOneById(id);

		Pageable pageable = new PageRequest(0, 10);
		OrderDTO dto = orderService.findByUserEmail(userDTO.getUserName(), pageable);
		dto.setPage(1);
		dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / 10));
		mav.addObject("model", userDTO);
		mav.addObject("role", roleService.findAll());
		mav.addObject("order", dto);

		return mav;
	}

	@RequestMapping(value = "/sub/user/order/{id}", method = RequestMethod.GET)
	public ModelAndView getPageOrderInUser(@PathVariable(name = "id") Long id,
			@RequestParam(name = "page") Integer page) {
		ModelAndView mav = new ModelAndView("admin/user/subViewOrder.jsp");
		UserDTO userDTO = userService.findOneById(id);
		OrderDTO dto = new OrderDTO();
		dto.setPage(page);
		dto.setLimit(10);

		Pageable pageable = new PageRequest(dto.getPage() - 1, dto.getLimit());
		dto = orderService.findByUserEmail(userDTO.getUserName(), pageable);
		dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / dto.getLimit()));

		mav.addObject("order", dto);
		mav.addObject("userId", userDTO.getId());

		return mav;
	}

	@RequestMapping(value = "/admin/user/edit/{id}", method = RequestMethod.POST)
	public ModelAndView updateCategory(@Valid @ModelAttribute("model") UserDTO userDTO, BindingResult result,
			Map<String, Object> model, @PathVariable(name = "id") Long id) {
		userValidator.validate(userDTO, result);
		if (result.hasErrors()) {
			System.out.println(userDTO.getGetTab());
			ModelAndView mav = new ModelAndView("admin/user/edit.jsp");
			
			userDTO = userService.convertValidateUser(userDTO);
			Pageable pageable = new PageRequest(0, 10);
			OrderDTO dto = orderService.findByUserEmail(userDTO.getUserName(), pageable);
			dto.setPage(1);
			dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / 10));
			
			mav.addObject("model", userDTO);
			mav.addObject("role", roleService.findAll());
			mav.addObject("order", dto);

			return mav;
		} else {
			userDTO.setFullName(userDTO.getFullNameValidator());
			userService.update(userDTO);
			return new ModelAndView("redirect:/admin/user/list?name=&page=1&limit=10&message=success_update");
		}
	}

	// address user
	@GetMapping(value = "/admin/user/{id}/address/create")
	public String addressCreate(@PathVariable(name = "id") Long id, Model model) {
		String path = servletContext.getRealPath("/");
		File file = new File(path + "/template/local1.json");
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(file.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("jsonn", result);
		model.addAttribute("model", userService.findOneById(id));
		return "admin/user/address/createaddress.jsp";
	}

	@PostMapping(value = "/admin/user/{id}/address/create")
	public String addressCreate(@PathVariable(name = "id") Long id, @ModelAttribute(name = "model") UserDTO user) {

		userService.saveShipAddress(id, user.getAddress());
		return "redirect:/admin/user/edit/" + id + "?message=create_success";
	}

	@GetMapping(value = "/admin/user/{id}/address/edit/{idaddress}")
	public String addressEdit(@PathVariable(name = "id") Long id, @PathVariable(name = "idaddress") Long idaddress,
			Model model) {
		String path = servletContext.getRealPath("/");
		File file = new File(path + "/template/local1.json");
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(file.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("jsonn", result);

		ShipAddressDTO dto = userService.findOneAddress(idaddress);
		model.addAttribute("addressdto", dto);
		model.addAttribute("model", userService.findOneById(id));
		return "admin/user/address/editaddress.jsp";
	}

	@PostMapping(value = "/admin/user/{id}/address/edit/{idaddress}")
	public String addressEdit(@PathVariable(name = "id") Long id, @PathVariable(name = "idaddress") Long idaddress,
			@ModelAttribute(name = "model") UserDTO user) {
		userService.saveShipAddress(id, user.getAddress());
		return "redirect:/admin/user/edit/" + id + "?message=update_success";
	}

	@GetMapping(value = "/admin/user/{id}/address/delete/{idaddress}")
	public String addressDelete(@PathVariable(name = "id") Long id, @PathVariable(name = "idaddress") Long idaddress) {
		userService.deleteShipAddress(idaddress);
		return "redirect:/admin/user/edit/" + id + "?message=delete_success";
	}
	
	@GetMapping(value = "/admin/user/delete/{id}")
	public ModelAndView addressDelete(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		try {
			userService.delete(id);
		} catch (ConstraintViolationException | SQLIntegrityConstraintViolationException
				| DataIntegrityViolationException e) {

			ModelAndView mav = getlist(1, null, request);
			mav.addObject("message", "Xóa không thành công");
			mav.addObject("alert", "danger");
			return mav;
		}
		return new ModelAndView("redirect:/admin/user/list?&message=success_delete");
	}

}
