package com.springbook.conveter.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.springbook.conveter.user.CommentConverter;
import com.springbook.dto.book.BookDTO;
import com.springbook.entity.book.AuthorEntity;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.CategoryEntity;
import com.springbook.entity.book.PublisherEntity;
import com.springbook.entity.book.TagEntity;
import com.springbook.repository.book.AuthorRepository;
import com.springbook.repository.book.CategoryRepository;
import com.springbook.repository.book.PublisherRepository;
import com.springbook.repository.book.TagRepository;

@Component
public class BookConverter {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired 
	private PublisherRepository publisherRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	
	@Autowired 
	private CommentConverter commentConverter;
	
	@Autowired
	private FileConverter fileConverter = new FileConverter();
	
	public BookDTO toDTO(BookEntity entity) {
		BookDTO bookDTO = new BookDTO();
		
		bookDTO.setId(entity.getId());
		bookDTO.setBookCode(entity.getBookCode());
		bookDTO.setTitle(entity.getTitle());
		bookDTO.setShortContent(entity.getShortContent());
		bookDTO.setContext(entity.getContext());
		bookDTO.setImage(entity.getImage());
		bookDTO.setPrice(entity.getPrice());
		bookDTO.setDiscount(entity.getDiscount());
		bookDTO.setQuantity(entity.getQuantity());
		bookDTO.setReleaseYear(entity.getReleaseYear());
		bookDTO.setStatus(entity.getStatus());
		
		bookDTO.setCategoryCode(entity.getCategory().getCodeCategory());
		bookDTO.setPublisherCode(entity.getPublisher().getCodePublisher());
		bookDTO.setAuthorCode(entity.getAuthor().getCodeAuthor());
		bookDTO.setTagCode(entity.getTag().getCodeTag());
		
		bookDTO.setAuthorName(entity.getAuthor().getNameAuthor());
		bookDTO.setCategoryName(entity.getCategory().getNameCategory());
		bookDTO.setPublisherName(entity.getPublisher().getNamePublisher());
		bookDTO.setTagName(entity.getTag().getNametag());
		
		
//		bookDTO.setComments(commentConverter.toDTOList(entity.getComments()));
		return bookDTO;
	}
	
//	public StockDTO toDTO(StockEntity entity) {
//		StockDTO stock = new StockDTO();
//		stock.setId(entity.getId());
//		stock.setQuantity(entity.getQuantity());
//		return stock;
//	}
	
	public BookEntity toEntity(BookDTO bookDTO) {
		CategoryEntity categoryEntity = categoryRepository.findOneByCodeCategory(bookDTO.getCategoryCode());
		PublisherEntity publisherEntity = publisherRepository.findOneByCodePublisher(bookDTO.getPublisherCode());
		AuthorEntity authorEntity = authorRepository.findOneByCodeAuthor(bookDTO.getAuthorCode());
		TagEntity tagEntity = tagRepository.findOneByCodeTag(bookDTO.getTagCode());
		
		BookEntity bookEntity = new BookEntity();	
		
		bookEntity.setCategory(categoryEntity);
		bookEntity.setAuthor(authorEntity);
		bookEntity.setPublisher(publisherEntity);
		bookEntity.setTag(tagEntity);
		
		bookEntity.setBookCode(bookDTO.getBookCode());
		bookEntity.setTitle(bookDTO.getTitle());
		bookEntity.setShortContent(bookDTO.getShortContent());
		bookEntity.setContext(bookDTO.getContext());
		bookEntity.setImage(fileConverter.saveFile(bookDTO.getImageFile()));
		bookEntity.setPrice(bookDTO.getPrice());
		bookEntity.setDiscount(bookDTO.getDiscount());
		
		bookEntity.setReleaseYear(bookDTO.getReleaseYear());
		bookEntity.setStatus(bookDTO.getStatus());
		
		return bookEntity;
	}
	public BookEntity toEntity(BookEntity bookEntity, BookDTO bookDTO) {
		String image = bookEntity.getImage();
		
		CategoryEntity categoryEntity = categoryRepository.findOneByCodeCategory(bookDTO.getCategoryCode());
		PublisherEntity publisherEntity = publisherRepository.findOneByCodePublisher(bookDTO.getPublisherCode());
		AuthorEntity authorEntity = authorRepository.findOneByCodeAuthor(bookDTO.getAuthorCode());
		TagEntity tagEntity = tagRepository.findOneByCodeTag(bookDTO.getTagCode());	
		
		if(StringUtils.hasText(bookDTO.getImageFile().getName()) && bookDTO.getImageFile().getSize() > 0 ) {
			bookEntity.setImage(fileConverter.saveFile(bookDTO.getImageFile()));
		}
		if(!StringUtils.hasText(bookDTO.getImageFile().getName()) && bookDTO.getImageFile().getSize() < 0) {
			bookEntity.setImage(image);
		}
		
		bookEntity.setBookCode(bookDTO.getBookCode());
		bookEntity.setTitle(bookDTO.getTitle());
		bookEntity.setShortContent(bookDTO.getShortContent());
		bookEntity.setContext(bookDTO.getContext());
		
		bookEntity.setPrice(bookDTO.getPrice());
		bookEntity.setDiscount(bookDTO.getDiscount());
		bookEntity.setReleaseYear(bookDTO.getReleaseYear());
		bookEntity.setStatus(bookDTO.getStatus());
		
		bookEntity.setCategory(categoryEntity);
		bookEntity.setAuthor(authorEntity);
		bookEntity.setPublisher(publisherEntity);
		bookEntity.setTag(tagEntity);	
		return bookEntity;
	}
	
	
	public Map<String, String> OnedTo2dArray( List<String> list) {
		Map<String, String> m = new HashMap<>();
		for(int i =1;i<=list.size();i++) {
			if(i%2==0) {
				m.put(list.get(i-1),list.get(i));	
			}
		}
		return m;
	}

}
