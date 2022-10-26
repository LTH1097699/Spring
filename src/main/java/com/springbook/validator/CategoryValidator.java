package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.book.CategoryDTO;
import com.springbook.entity.book.CategoryEntity;
import com.springbook.repository.book.CategoryRepository;
@Component
public class CategoryValidator implements Validator {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return CategoryDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CategoryDTO categoryDTO = (CategoryDTO) target;	
		String codeCategory = categoryDTO.getCodeCategory();
		//check unique codeAuthor
		if(categoryDTO.getId()== null) {
			if(categoryRepository.existsByCodeCategory(codeCategory) == true) {
				errors.rejectValue("codeCategory","model.codeCategory","Mã đã tồn tại");
			}
		}
		if(categoryDTO.getId()!= null) {
			if(categoryRepository.existsByCodeCategory(codeCategory) == true) {
				CategoryEntity categoryEntity = categoryRepository.findOneByCodeCategory(codeCategory);
				if(categoryEntity.getId() != categoryDTO.getId()) {
					errors.rejectValue("codeCategory","model.codeCategory","Mã đã tồn tại");
				}			
			}
		}	
	}

}
