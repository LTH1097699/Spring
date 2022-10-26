package com.springbook.conveter.book;

import org.springframework.stereotype.Component;

import com.springbook.dto.book.BookDTO;
import com.springbook.dto.book.PublisherDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.PublisherEntity;
@Component
public class PublisherConverter {
	
	public PublisherDTO toDTO(PublisherEntity entity) {
		PublisherDTO publisherDTO = new PublisherDTO();
		
		publisherDTO.setId(entity.getId());
		publisherDTO.setCodePublisher(entity.getCodePublisher());
		publisherDTO.setNamePublisher(entity.getNamePublisher());

		return publisherDTO;
	}
	
	public PublisherEntity toEntity(PublisherDTO publisherDTO) {
		PublisherEntity publisherEntity = new PublisherEntity();	
		publisherEntity.setCodePublisher(publisherDTO.getCodePublisher());
		publisherEntity.setNamePublisher(publisherDTO.getNamePublisher());
		return publisherEntity;
	}
	public PublisherEntity toEntity(PublisherEntity publisherEntity,PublisherDTO publisherDTO) {
		
		publisherEntity.setCodePublisher(publisherDTO.getCodePublisher());
		publisherEntity.setNamePublisher(publisherDTO.getNamePublisher());
		return publisherEntity;
	}

}
