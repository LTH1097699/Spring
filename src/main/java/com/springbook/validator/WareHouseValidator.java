package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.book.WareHouseDTO;
import com.springbook.entity.book.WareHouseEntity;
import com.springbook.repository.book.WareHouseRepository;
@Component
public class WareHouseValidator implements Validator {
	@Autowired
	private WareHouseRepository repository;

	@Override
	public boolean supports(Class<?> clazz) {
		return WareHouseDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		WareHouseDTO dto = (WareHouseDTO) target;
		String code = dto.getCode();
		if(dto.getId()== null) {
			if(repository.existsByCode(code) == true) {
				errors.rejectValue("code","model.code","Mã đã tồn tại");
			}
		}
		else if(dto.getId()!= null) {
			if(repository.existsByCode(code) == true) {
				WareHouseEntity entity = repository.findOneByCode(code);
				if(entity.getId() != dto.getId()) {
					errors.rejectValue("code","model.code","Mã đã tồn tại");
				}			
			}
		
		}
	}
}
