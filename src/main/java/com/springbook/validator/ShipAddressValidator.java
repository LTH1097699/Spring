package com.springbook.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.user.ShipAddressDTO;
@Component
public class ShipAddressValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ShipAddressDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ShipAddressDTO dto = (ShipAddressDTO) target;
		
	}

}
