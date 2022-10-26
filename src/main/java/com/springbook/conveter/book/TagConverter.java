package com.springbook.conveter.book;

import org.springframework.stereotype.Component;

import com.springbook.dto.book.TagDTO;
import com.springbook.entity.book.TagEntity;
@Component
public class TagConverter {
	
	public TagDTO toDTO(TagEntity entity) {
		TagDTO tagDTO = new TagDTO();
		
		tagDTO.setId(entity.getId());
		tagDTO.setCodeTag(entity.getCodeTag());
		tagDTO.setNameTag(entity.getNametag());

		return tagDTO;
	}
	
	public TagEntity toEntity(TagDTO tagDTO) {
		TagEntity tagEntity = new TagEntity();	
		tagEntity.setCodeTag(tagDTO.getCodeTag());
		tagEntity.setNametag(tagDTO.getNameTag());
		return tagEntity;
	}
	public TagEntity toEntity(TagEntity tagEntity,TagDTO tagDTO) {
		
		tagEntity.setCodeTag(tagDTO.getCodeTag());
		tagEntity.setNametag(tagDTO.getNameTag());
		return tagEntity;
	}
}
