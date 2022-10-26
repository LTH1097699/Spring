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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbook.dto.book.TagDTO;
import com.springbook.service.ITagService;
import com.springbook.utils.MessageUtils;
import com.springbook.validator.TagValidator;

@Controller(value = "tagControllerInAdmin")
public class TagController {
	@Autowired
	private ITagService tagService;

	@Autowired
	private TagValidator tagValidator;
	
	@Autowired
	private MessageUtils messageUtils;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(tagValidator);
	}

	@RequestMapping(value = "admin/tag/list", method = RequestMethod.GET)
	public ModelAndView tagList(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "name", required = false) String name, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/tags/list.jsp");
		TagDTO tagDTO = new TagDTO();
		if(page != null) {
			tagDTO.setPage(page);
		}else {
			tagDTO.setPage(1);
		}
		tagDTO.setLimit(10);
		
		Pageable pageable = new PageRequest(tagDTO.getPage()-1, tagDTO.getLimit());

		if (StringUtils.hasText(name)) {
			tagDTO.setListResult(tagService.search(name,pageable));
			tagDTO.setTotalItem(tagService.totalItemSearch(name));
			if (tagDTO.getTotalItem() != 0) {
				tagDTO.setTotalPage((int) Math.ceil((double) tagDTO.getTotalItem() / tagDTO.getLimit()));
			} else {
				tagDTO.setPage(1);
				tagDTO.setTotalPage(1);
				mav.addObject("mess", "Kết quả không tìm thấy");
			}
		} else {
			tagDTO.setListResult(tagService.findAll(pageable));
			tagDTO.setTotalItem(tagService.getTotalItems());
			if(tagDTO.getTotalItem() != 0) {
				tagDTO.setTotalPage((int) Math.ceil((double) tagDTO.getTotalItem() / tagDTO.getLimit()));
			}else {
				tagDTO.setPage(1);
				tagDTO.setTotalPage(1);
				mav.addObject("mess", "Chưa có dữ liệu");
			}
			
		}
		if(request.getParameter("message")!= null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		mav.addObject("model", tagDTO);
		return mav;
	}

	@RequestMapping(value = "/admin/tag/create", method = RequestMethod.GET)
	public ModelAndView tagCreate() {
		ModelAndView mav = new ModelAndView("admin/tags/create.jsp");
		mav.addObject("model", new TagDTO());
		return mav;
	}

	@RequestMapping(value = "/admin/tag/create", method = RequestMethod.POST)
	public ModelAndView tagCreate(RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("model") TagDTO tagDTO, BindingResult result, Errors errors) {		
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("admin/tags/create.jsp");
			return mav;
		} else {
			tagService.insert(tagDTO);
			return new ModelAndView("redirect:/admin/tag/list?name=&page=1&limit=10&message=success_save");
		}
	}

	@RequestMapping(value = "/admin/tag/edit/{id}", method = RequestMethod.GET)
	public ModelAndView tagEdit(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/tags/edit.jsp");
		TagDTO tagDTO = tagService.findById(id);
		mav.addObject("model", tagDTO);
		return mav;
	}

	@RequestMapping(value = "/admin/tag/edit/{id}", method = RequestMethod.POST)
	public ModelAndView tagEdit(@Valid @ModelAttribute("model") TagDTO tagDTO,
			BindingResult result, Errors errors, @PathVariable(name = "id") Long id) {	
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("admin/tags/edit.jsp");
			return mav;
		} else {
			tagService.update(tagDTO);
			return new ModelAndView("redirect:/admin/tag/list?name=&page=1&limit=10&message=success_update");
		}
	}

	@RequestMapping(value = "/admin/tag/delete", method = RequestMethod.GET)
	public @ResponseBody ModelAndView tagDelete(@RequestParam(name = "id") Long id, HttpServletRequest request) {		
		try {
			tagService.delete(id);
		} catch (ConstraintViolationException | SQLIntegrityConstraintViolationException
				| DataIntegrityViolationException e) {
			ModelAndView mav = tagList(1, null,null);
			mav.addObject("message", "Không thể xóa");
			mav.addObject("alert", "danger"); 
			return mav;
		}
		return new ModelAndView("redirect:/admin/tag/list?name=&page=1&limit=10&message=success_delete");
	}

}
