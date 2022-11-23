package com.springbook.controller.admin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbook.dto.order.OrderDTO;
import com.springbook.service.IExportPDFService;
import com.springbook.service.IOrderService;

@Controller
public class RecieptController {
	/*
	 * generate html to pdf 
	 * source : https://simplesolution.dev/spring-boot-web-download-pdf-from-html-template/
	 * 
	 * issue font when render by ITextRenderer
	 * https://stackoverflow.com/questions/10250606/generation-of-pdf-from-html-with-non-latin-characters-using-itextrenderer-does-n
	 */
	@Autowired
	private IExportPDFService exportPDFService;
	
	@Autowired
	private IOrderService orderService;
	
	
	
	@RequestMapping(value = "/pdf/downloadRecept", method = RequestMethod.GET)
	public void downloadReceipt(HttpServletResponse respone, @RequestParam("id") Long id) throws IOException{
		
		Map<String, Object> data = createData(id);
		ByteArrayInputStream exportPDF = exportPDFService.exportPDF("reciept.html", data);
		
		respone.setContentType("text/html; charset=UTF-8");
		respone.setHeader("Content-Disposition", "attachment; filename=reciept.pdf");
		
		IOUtils.copy(exportPDF, respone.getOutputStream());
	}

	public Map<String, Object> createData(Long id){
		Map<String, Object> data = new HashMap<>();
		OrderDTO orderDTO = orderService.findOneById(id);
		data.put("model", orderDTO);
		
		return data;
	}
}
