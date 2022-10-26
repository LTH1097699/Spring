package com.springbook.controller.admin;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springbook.dto.user.ContactDTO;
import com.springbook.service.impl.ContactService;
import com.springbook.utils.MessageUtils;

@Controller(value = "contactControllerInAdmin")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private MessageUtils messageUtils;
	
	@RequestMapping(value = "/admin/contact/list", method = RequestMethod.GET)
	public ModelAndView contactList(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "name", required = false) String name,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/user/contact/list.jsp");
		ContactDTO contactDTO = new ContactDTO();
		if (page != null) {
			contactDTO.setPage(page);
		} else {
			contactDTO.setPage(1);
		}
		contactDTO.setLimit(10);
		Pageable pageable = new PageRequest(contactDTO.getPage() - 1, contactDTO.getLimit());

		if (StringUtils.hasText(name)) {
			contactDTO.setListResult(contactService.search(name,pageable));
			contactDTO.setTotalItem(contactService.totalItemSearch(name));
			if (contactDTO.getTotalItem() != 0) {
				contactDTO.setTotalPage((int) Math.ceil((double) contactDTO.getTotalItem() / contactDTO.getLimit()));
			} else {
				contactDTO.setPage(1);
				contactDTO.setTotalPage(1);
				mav.addObject("mess", "Kết quả không tìm thấy");
			}
		} else {
			contactDTO.setListResult(contactService.findAll(pageable));
			contactDTO.setTotalItem(contactService.getTotalItems());
			if(contactDTO.getTotalItem() != 0) {
				contactDTO.setTotalPage((int) Math.ceil((double) contactDTO.getTotalItem() / contactDTO.getLimit()));
			}else {
				mav.addObject("mess", "Chưa có dữ liệu");
				contactDTO.setPage(1);
				contactDTO.setTotalPage(1);
			}	
		}
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		mav.addObject("model", contactDTO);
		return mav;
	}
	
	@RequestMapping(value = "/admin/contact/delete", method = RequestMethod.GET)
	public @ResponseBody ModelAndView contactDelete(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		try {
			contactService.delete(id);
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {

			ModelAndView mav = contactList(1, null, request);
			mav.addObject("message", "Xóa không thành công");
			mav.addObject("alert", "danger"); 
			return mav;
		}
		return new ModelAndView("redirect:/admin/contact/list?message=success_delete");

	}
}
