package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.book.PublisherDTO;
import com.springbook.entity.book.PublisherEntity;
import com.springbook.repository.book.PublisherRepository;
@Component
public class PublisherValidator implements Validator {
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PublisherDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PublisherDTO publisherDTO = (PublisherDTO) target;	
		String codePublisher = publisherDTO.getCodePublisher();
		//check unique codeAuthor
		if(publisherDTO.getId()== null) {
			if(publisherRepository.existsByCodePublisher(codePublisher) == true) {
				errors.rejectValue("codePublisher","model.codePublisher","Mã đã tồn tại");
			}
		}
		if(publisherDTO.getId()!= null) {
			if(publisherRepository.existsByCodePublisher(codePublisher) == true) {
				PublisherEntity publisherEntity = publisherRepository.findOneByCodePublisher(codePublisher);
				if(publisherEntity.getId() != publisherDTO.getId()) {
					errors.rejectValue("codePublisher","model.codePublisher","Mã đã tồn tại");
				}			
			}
		}	
	}

}
