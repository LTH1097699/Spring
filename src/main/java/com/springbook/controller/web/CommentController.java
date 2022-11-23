//package com.springbook.controller.web;
//
//import java.sql.SQLIntegrityConstraintViolationException;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.Valid;
//
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.springbook.dto.book.AuthorDTO;
//import com.springbook.dto.user.CommentDTO;
//import com.springbook.service.IUserService;
//import com.springbook.service.impl.CommentService;
//import com.springbook.utils.MessageUtils;
//
//@Controller(value = "commentControllerInWeb")
//public class CommentController {
//	@Autowired
//	private CommentService commentService;
//	
//	@Autowired
//	private IUserService userService;
//	
//	@Autowired
//	private MessageUtils messageUtils;
//	
//	
//	@GetMapping(value = "/admin/account/comment/list")
//	public String commentList(@RequestParam("page") int page,
//			@RequestParam(name = "name", required = false) String name, HttpServletRequest request, Model model) {
//
//		CommentDTO dto = new CommentDTO();
//		dto.setPage(page);
//		dto.setLimit(10);
//
//		Pageable pageable = new PageRequest(page - 1, 10);
//
//		dto.setListResult(commentService.findAll(pageable));
//		dto.setTotalItem(commentService.getTotalItems());
//		dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / dto.getLimit()));
//		
//		if (request.getParameter("message") != null) {
//			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
//			model.addAttribute("message", message.get("message"));
//			model.addAttribute("alert", message.get("alert"));
//		}
//		model.addAttribute("model", dto);
//		return "admin/warehouse/list.jsp";
//
//	}
//	
//	@RequestMapping(value = "/admin/author/create", method = RequestMethod.GET)
//	public ModelAndView authorCreate(Model model, RedirectAttributes redirectAttributes) {
//		ModelAndView mav = new ModelAndView("admin/authors/create.jsp");
//		mav.addObject("model", new AuthorDTO());
//		return mav;
//	}
//
//	@RequestMapping(value = "/admin/author/create", method = RequestMethod.POST)
//	public ModelAndView authorCreate(RedirectAttributes redirectAttributes,
//			@Valid @ModelAttribute("model") AuthorDTO authorDTO, BindingResult result, Errors errors) {
//		ModelAndView mav = new ModelAndView("admin/authors/create.jsp");
//		if (result.hasErrors()) {	
//			return mav;
//		} else {	
//			try {
//				authorService.insert(authorDTO);
//			} 
//			catch (javax.validation.ConstraintViolationException  e) {
//				for(ConstraintViolation<?> v : e.getConstraintViolations()) {
//					errors.rejectValue(v.getPropertyPath().toString(), "", "ma da ton taij");
//				}
//				return mav;
//			} 
//			catch (DataIntegrityViolationException e) {		
//				mav.addObject("message", "could not execute statement");
//				return mav;
//			}	
//			return new ModelAndView("redirect:/admin/author/list?name=&page=1&limit=10&message=success_save");
//		}
//	}
//
//	@RequestMapping(value = "/admin/author/edit/{id}", method = RequestMethod.GET)
//	public ModelAndView authorEdit(@PathVariable(value = "id") Long id, HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView("admin/authors/edit.jsp");
//		AuthorDTO authorDTO = authorService.findById(id);
//		mav.addObject("model", authorDTO);
//		return mav;
//	}
//
//	@RequestMapping(value = "/admin/author/edit/{id}", method = RequestMethod.POST)
//	public ModelAndView authorEdit(@Valid @ModelAttribute("model") AuthorDTO authorDTO,BindingResult result,
//			@RequestParam(name = "id") Long id) {
//
//		if (result.hasErrors()) {
//			ModelAndView mav = new ModelAndView("admin/authors/edit.jsp");
//			mav.addObject("model", authorDTO);
//			return mav;
//		} else {
//			authorService.update(authorDTO);
//			return new ModelAndView("redirect:/admin/author/list?name=&page=1&limit=10&message=success_update");
//		}
//	}
//
//	@RequestMapping(value = "/admin/author/delete", method = RequestMethod.GET)
//	public @ResponseBody ModelAndView authorDelete(@RequestParam(name = "id") Long id, HttpServletRequest request) {
//		try {
//			authorService.delete(id);
//		} catch (ConstraintViolationException | SQLIntegrityConstraintViolationException
//				| DataIntegrityViolationException e) {
//
//			ModelAndView mav = authorList(1, 10, null, request);
//			mav.addObject("message", "Không thể xóa");
//			mav.addObject("alert", "danger");
//			return mav;
//		}
//		return new ModelAndView("redirect:/admin/author/list?name=&page=1&limit=10&message=success_delete");
//
//	}
//	
//}
