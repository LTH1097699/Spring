package com.springbook.controller.admin;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springbook.dto.book.PublisherDTO;
import com.springbook.service.IPublisherService;
import com.springbook.utils.MessageUtils;
import com.springbook.validator.PublisherValidator;

@Controller(value = "publisherInAdmin")
public class PublisherController {
	@Autowired
	private IPublisherService publisherService;
	
	@Autowired
	private PublisherValidator publisherValidator;
	
	@Autowired
	private MessageUtils messageUtils;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(publisherValidator);
	}
	
	@RequestMapping(value = "admin/publisher/list", method= RequestMethod.GET)
	public ModelAndView publisherList(@RequestParam(name="page", required = false) Integer page,
								@RequestParam(name="name", required = false) String name,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/publishers/list.jsp");
		PublisherDTO publisherDTO = new PublisherDTO();
		if(page != null) {
			publisherDTO.setPage(page);
		}else {
			publisherDTO.setPage(1);
		}
		publisherDTO.setLimit(10);

		Pageable pageable = new PageRequest(publisherDTO.getPage()-1, publisherDTO.getLimit());

		if(StringUtils.hasText(name)) {
			publisherDTO.setListResult(publisherService.search(name,pageable));
			
			publisherDTO.setTotalItem(publisherService.totalitemSearch(name));
			if(publisherDTO.getTotalItem() != 0) {
				publisherDTO.setTotalPage((int) Math.ceil((double) publisherDTO.getTotalItem() / publisherDTO.getLimit()));
			}else {
				publisherDTO.setPage(1);
				publisherDTO.setTotalPage(1);
				mav.addObject("mess","Kết quả không tìm thấy");
			}	
		}else {
			publisherDTO.setListResult(publisherService.findAll(pageable));
			publisherDTO.setTotalItem(publisherService.getTotalItems());
			if(publisherDTO.getTotalItem() != 0) {
				publisherDTO.setTotalPage((int) Math.ceil((double) publisherDTO.getTotalItem() / publisherDTO.getLimit()));
			}else {
				publisherDTO.setPage(1);
				publisherDTO.setTotalPage(1);
				mav.addObject("mess", "Chưa có dữ liệu");
			}	
		}
		
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		mav.addObject("model",publisherDTO);
		return mav;
	}
	
	@RequestMapping(value = "/admin/publisher/create", method = RequestMethod.GET)
	public ModelAndView publisherCreate() {
		ModelAndView mav = new ModelAndView("admin/publishers/create.jsp");
		mav.addObject("model",new PublisherDTO());
		return mav;
	}
	
	@RequestMapping(value = "/admin/publisher/create", method = RequestMethod.POST)
	public ModelAndView publisherCreate(@Valid @ModelAttribute("model") PublisherDTO publisherDTO,BindingResult result, Errors errors) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("admin/publishers/create.jsp");
			return mav;
		} else {
			publisherService.insert(publisherDTO);
			return new ModelAndView("redirect:/admin/publisher/list?message=success_save");
		}
	}
	
	@RequestMapping(value = "/admin/publisher/edit/{id}", method = RequestMethod.GET)
	public ModelAndView publisherEdit(@PathVariable(name = "id") Long id,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/publishers/edit.jsp");
		PublisherDTO publisherDTO = publisherService.findById(id);
		mav.addObject("model",publisherDTO);
		return mav;
	}
	
	@RequestMapping(value = "/admin/publisher/edit/{id}", method = RequestMethod.POST)
	public ModelAndView publisherEdit(@Valid @ModelAttribute("model") PublisherDTO publisherDTO,BindingResult result, Errors errors,
			@PathVariable(name = "id") Long id) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("admin/publishers/edit.jsp");
			return mav;
		} else {
			publisherService.update(publisherDTO);
			return new ModelAndView("redirect:/admin/publisher/list?message=success_update");
		}
	}
	
	@RequestMapping(value = "/admin/publisher/delete", method = RequestMethod.GET)
	public @ResponseBody ModelAndView publisherDelete(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		try {
			publisherService.delete(id);
		} catch (ConstraintViolationException | SQLIntegrityConstraintViolationException
				| DataIntegrityViolationException e) {
			ModelAndView mav = publisherList(1,null,null);
			mav.addObject("message", "Xóa không thành công");
			mav.addObject("alert", "danger"); 
			return mav;
		}
		return new ModelAndView("redirect:/admin/publisher/list?message=success_delete");
	}

}
