package com.springbook.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.user.ContactDTO;

@Component
public class ContactValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ContactDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ContactDTO dto = (ContactDTO) target;
		
		Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		Matcher regMatcher = null;
		if(StringUtils.hasText(dto.getEmail())) {
			regMatcher   = regexPattern.matcher(dto.getEmail());
			
			if(!regMatcher.matches() ) {
				errors.rejectValue("email", null, "Sai định dạng email. Vd: user@email.com");
			}
		}
		
		
 }
	
}
