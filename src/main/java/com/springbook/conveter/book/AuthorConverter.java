package com.springbook.conveter.book;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import com.springbook.dto.book.AuthorDTO;
import com.springbook.entity.book.AuthorEntity;

@UtilityClass
public class AuthorConverter {
	
	public AuthorDTO toDTO(AuthorEntity entity) {
		AuthorDTO authorDTO = new AuthorDTO();
		authorDTO.setId(entity.getId());
		authorDTO.setCodeAuthor(entity.getCodeAuthor());
		authorDTO.setNameAuthor(entity.getNameAuthor());
		
		return authorDTO;
	}
	
	public AuthorEntity toEntity(AuthorDTO authorDTO) {
		AuthorEntity authorEntity = new AuthorEntity();
		authorEntity.setCodeAuthor(authorDTO.getCodeAuthor());
		authorEntity.setNameAuthor(authorDTO.getNameAuthor());
		
		return authorEntity;
	}
	
	public AuthorEntity toEntity(AuthorEntity authorEntity, AuthorDTO authorDTO) {
		
		authorEntity.setCodeAuthor(authorDTO.getCodeAuthor());
		authorEntity.setNameAuthor(authorDTO.getNameAuthor());
		
		return authorEntity;
	}
}
