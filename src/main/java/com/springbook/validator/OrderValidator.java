package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springbook.contanst.Constant;
import com.springbook.dto.order.OrderDTO;
import com.springbook.dto.order.OrderDetailDTO;
import com.springbook.repository.user.UserRepository;
import com.springbook.utils.SecurityUtils;

@Component
public class OrderValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return OrderDTO.class.isAssignableFrom(clazz);
	}

	public static final String NOTSELECTED = "Chưa nhập giá trị";

	@Override
	public void validate(Object target, Errors errors) {
		OrderDTO dto = (OrderDTO) target;

		if (dto.getStatus() < 0) {
			if (dto.getUser() != null) {

				if ("".equals(dto.getShipAddress().getAddress()) || "".equals(dto.getShipAddress().getProvince())
						|| "".equals(dto.getShipAddress().getDistrict()) || "".equals(dto.getShipAddress().getWard())
						|| "".equals(dto.getShipAddress().getNumber())) {
					errors.rejectValue("shipAddress", "order.shipAdress", "Chưa chọn địa chỉ");
				}
			}
			if (dto.getUser() == null) {

				if (userRepository.existsByEmail(dto.getEmail())) {
					errors.rejectValue("email", "order.email", "Tai khoan da ton tai");
				}
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "order.number", NOTSELECTED);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "order.address", NOTSELECTED);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "province", "order.province", NOTSELECTED);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "district", "order.district", NOTSELECTED);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ward", "order.ward", "Chưa chọn giá trị");
			}
		}
		if (dto.getStatus() == Constant.SHIP_STATUS) {
			int i = 0;
			for (OrderDetailDTO orderDetailDTO : dto.getOrderDetails()) {
				if (orderDetailDTO.getWareHouseId() == null) {
					errors.rejectValue("orderDetails[" + i + "].wareHouseId",
							"order.orderDetails[" + i + "].wareHouseId", NOTSELECTED);
				}
				i++;
			}
		}

	}

}
