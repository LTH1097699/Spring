package com.springbook.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component 
public class MessageUtils {
	public Map<String, String> getMessage(String message) {
		Map<String, String> result = new HashMap<>();

		if (message.equals("success_sign_in")) {
			result.put("message", "Đăng kí thành công");
			result.put("alert", "success");
		}else if(message.equals("success_save")) {
			result.put("message", "Thêm mới thành công");
			result.put("alert", "success");
		}else if(message.equals("success_update")) {
			result.put("message", "Cập nhật thành công");
			result.put("alert", "success");
		}else if(message.equals("success_delete")) {
			result.put("message", "Xóa thành công");
			result.put("alert", "success");
		}else if(message.equals("sent_success")) {
			result.put("message", "gửi thành công");
			result.put("alert", "success");
		}

		return result;
	}
}
