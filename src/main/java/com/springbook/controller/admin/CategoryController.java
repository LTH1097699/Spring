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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springbook.dto.book.CategoryDTO;
import com.springbook.service.ICategoryService;
import com.springbook.utils.MessageUtils;

@Controller(value = "categoryInAdmin")
public class CategoryController {
	
	@Autowired
	private ICategoryService categoryService;
	

	@Autowired
	private MessageUtils messageUtils;
	
	//LIST
	@RequestMapping(value = "admin/category/list", method= RequestMethod.GET)
	public ModelAndView categoryList(@RequestParam(name="page", required = false) Integer page,
								
								@RequestParam(name="name", required = false) String name, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/categories/list.jsp");
		CategoryDTO categoryDTO = new CategoryDTO();
		if(page != null) {
			categoryDTO.setPage(page);
		}else {
			categoryDTO.setPage(1);
		}
		categoryDTO.setPage(page);
		categoryDTO.setLimit(10);
		
		Pageable pageable = new PageRequest(categoryDTO.getPage()-1, categoryDTO.getLimit());
		
		if(StringUtils.hasText(name)) {
			categoryDTO.setListResult(categoryService.search(name,pageable));		
			categoryDTO.setTotalItem(categoryService.totalItemSearch(name));
			if(categoryDTO.getTotalItem() != 0) {
				categoryDTO.setTotalPage((int) Math.ceil((double) categoryDTO.getTotalItem() / categoryDTO.getLimit()));
			}else {
				categoryDTO.setPage(1);
				categoryDTO.setTotalPage(1);
				mav.addObject("mess","Kết quả không tìm thấy");
			}
		}else {	
			categoryDTO.setListResult(categoryService.findAll(pageable));
			categoryDTO.setTotalItem(categoryService.getTotalItems());
			if(categoryDTO.getTotalItem() != 0) {
				categoryDTO.setTotalPage((int) Math.ceil((double) categoryDTO.getTotalItem() / categoryDTO.getLimit()));
			}else {
				categoryDTO.setPage(1);
				categoryDTO.setTotalPage(1);
				mav.addObject("mess", "Chưa có dữ liệu");
			}	
		}	
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		mav.addObject("model",categoryDTO);
		return mav;
	}
	//CREATE
	@RequestMapping(value = "/admin/category/create", method = RequestMethod.GET)
	public ModelAndView categoryCreate() {
		ModelAndView mav = new ModelAndView("admin/categories/create.jsp");
		mav.addObject("model",new CategoryDTO());
		return mav;
	}
	//SAVE
	@RequestMapping(value = "/admin/category/create", method = RequestMethod.POST)
	public ModelAndView categoryCreate(@Valid @ModelAttribute("model") CategoryDTO categoryDTO,BindingResult result, Errors errors) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("admin/categories/create.jsp");
			return mav;
		} else {
			categoryService.insert(categoryDTO);
			return new ModelAndView("redirect:/admin/category/list?&message=success_save");
		}
	}
	//EDIT
	@RequestMapping(value = "/admin/category/edit/{id}", method = RequestMethod.GET)
	public ModelAndView categoryEdit(@PathVariable(name = "id") Long id,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/categories/edit.jsp");
		CategoryDTO categoryDTO = categoryService.findById(id);
		mav.addObject("model",categoryDTO);
		return mav;
	}
	//UPDATE
	@RequestMapping(value = "/admin/category/edit/{id}", method = RequestMethod.POST)
	public ModelAndView categoryEdit(@Valid @ModelAttribute("model") CategoryDTO categoryDTO,BindingResult result,
			@PathVariable(name = "id") Long id) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("admin/categories/edit.jsp");
			mav.addObject("model", categoryDTO);
			return mav;
		} else {
			categoryService.update(categoryDTO);
			return new ModelAndView("redirect:/admin/category/list?message=success_update");
		}	
	}

	//DELETE
	@RequestMapping(value = "/admin/category/delete", method = RequestMethod.GET)
	public @ResponseBody ModelAndView categoryDelete(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		try {
			categoryService.delete(id);
		} catch (ConstraintViolationException | SQLIntegrityConstraintViolationException | DataIntegrityViolationException e) {
			ModelAndView mav = categoryList(1,null, request);
			mav.addObject("message", "Xóa không thành công");
			mav.addObject("alert", "danger"); 
			return mav;
		}		
		return new ModelAndView("redirect:/admin/category/list?message=success_delete");
	}

	
	
}
