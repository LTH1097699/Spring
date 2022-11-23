package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.user.UserDTO;
import com.springbook.repository.user.UserRepository;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
	public static final int UPDATE_FULLNAME = 100;
	public static final int UPDATE_PASSWORD = 200;
	public static final int CREATE_USER_IN_ADMIN = 300;
	public static final int CREATE_USER_IN_WEB = 350;
	public static final String MESS_NOT_SELECTED = "Chưa chọn giá trị";
	public static final String MESS_NOT_EMPTY = "Chưa nhập giá trị";
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDTO dto = (UserDTO) target;
		Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		Matcher regMatcher = null;
		if(StringUtils.hasText(dto.getUserName())) {
			regMatcher   = regexPattern.matcher(dto.getUserName());
		}
		
		
		
		if (StringUtils.hasText(dto.getPassword()) && StringUtils.hasText(dto.getConfirmPassword()) && !Objects.equals(dto.getPassword(), dto.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", null, "Mật khẩu không trùng");
		}
		if (dto.getAction() == CREATE_USER_IN_WEB) {
			if(StringUtils.hasText(dto.getUserName()) && userRepository.existsByEmail(dto.getUserName())) {
				errors.rejectValue("userName", "model.userName", "Tài khoản đã tồn tại");
			}
			if(!StringUtils.hasText(dto.getUserName())) {
				errors.rejectValue("userName", "model.userName", MESS_NOT_EMPTY);
			}
			if(StringUtils.hasText(dto.getUserName()) && !regMatcher.matches()) {
				errors.rejectValue("userName", "model.userName", "Sai định dạng email. Vd: user@email.com");
			}
			if(!StringUtils.hasText(dto.getPassword())) {
				errors.rejectValue("password", "model.password", MESS_NOT_EMPTY);
			}
			if(!StringUtils.hasText(dto.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "model.confirmPassword", MESS_NOT_EMPTY);
			}
		}
		
		
		
		if (dto.getAction() == CREATE_USER_IN_ADMIN) {
			if(StringUtils.hasText(dto.getUserName()) && userRepository.existsByEmail(dto.getUserName())) {
				errors.rejectValue("userName", "model.userName", "Tài khoản đã tồn tại");
			}
			if(StringUtils.hasText(dto.getUserName()) && !regMatcher.matches()) {
				errors.rejectValue("userName", "model.userName", "Sai định dạng email. Vd: user@email.com");
			}
			if (!StringUtils.hasText(dto.getRoleCode())) {
				errors.rejectValue("roleCode", "model.roleCode", MESS_NOT_EMPTY);
			}
			if (!StringUtils.hasText(dto.getAddress().getAddress())) {
				errors.rejectValue("address.address", "model.address.address", MESS_NOT_EMPTY);
			}
			if (!StringUtils.hasText(dto.getAddress().getNumber())) {
				errors.rejectValue("address.number", "model.address.number", MESS_NOT_EMPTY);
			}
			if (!StringUtils.hasText(dto.getAddress().getWard())) {
				errors.rejectValue("address.ward", "model.address.ward", MESS_NOT_SELECTED);
			}
			if (!StringUtils.hasText(dto.getAddress().getDistrict())) {
				errors.rejectValue("address.district", "model.address.district", MESS_NOT_SELECTED);
			}
			if (!StringUtils.hasText(dto.getAddress().getProvince())) {
				errors.rejectValue("address.province", "model.address.province", MESS_NOT_SELECTED);
			}
		}
		if (dto.getAction() == UPDATE_PASSWORD) {
			if (!StringUtils.hasLength(dto.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "model.confirmPassword", MESS_NOT_EMPTY);
			}
			if (!StringUtils.hasLength(dto.getPassword())) {
				errors.rejectValue("password", "model.password", MESS_NOT_EMPTY);
			}
		}
		if (dto.getAction() == UPDATE_FULLNAME) {
			if (!StringUtils.hasLength(dto.getFullNameValidator())) {
				errors.rejectValue("fullNameValidator", "model.fullName", MESS_NOT_EMPTY);

			}

		}

	}

}
