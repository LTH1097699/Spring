package com.springbook.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springbook.dto.book.BookDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.repository.book.BookRepository;
@Component
public class BookValidator implements Validator {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return BookDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BookDTO bookDTO = (BookDTO) target;	
		String bookCode = bookDTO.getBookCode();
		
		//check unique codeAuthor
		if(bookDTO.getId()== null) {
			if(bookRepository.existsByBookCode(bookCode) == true) {
				errors.rejectValue("bookCode","model.bookCode","Mã đã tồn tại");
			}
		}
		if(bookDTO.getId()!= null) {
			if(bookRepository.existsByBookCode(bookCode) == true) {
				BookEntity bookEntity = bookRepository.findOneByBookCode(bookCode);
				if(bookEntity.getId() != bookDTO.getId()) {
					errors.rejectValue("bookCode","model.bookCode","Mã đã tồn tại");
				}			
			}
		}	
		//image
		if(bookDTO.getId()== null && bookDTO.getImageFile().getSize()<= 0) {
			errors.rejectValue("imageFile","model.imageFile","Chưa chọn hình ảnh");
		}
		
		if(bookDTO.getWareHouseId().size()==0) {
			errors.rejectValue("wareHouseId","model.wareHouseId","Chưa chọn kho");
		}
		if(bookDTO.getWareHouseId().size()>0) {
			for(int i=1;i<bookDTO.getWareHouseId().size();i++) {
				if(bookDTO.getWareHouseId().get(i) == "") {
					errors.rejectValue("wareHouseId","model.wareHouseId","Chưa nhập số lượng kho");
				}
			}
			
		}
		
	}

}
