package com.springbook.controller.web;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springbook.dto.user.ContactDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.service.IUserService;
import com.springbook.service.impl.ContactService;
import com.springbook.utils.MessageUtils;
import com.springbook.utils.SecurityUtils;
import com.springbook.validator.ContactValidator;
@Controller(value = "contactControllerInWeb")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private MessageUtils messageUtils;
	
	@Autowired
	private ContactValidator contactValidator;
	
	@InitBinder("result")
	private void innitBinder(WebDataBinder binder) {
	}
	
	@RequestMapping(value = "/home/contact", method = RequestMethod.GET)
	public ModelAndView contactUser(HttpServletRequest request, Principal user) {
		ModelAndView mav = new ModelAndView("web/contact/create.jsp");
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		if(user!= null) {
			Long userId = SecurityUtils.getPrincipal().getId();
			UserDTO userDTO = userService.findOneById(userId);
			mav.addObject("user", userDTO);
		}
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		mav.addObject("model", new ContactDTO());
		return mav;
	}	
	
	@RequestMapping(value = "/home/contact", method = RequestMethod.POST)
	public String addContactUser(@Valid @ModelAttribute(name = "model") ContactDTO contact,BindingResult result, HttpServletRequest request) {
		contactValidator.validate(contact, result);
		if(result.hasErrors()) {
			
			return "web/contact/create.jsp";
		}else {
			contactService.insert(contact);
			return "redirect:/home/contact?message=sent_success";
		}
		
	}	
}
