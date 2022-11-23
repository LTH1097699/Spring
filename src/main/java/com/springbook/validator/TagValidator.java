package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.book.TagDTO;
import com.springbook.entity.book.TagEntity;
import com.springbook.repository.book.TagRepository;
@Component
public class TagValidator implements Validator {
	
	@Autowired
	private TagRepository tagRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return TagDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TagDTO tagDTO = (TagDTO) target;
		String codeTag = tagDTO.getCodeTag();
		if(tagDTO.getId()== null) {
			if(tagRepository.existsByCodeTag(codeTag) == true) {
				errors.rejectValue("codeTag","model.codeTag","Mã đã tồn tại");
			}
		}
		if(tagDTO.getId()!= null) {
			if(tagRepository.existsByCodeTag(codeTag) == true) {
				TagEntity tagEntity = tagRepository.findOneByCodeTag(codeTag);
				if(tagEntity.getId() != tagDTO.getId()) {
					errors.rejectValue("codeTag","model.codeTag","Mã đã tồn tại");
				}			
			}
		
		}
	}

}
