package com.springbook.controller.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springbook.contanst.Constant;
import com.springbook.dto.book.BookDTO;
import com.springbook.dto.cart.CartDTO;
import com.springbook.dto.user.CommentDTO;
import com.springbook.service.AuthorService;
import com.springbook.service.IBookService;
import com.springbook.service.ICartService;
import com.springbook.service.ICategoryService;
import com.springbook.service.IPublisherService;
import com.springbook.service.impl.CommentService;
import com.springbook.utils.SecurityUtils;

@Controller(value = "bookControllerOfWeb")
public class BookController {
	@Autowired
	private IBookService bookService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private IPublisherService publisherService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ICartService cartService;

	@RequestMapping(value = { "/home", "/home/book" }, method = RequestMethod.GET)
	public ModelAndView BookList(@RequestParam(name = "page", required = false) Integer page, Principal user,
			HttpSession session, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "categoryKey[]", required = false) List<Long> categoryKey,
			@RequestParam(name = "authorKey[]", required = false) List<Long> authorKey,
			@RequestParam(name = "publisherKey[]", required = false) List<Long> publisherKey) {
		ModelAndView mav = new ModelAndView("web/books/list.jsp");
		BookDTO bookDTO = new BookDTO();

		@SuppressWarnings("unchecked")
		HashMap<Long, CartDTO> cart = (HashMap<Long, CartDTO>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}

		if (page != null) {
			bookDTO.setPage(page);
		} else {
			bookDTO.setPage(1);
		}

		bookDTO.setLimit(10);
		Pageable pageable = new PageRequest(bookDTO.getPage() - 1, bookDTO.getLimit());

		if (StringUtils.hasText(name) || !CollectionUtils.isEmpty(categoryKey) || !CollectionUtils.isEmpty(authorKey)
				|| !CollectionUtils.isEmpty(publisherKey)) {
			mav.addObject("categoryIsChecked", categoryKey);
			mav.addObject("publisherIsChecked", publisherKey);
			mav.addObject("authorIsChecked", authorKey);
			mav.addObject("isSearchedName", name);
			BookDTO dto = bookService.search(name, categoryKey, authorKey, publisherKey, pageable);
			if (!CollectionUtils.isEmpty(dto.getListResult())) {
				bookDTO.setListResult(dto.getListResult());
				bookDTO.setTotalItem(dto.getTotalItem());
				bookDTO.setTotalPage((int) Math.ceil((double) bookDTO.getTotalItem() / bookDTO.getLimit()));
			} else {
				bookDTO.setTotalItem(1);
				bookDTO.setTotalPage(1);
				mav.addObject("message", "Kết quả không tìm thấy");
				mav.addObject("alert", "danger");
			}
		}
		else {
			bookDTO.setListResult(bookService.findAll(pageable, Constant.DISPLAY_STATUS));
			bookDTO.setTotalItem(bookService.getTotalItem());
			bookDTO.setTotalPage((int) Math.ceil((double) bookDTO.getTotalItem() / bookDTO.getLimit()));
		}
		if (user != null) {
			Long userId = SecurityUtils.getPrincipal().getId();
			cartService.checkUserCart(userId);
			if (cart.size() != 0) {
				cartService.transferToDB(cart, userId);

			}
			session.removeAttribute("cart");
		}
		mav.addObject("cate", categoryService.findAllId());
		mav.addObject("autho", authorService.findAllId());
		mav.addObject("publish", publisherService.findAllId());
		mav.addObject("model", bookDTO);
		mav.addObject("model2", bookService.findByCodeTagOrderByModifiedDateDescLimit6());
		return mav;
	}

	@GetMapping(value = "/home/book/{id}")
	public String bookDetail(@PathVariable(name = "id") Long id, Model model,
			@RequestParam(name = "page", required = false) Integer page) {
		BookDTO dto = bookService.findOneById(id);
		CommentDTO commentDTO = new CommentDTO();

		commentDTO.setListResult(commentService.findAll(null,id));
		
		model.addAttribute("model", dto);
		model.addAttribute("comment", commentDTO);
		return "web/books/detail.jsp";
	}

	@GetMapping(value = "/home/book/addcomment/{id}")
	public String createComment(@PathVariable(name = "id") Long id, Principal user,
			@RequestParam(name = "comment") String comment, HttpServletRequest request) {

		Long userId = SecurityUtils.getPrincipal().getId();
		commentService.insert(comment, id, userId);

		return "redirect:" + request.getHeader("Referer");
	}

}
