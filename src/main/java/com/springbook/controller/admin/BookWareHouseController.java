package com.springbook.controller.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbook.dto.book.BookWareHouseDTO;
import com.springbook.dto.order.OrderDetailDTO;
import com.springbook.service.IOrderService;
import com.springbook.service.impl.ExcelExporter;
import com.springbook.service.impl.WareHouseService;
import com.springbook.utils.MessageUtils;

@Controller(value = "bookWareHouseControllerInAdmin")
public class BookWareHouseController {
	@Autowired
	private WareHouseService wareHouseService;
	
	@Autowired
	private MessageUtils messageUtils;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ExcelExporter excelExporter;
	
	@GetMapping(value = "/admin/warehouse/list/book")
	public String allBookWareHouseList(@ModelAttribute("model") BookWareHouseDTO bookWareHouseDTO,
			Model model, @RequestParam("page") Integer page, 
			HttpServletRequest request) {
		
		BookWareHouseDTO dto = new BookWareHouseDTO();
		
		dto.setLimit(10);
		if (page != null) {
			dto.setPage(page);
		} else {
			dto.setPage(1);
		}
		Pageable pageable = new PageRequest(dto.getPage() - 1, 10);
		if(bookWareHouseDTO.getCodeWareHouse() != null || bookWareHouseDTO.getKeyword()!=null) {
				BookWareHouseDTO dtos = wareHouseService.searchBookInWareHouse(bookWareHouseDTO.getCodeWareHouse(),bookWareHouseDTO.getKeyword(),pageable);
				if(!CollectionUtils.isEmpty(dtos.getListResult())) {
					dto.setListResult(dtos.getListResult());
					dto.setTotalItem(dtos.getTotalItem());
					dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / dto.getLimit()));
				}else {
					dto.setTotalPage(1);
					dto.setPage(1);
					model.addAttribute("mess", "Kết quả không tìm thấy");		
				}
				dto.setCodeWareHouse(bookWareHouseDTO.getCodeWareHouse());
				dto.setKeyword(bookWareHouseDTO.getKeyword());
			
		}else {
			dto.setListResult(wareHouseService.allListBookInWareHouse(pageable));
			dto.setTotalItem(wareHouseService.getTotalItemBookWarehouse());
			dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / dto.getLimit()));
		}
		
		if (request.getParameter("message") != null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			model.addAttribute("message", message.get("message"));
			model.addAttribute("alert", message.get("alert"));
		}
		model.addAttribute("model", dto);
		model.addAttribute("wareHouse",wareHouseService.findAll());
		return "admin/warehouse/import.jsp";
	}
	
	@RequestMapping(value = "/admin/warehouse/book/{id}", method = RequestMethod.GET)
	public String updateBookWareHouse(Model model,@PathVariable(name = "id") Long id,HttpServletRequest request) {
		BookWareHouseDTO dto = wareHouseService.getOneBookWareHouseById(id);
		model.addAttribute("model",dto);
		if (request.getParameter("message") != null) {
			Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
			model.addAttribute("message", message.get("message"));
			model.addAttribute("alert", message.get("alert"));
		}
		return "admin/warehouse/editimport.jsp";
	}
	@RequestMapping(value = "/admin/warehouse/book/{id}", method = RequestMethod.POST)
	public String updateBookWareHouse(@ModelAttribute(name = "model") BookWareHouseDTO dto,Model model,@PathVariable(name = "id") Long id,HttpServletRequest request) {
		wareHouseService.updateBookWareHouse(id,dto);
		return "redirect:/admin/warehouse/book/"+id+"?message=success_update";
	}
	
	//=============================================================================================================================
	@GetMapping(value = "/admin/warehouse/list/order")
	public String allBookWareHouseListInOrderShipStatus(@ModelAttribute(name = "model") OrderDetailDTO order ,
			Model model, @RequestParam(name = "page",required = false ) Integer page, 
			HttpServletRequest request) {
		
		OrderDetailDTO dto = new OrderDetailDTO();

		dto.setLimit(10);
		if (page != null) {
			dto.setPage(page);
		} else {
			dto.setPage(1);
		}
		Pageable pageable = new PageRequest(dto.getPage() - 1, 10);

		if(order.getWareHouseId() != null || order.getKeyword()!=null) {
			OrderDetailDTO dtos = wareHouseService.searchOrderInWareHouse(order.getWareHouseId(), order.getKeyword(),pageable);
			dto.setListResult(dtos.getListResult());
			dto.setTotalItem(dtos.getTotalItem());
			dto.setWareHouseId(order.getWareHouseId());
			dto.setKeyword(order.getKeyword());
		}else {
			dto.setListResult(orderService.findAllOrderDetailsStatusShip(pageable));
			dto.setTotalItem(dto.getListResult().size());	
		}
		dto.setTotalPage((int) Math.ceil((double) dto.getTotalItem() / dto.getLimit()));
		
		model.addAttribute("model", dto);
		model.addAttribute("wareHouse",wareHouseService.findAll());
		return "admin/warehouse/export.jsp";
	}
	
	@GetMapping(value = "/export/warehouse")
	public void exportWareHouseAndBook(@RequestParam(name = "codeWareHouse") Long codeWareHouse,@RequestParam(name = "keyword") String keyword,
			HttpServletResponse response) throws IOException {
		BookWareHouseDTO dtos = wareHouseService.searchBookInWareHouse(codeWareHouse,keyword,null);
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=kho_soluong_"+timeStamp+".xlsx");
		excelExporter.exportWareHouse(response, dtos.getListResult());
	}
	
	@GetMapping(value = "/export/warehouse/order")
	public void exportWareHouseAndOrder(@RequestParam(name = "wareHouseId") Long codeWareHouse,@RequestParam(name = "keyword") String keyword,
			HttpServletResponse response) throws IOException {
		OrderDetailDTO dtos = wareHouseService.searchOrderInWareHouse(codeWareHouse,keyword,null);
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=kho_donhang_xuat_"+timeStamp+".xlsx");
		excelExporter.exportWareHouseAndOrderDetail(response, dtos.getListResult());
	}
}
