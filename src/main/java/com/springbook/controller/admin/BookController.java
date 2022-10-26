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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.springbook.dto.book.BookDTO;
import com.springbook.service.AuthorService;
import com.springbook.service.IBookService;
import com.springbook.service.ICategoryService;
import com.springbook.service.IPublisherService;
import com.springbook.service.ITagService;
import com.springbook.service.impl.WareHouseService;
import com.springbook.utils.MessageUtils;
import com.springbook.validator.BookValidator;

@Controller(value = "bookControllerOfAdmin")
public class BookController {

	@Autowired
	private IBookService bookService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IPublisherService publisherService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private ITagService tagService;
	
	@Autowired
	private WareHouseService wareHouseService;

	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private BookValidator bookValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(bookValidator);
	}

	@RequestMapping(value = "/admin/book/list", method = RequestMethod.GET)
	public ModelAndView bookList(@RequestParam(name = "page", required = false) Integer page, 
			@RequestParam(name = "name", required = false) String name, HttpServletRequest request) {

		BookDTO bookDTO = new BookDTO();
		ModelAndView mav = new ModelAndView("admin/books/list.jsp");
		if (page != null) {
			bookDTO.setPage(page);
		} else {
			bookDTO.setPage(1);
		}
		bookDTO.setLimit(10);
		Pageable pageable = new PageRequest(bookDTO.getPage() - 1, bookDTO.getLimit());

		if (StringUtils.hasText(name)) {
			bookDTO.setListResult(bookService.searchInAdmin(name,pageable));
			bookDTO.setTotalItem(bookService.totalItemSearch(name));
			if (bookDTO.getTotalItem() != 0) {
				bookDTO.setTotalPage((int) Math.ceil((double) bookDTO.getTotalItem() / bookDTO.getLimit()));
			} else {
				bookDTO.setPage(1);
				bookDTO.setTotalPage(1);
				mav.addObject("mess", "Kết quả không tìm thấy");
			}
		} else {
			bookDTO.setListResult(bookService.findAll(pageable,com.springbook.contanst.Constant.HIDE_STATUS));
			bookDTO.setTotalItem(bookService.getTotalItem());
			bookDTO.setTotalPage((int) Math.ceil((double) bookDTO.getTotalItem() / bookDTO.getLimit()));		
		}
		if (request.getParameter("message") != null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		mav.addObject("category", categoryService.findAll());
		mav.addObject("publisher", publisherService.findAll());
		mav.addObject("author", authorService.findAll());
		mav.addObject("tag", tagService.findAll());

		mav.addObject("model", bookDTO);
		return mav;
	}

	@RequestMapping(value = "/admin/book/create", method = RequestMethod.GET)
	public ModelAndView bookCreate() {
		ModelAndView mav = new ModelAndView("admin/books/new.jsp");	
		mav.addObject("category", categoryService.findAll());
		mav.addObject("publisher", publisherService.findAll());
		mav.addObject("author", authorService.findAll());
		mav.addObject("tag", tagService.findAll());
		mav.addObject("warehouse", wareHouseService.findAllMap());
		mav.addObject("model", new BookDTO());
		return mav;
	}
	@RequestMapping(value = "/admin/book/create", method = RequestMethod.POST)
	public ModelAndView bookCreate(RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("model") BookDTO dto, BindingResult result, Errors errors){
			if (result.hasErrors()) {
				ModelAndView mav = new ModelAndView("admin/books/new.jsp");
				mav.addObject("category", categoryService.findAll());
				mav.addObject("publisher", publisherService.findAll());
				mav.addObject("author", authorService.findAll());
				mav.addObject("tag", tagService.findAll());
				mav.addObject("warehouse", wareHouseService.findAllMap());
				return mav;
			} else {
				BookDTO book = bookService.insert(dto);
				//add quantity for book
				if(dto.getWareHouseId().size() >1) {
					wareHouseService.insertQuantityWareHouse(dto.getWareHouseId(),book.getId());
				}
				bookService.updateQuantity(book.getId(), null);
				return new ModelAndView("redirect:/admin/book/list?name=&page=1&limit=10&message=success_save");
			}
	}
	
	@RequestMapping(value = "/admin/book/edit/{id}", method = RequestMethod.GET)
	public ModelAndView bookEdit(@PathVariable(name = "id") Long id, HttpServletRequest request
			) throws JsonProcessingException {
		ModelAndView mav = new ModelAndView("admin/books/edit.jsp");
		BookDTO bookDTO = bookService.findOneById(id);

		mav.addObject("category", categoryService.findAll());
		mav.addObject("publisher", publisherService.findAll());
		mav.addObject("author", authorService.findAll());
		mav.addObject("tag", tagService.findAll());
		mav.addObject("warehouse", wareHouseService.findAllMap());
		mav.addObject("model", bookDTO);
		//send a json string 
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(wareHouseService.getBookWareHouse(id));
		mav.addObject("mapWareHouse",json);
			
		return mav;
	}

	@RequestMapping(value = "/admin/book/edit/{id}", method = RequestMethod.POST)
	public ModelAndView bookEdit(@Valid @ModelAttribute("model") BookDTO dto, BindingResult result, Errors errors,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			@PathVariable(name = "id") Long id
			) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("admin/books/edit.jsp");
			mav.addObject("category", categoryService.findAll());
			mav.addObject("publisher", publisherService.findAll());
			mav.addObject("author", authorService.findAll());
			mav.addObject("tag", tagService.findAll());
			return mav;
		} else {
			BookDTO bookDTO = bookService.update(dto);
			
			if(dto.getWareHouseId().size()>1) {
				wareHouseService.updateBookWareHouse(dto.getWareHouseId(), dto.getId());
			}
			bookService.updateQuantity(bookDTO.getId(), null);
			return new ModelAndView("redirect:/admin/book/list?name=&page=1&limit=10&message=success_update");
		}
	}

	@RequestMapping(value = "/admin/book/delete", method = RequestMethod.GET)
	public @ResponseBody ModelAndView bookDelete(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		try {
			bookService.delete(id);
		} catch (ConstraintViolationException | SQLIntegrityConstraintViolationException
				| DataIntegrityViolationException e) {
			ModelAndView mav = bookList(1,null, request);
			mav.addObject("message", "Xóa thất bại");
			mav.addObject("alert", "danger");
			return mav;
		}
		return new ModelAndView("redirect:/admin/book/list?name=&page=1&limit=10&message=success_delete");
	}
}
