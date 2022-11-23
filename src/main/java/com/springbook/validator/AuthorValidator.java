package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.book.AuthorDTO;
import com.springbook.entity.book.AuthorEntity;
import com.springbook.repository.book.AuthorRepository;

@Component
public class AuthorValidator implements Validator {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {	
		return AuthorDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AuthorDTO authorDTO = (AuthorDTO) target;	
		String codeAuthor = authorDTO.getCodeAuthor();
		//check unique codeAuthor
		if(authorDTO.getId()== null) {
			if(authorRepository.existsByCodeAuthor(codeAuthor) == true) {
				errors.rejectValue("codeAuthor","model.codeAuthor","Mã đã tồn tại");
			}
		}
		if(authorDTO.getId()!= null) {
			if(authorRepository.existsByCodeAuthor(codeAuthor) == true) {
				AuthorEntity authorEntity = authorRepository.findOneByCodeAuthor(codeAuthor);
				if(authorEntity.getId() != authorDTO.getId()) {
					errors.rejectValue("codeAuthor","model.codeAuthor","Mã đã tồn tại");
				}			
			}
		
		}
		
	}



}
