package com.springbook.controller.admin;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbook.dto.book.BookWareHouseDTO;
import com.springbook.dto.book.WareHouseDTO;
import com.springbook.service.impl.WareHouseService;
import com.springbook.utils.MessageUtils;
import com.springbook.validator.WareHouseValidator;

@Controller(value = "wareHouseControllerInAdmin")
public class WareHouseController {
	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private WareHouseService wareHouseService;

	@Autowired
	private WareHouseValidator wareHouseValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(wareHouseValidator);
	}

	@GetMapping(value = "/admin/warehouse/list")
	public String wareHouseList(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "name", required = false) String name, HttpServletRequest request, Model model) {

		WareHouseDTO dto = new WareHouseDTO();
		if(page!= null) {
			dto.setPage(page);
		}else {
			dto.setPage(1);
		}
		
		dto.setLimit(10);

		Pageable pageable = new PageRequest(dto.getPage() - 1, dto.getLimit());

		if (StringUtils.hasText(name)) {
			dto.setListResult(wareHouseService.search(name,pageable));
			dto.setTotalItem(wareHouseService.totalItemSearch(name));
			if (dto.getTotalItem() != 0) {
				dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / dto.getLimit()));
			} else {
				dto.setPage(1);
				dto.setTotalPage(1);
				model.addAttribute("mess", "Kết quả không tìm thấy");
			}
		} else {
			dto.setListResult(wareHouseService.findAll(pageable));
			dto.setTotalItem(wareHouseService.getTotalItems());
			dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / dto.getLimit()));
		}
		if (request.getParameter("message") != null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			model.addAttribute("message", message.get("message"));
			model.addAttribute("alert", message.get("alert"));
		}
		model.addAttribute("model", dto);
		return "admin/warehouse/list.jsp";
	}

	@GetMapping(value = "/admin/warehouse/create")
	public String wareHouseCreate(Model model) {
		model.addAttribute("model", new WareHouseDTO());
		return "admin/warehouse/create.jsp";
	}

	@PostMapping(value = "/admin/warehouse/create")
	public String wareHouseCreate(RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("model") WareHouseDTO dto, BindingResult result, Errors errors) {
		if (result.hasErrors()) {
			return "admin/warehouse/create.jsp";
		} else {
			wareHouseService.save(dto);
			return "redirect:/admin/warehouse/list?name=&page=1&message=success_save";
		}
	}

	@GetMapping(value = "/admin/warehouse/edit/{id}")
	public String wareHouseEdit(@PathVariable(name = "id") Long id, HttpServletRequest request, Model model) {
		model.addAttribute("model", wareHouseService.findById(id));
		return "admin/warehouse/edit.jsp";
	}

	@PostMapping(value = "/admin/warehouse/edit/{id}")
	public String wareHouseEdit(@Valid @ModelAttribute("model") WareHouseDTO dto, BindingResult result, Errors errors,
			@PathVariable(name = "id") Long id) {
		if (result.hasErrors()) {
			return "/admin/warehouse/edit/{id}";
		} else {
			wareHouseService.save(dto);
			return "redirect:/admin/warehouse/list?name=&page=1&message=success_update";
		}
	}

	@GetMapping(value = "/admin/warehouse/delete")
	public String wareHouseDelete(@RequestParam(name = "id") Long id, HttpServletRequest request, Model model) {
		try {
			wareHouseService.delete(id);
		} catch (ConstraintViolationException | SQLIntegrityConstraintViolationException
				| DataIntegrityViolationException e) {
			model.addAttribute("message", "Không thể xóa");
			model.addAttribute("alert", "danger");
			return "/admin/warehouse/list";
		}
		return "redirect:/admin/warehouse/list?name=&page=1&message=success_delete";
	}

}
