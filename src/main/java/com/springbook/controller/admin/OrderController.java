package com.springbook.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.springbook.dto.book.BookDTO;
import com.springbook.dto.cart.CartDTO;
import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.order.OrderDetailDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.service.IBookService;
import com.springbook.service.ICartService;
import com.springbook.service.IOrderService;
import com.springbook.service.IUserService;
import com.springbook.service.impl.ExcelExporter;
import com.springbook.utils.MessageUtils;
import com.springbook.utils.SecurityUtils;
import com.springbook.validator.OrderValidator;

@Controller(value = "orderControllerInAdmin")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private IBookService bookService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ExcelExporter excelExporter;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICartService cartService;
	
	@Autowired
	private OrderValidator orderValidator;

	@RequestMapping(value = "/admin/order/list", method = RequestMethod.GET)
	public ModelAndView orderList(@RequestParam(name = "page", required = false) Integer page,
			@ModelAttribute("model") OrderDTO order) {
		ModelAndView mav = new ModelAndView("admin/orders/list.jsp");
		OrderDTO orderDTO = new OrderDTO();
		if (page != null) {
			orderDTO.setPage(page);
		} else {
			orderDTO.setPage(1);
		}
		orderDTO.setLimit(10);
		Pageable pageable = new PageRequest(orderDTO.getPage() - 1, orderDTO.getLimit());
		if (StringUtils.hasText(order.getSearchStatus()) || StringUtils.hasText(order.getStart())
				|| StringUtils.hasText(order.getEnd())) {
			Map<Integer, List<OrderDTO>> dtos = orderService.search(order.getSearchStatus(), order.getStart(),
					order.getEnd(), pageable);
			if (dtos.size() != 0) {
				for (Map.Entry<Integer, List<OrderDTO>> item : dtos.entrySet()) {
					orderDTO.setListResult(item.getValue());
					orderDTO.setTotalItem(item.getKey());
				}
				orderDTO.setSearchStatus(order.getSearchStatus());
				orderDTO.setStart(order.getStart());
				orderDTO.setEnd(order.getEnd());
				orderDTO.setTotalPage((int) Math.ceil((double) orderDTO.getTotalItem() / orderDTO.getLimit()));
			} else {
				orderDTO.setStart(order.getStart());
				orderDTO.setEnd(order.getEnd());
				orderDTO.setSearchStatus(order.getSearchStatus());
				orderDTO.setPage(1);
				orderDTO.setTotalPage(1);
				mav.addObject("message", "Kết quả không tìm thấy");
				mav.addObject("alert", "danger");
			}
		} else {
			orderDTO.setListResult(orderService.findAll(pageable));
			orderDTO.setTotalItem(orderService.getTotalItems());
			
			if(orderDTO.getTotalItem()!= 0) {
				orderDTO.setTotalPage((int) Math.ceil((double) orderDTO.getTotalItem() / orderDTO.getLimit()));
			}else {
				orderDTO.setTotalPage(1);
				orderDTO.setPage(1);
			}
		}
		mav.addObject("model", orderDTO);
		return mav;
	}

	@RequestMapping(value = "/admin/order/edit/{id}", method = RequestMethod.GET)
	public ModelAndView orderEdit(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("admin/orders/edit.jsp");
		String path = servletContext.getRealPath("/");
		File file = new File(path + "/template/local1.json");
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(file.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		OrderDTO orderDTO = orderService.findOneById(id);
		mav.addObject("model", orderDTO);
		mav.addObject("jsonn", result);
		if (request.getParameter("message") != null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			mav.addObject("message", message.get("message"));
			mav.addObject("alert", message.get("alert"));
		}
		return mav;
	}

	@RequestMapping(value = "/admin/order/edit/{id}", method = RequestMethod.POST)
	public String orderEdit(Model model, @ModelAttribute("model") OrderDTO orderDTO, @PathVariable(name = "id") Long id,
			HttpServletRequest request) {

			OrderDTO order = orderService.editOrder(orderDTO);
			List<Long> listBook = bookService.updateQuantity(null, order.getId());
			for (Long idbook : listBook) {
				bookService.updateQuantity(idbook, null);
			}
			return "redirect:/admin/order/edit/" + id + "?message=success_update";
		
		

	}

	@RequestMapping(value = "/admin/order/ship/{id}", method = RequestMethod.GET)
	public ModelAndView orderShip(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		OrderDTO orderDTO  = orderService.findOneById(id);	
		ModelAndView mav = new ModelAndView("admin/orders/shipment.jsp");	 
		mav.addObject("model", orderDTO);
		return mav;
	}
	@RequestMapping(value = "/admin/order/ship/{id}", method = RequestMethod.POST)
	public String orderShip(@PathVariable(name = "id") Long id, HttpServletRequest request, @ModelAttribute("model") OrderDTO orderDTO,BindingResult result) {
		orderValidator.validate(orderDTO, result);
		if(result.hasErrors()) {
			orderDTO = orderService.returnValidateOrderShip(orderDTO);
			return "admin/orders/shipment.jsp";
		}else {
			OrderDTO order = orderService.editOrder(orderDTO);
			List<Long> listBook = bookService.updateQuantity(null, order.getId());
			for (Long idbook : listBook) {
				bookService.updateQuantity(idbook, null);
			}
			return "redirect:/admin/order/edit/" + id + "?message=success_update";
		}
		
	}


	@RequestMapping(value = "/admin/order/refund/{id}", method = RequestMethod.GET)
	public ModelAndView orderRefund(@PathVariable(name = "id") Long id, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("admin/orders/refund.jsp");
		OrderDTO orderDTO = orderService.findOneById(id);
		mav.addObject("model", orderDTO);
		return mav;
	}

	@RequestMapping(value = "/admin/orders/export/excel", method = RequestMethod.GET)
	public void exportOrderToExcel(HttpServletResponse response,
			@RequestParam(name = "searchStatus", required = false) String status,
			@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end, @ModelAttribute("model") OrderDTO dto)
			throws IOException {
		List<OrderDTO> orders = orderService.findAllForExportExcel(status, start, end);
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=orders_"+timeStamp+".xlsx");
		excelExporter.exportOrder(response, orders);

	}

	@RequestMapping(value = "/admin/order/create/user", method = RequestMethod.GET)
	public ModelAndView orderCreateSelecteUser(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "name", required = false) String name) {
		ModelAndView mav = new ModelAndView("admin/orders/create/selectuser.jsp");

		UserDTO userDTO = new UserDTO();
		userDTO.setPage(1);
		if (page != null) {
			userDTO.setPage(page);
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
			}
		} else {

			userDTO.setListResult(userService.findAll(pageable));

			userDTO.setTotalItem(userService.getTotalItems());
			userDTO.setTotalPage((int) Math.ceil((double) userDTO.getTotalItem() / userDTO.getLimit()));
		}
		mav.addObject("model", userDTO);
		return mav;
	}

	@RequestMapping(value = "/admin/order/create/user/{id}", method = RequestMethod.GET)
	public ModelAndView orderCreate(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("admin/orders/create/create.jsp");
		String path = servletContext.getRealPath("/");
		File file = new File(path + "/template/local1.json");
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(file.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<Long, CartDTO> cart = new HashMap<>();
		cart = cartService.findAll(cart, id);

		OrderDTO dto = new OrderDTO();
		dto.setUser(userService.findOneById(id));
		mav.addObject("model", dto);
		mav.addObject("jsonn", result);
		mav.addObject("cart", cart);

		mav.addObject("book", bookService.findAll((short) 1));

		return mav;
	}

	@RequestMapping(value = "/admin/order/create/user/{id}", method = RequestMethod.POST)
	public ModelAndView orderCreate(@PathVariable(name = "id") Long id,
			@ModelAttribute(name = "model") OrderDTO order) {

		if (order.getAction() == 20 && order.getDeleteOrderDetail() == null) {
			ModelAndView mav = new ModelAndView("admin/orders/create/create.jsp");
			String path = servletContext.getRealPath("/");
			File file = new File(path + "/template/local1.json");
			String result = null;
			try {
				result = new String(Files.readAllBytes(Paths.get(file.toString())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			HashMap<Long, CartDTO> cart = new HashMap<>();
			cart = cartService.findAll(cart, id);
			OrderDTO dto = order;
			if (dto.getChosedBookIncart() != null) {
				List<Long> newList = new ArrayList<>();
				for (Long item : dto.getChosedBookIncart()) {
					if (!newList.contains(item)) {
						newList.add(item);
					}
				}

				List<OrderDetailDTO> orderDetailDTOs = orderService.initializeOrderDetail(newList, id);
				dto.setOrderDetails(orderDetailDTOs);
			}
			dto.setUser(userService.findOneById(id));
			mav.addObject("model", dto);
			mav.addObject("jsonn", result);
			mav.addObject("cart", cart);
			mav.addObject("book", bookService.findAll((short) 1));
			return mav;
		}
		if (order.getAction() == 30 && order.getDeleteOrderDetail() != null) {
			ModelAndView mav = new ModelAndView("admin/orders/create/create.jsp");
			String path = servletContext.getRealPath("/");
			File file = new File(path + "/template/local1.json");
			String result = null;
			try {
				result = new String(Files.readAllBytes(Paths.get(file.toString())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			HashMap<Long, CartDTO> cart = new HashMap<>();
			cart = cartService.findAll(cart, id);
			OrderDTO dto = order;
			List<Long> listid = dto.getChosedBookIncart();

			int j = 0;
			while (j < listid.size()) {

				if (Objects.equals(listid.get(j), order.getDeleteOrderDetail())) {
					listid.remove(j);
				}
				j++;
			}

			dto.setChosedBookIncart(listid);
			if (dto.getChosedBookIncart() != null) {
				List<OrderDetailDTO> orderDetailDTOs = orderService.initializeOrderDetail(dto.getChosedBookIncart(),
						id);
				dto.setOrderDetails(orderDetailDTOs);
			}
			dto.setUser(userService.findOneById(id));
			mav.addObject("model", dto);
			mav.addObject("jsonn", result);
			mav.addObject("cart", cart);
			mav.addObject("book", bookService.findAll((short) 1));
			return mav;
		}

		else {
			
			OrderDTO orderDTO = orderService.addOrderInAdmin(order);
			OrderDTO orderDTOs = orderService.addDetailOrderByAdmin(order,orderDTO);
			List<Long> bookListId = bookService.updateQuantity(null, orderDTO.getId());
			for (Long idbook : bookListId) {
				bookService.updateQuantity(idbook, null);
			}

			cartService.deleteDetailCartInAdmin(order.getChosedBookIncart(), orderDTO.getEmail());
			return new ModelAndView("redirect:/admin/order/list");
		}

	}

	@RequestMapping(value = "/subview/search/book", method = RequestMethod.GET)
	public ModelAndView getSearchBook(@RequestParam(name = "keyword", required = false) String keyword) {
		ModelAndView mav = new ModelAndView("admin/orders/create/search.jsp");
		BookDTO dto = bookService.search(keyword, null, null, null, null);
		if (CollectionUtils.isEmpty(dto.getListResult())) {
			mav.addObject("message_search", "Kết quả không tìm thấy");
			mav.addObject("alert_search", "danger");
		}
		mav.addObject("book", dto.getListResult());

		return mav;

	}

}
