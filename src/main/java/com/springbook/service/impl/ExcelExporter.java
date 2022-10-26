package com.springbook.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.springbook.contanst.Constant;
import com.springbook.dto.book.BookWareHouseDTO;
import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.order.OrderDetailDTO;

@Service
public class ExcelExporter {

	private XSSFSheet sheet;

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer)value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	public void exportOrder(HttpServletResponse response, List<OrderDTO> orders) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Đơn hàng");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Id", style);
		createCell(row, 1, "Email", style);
		createCell(row, 2, "Họ và tên", style);
		createCell(row, 3, "Ngày đặt", style);
		createCell(row, 4, "Địa chỉ", style);
		createCell(row, 5, "SĐT", style);
		createCell(row, 5, "Trạng thái", style);
		createCell(row, 7, "Chi tiết", style);

		///////////////////////////////////////////////////////////////
		int rowCount = 1;

		CellStyle style1 = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeight(14);
		style1.setFont(font1);
		style1.setWrapText(true);
		for (OrderDTO order : orders) {
			Row row1 = sheet.createRow(rowCount++);
			int rowStr = 0;
			String address = order.getAddress() + ", " + order.getWard() + ", " + order.getDistrict() + ", "
					+ order.getProvince();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String statusStr = getStatusStr(order.getStatus());
			createCell(row1, 0, order.getId(), style1);
			createCell(row1, 1, order.getEmail(), style1);
			createCell(row1, 2, order.getUsername(), style1);
			createCell(row1, 3, df.format(order.getShipDate()).toString() , style1);
			createCell(row1, 4, address, style1);
			createCell(row1, 5, order.getNumber(), style1);
			
			createCell(row1, 6, statusStr, style1);
			String orderdetail = "";
			for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
				rowStr++;
				if(order.getOrderDetails().size()==rowStr) {
					String line = orderDetail.getBook().getTitle() + "/SL :" + orderDetail.getQuantity();
					orderdetail += line;
				}else {
					String line = orderDetail.getBook().getTitle() + "/SL :" + orderDetail.getQuantity() + "\n";
					orderdetail += line;
				}	
			}
			createCell(row1, 7, orderdetail, style1);
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();
	}

	public String getStatusStr(int status) {
		String statusStr = "";
		switch (status) {
		case Constant.PENDING_STATUS:
			statusStr= "Chờ xử lý";
			break;
		case Constant.PROCESSING_STATUS:
			statusStr=  "Đang xử lý";
			break;
		case Constant.CANCEL_STATUS:
			statusStr=  "Hủy đơn hàng";
			break;
		case Constant.SHIP_STATUS:
			statusStr=  "Đang giao hàng";
			break;
		case Constant.REFUND_STATUS:
			statusStr=  "Hoàn trả";
			break;
		case Constant.COMPLETE_STATUS:
			statusStr=  "Hoàn thành";
			break;
		default:
			break;
		}
		return statusStr;
	}
	
	
	public void exportWareHouse(HttpServletResponse response, List<BookWareHouseDTO> bookWareHouseDTOs) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Đơn hàng");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "STT", style);
		createCell(row, 1, "Sản phẩm", style);
		createCell(row, 2, "Số lượng", style);
		createCell(row, 3, "Tên kho", style);

		///////////////////////////////////////////////////////////////
		int rowCount = 1;

		CellStyle style1 = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeight(14);
		style1.setFont(font1);
		style1.setWrapText(true);
		
		int number = 0;
		for (BookWareHouseDTO item : bookWareHouseDTOs) {
			Row row1 = sheet.createRow(rowCount++);
			String product = "Tên: "+item.getBook().getTitle()+"\nMã: "+item.getBook().getBookCode();
			createCell(row1, 0, number, style1);
			createCell(row1, 1, product, style1);
			createCell(row1, 2, item.getQuantity(), style1);	
			createCell(row1, 3, item.getWareHouse().getName() , style1);
			number++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();
	}
	
	public void exportWareHouseAndOrderDetail(HttpServletResponse response, List<OrderDetailDTO > orderdetails) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Đơn hàng");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "STT", style);
		createCell(row, 1, "Mã đơn hàng", style);
		createCell(row, 2, "Mã chi tiết", style);
		createCell(row, 3, "Sản phẩm", style);
		createCell(row, 4, "Số lượng", style);
		createCell(row, 5, "Tên kho", style);

		///////////////////////////////////////////////////////////////
		int rowCount = 1;

		CellStyle style1 = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeight(14);
		style1.setFont(font1);
		style1.setWrapText(true);
		
		int number = 0;
		for (OrderDetailDTO item : orderdetails) {
			Row row1 = sheet.createRow(rowCount++);
			
			String product = item.getBook().getTitle()+"\n"+item.getBook().getBookCode();
			createCell(row1, 0, number, style1);
			createCell(row1, 1, item.getOrderId(), style1);
			createCell(row1, 2, item.getId(), style1);	
			createCell(row1, 3, product , style1);
			createCell(row1, 4, item.getQuantity() , style1);
			createCell(row1, 5, item.getWareHouseDTO().getName() , style1);
			number++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();
	}
}
