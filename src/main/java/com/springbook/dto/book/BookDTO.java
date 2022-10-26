package com.springbook.dto.book;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.springbook.dto.AbstractDTO;
import com.springbook.dto.user.CommentDTO;

public class BookDTO extends AbstractDTO<BookDTO>{
	private String action;
	
	@NotEmpty(message = "Chưa nhập giá trị")
	private String bookCode;
	
	@NotEmpty(message = "Chưa nhập giá trị")
	private String title;
	
	@NotNull(message = "Chưa nhập giá trị")
	@Digits(fraction = 5, integer = 10,message = "Giá trị không hợp lệ")
	private Double price;
	
	@Digits(fraction = 5, integer = 10,message = "Giá trị không hợp lệ")
	private Double discount;
	
	private String image;
	
	@Digits(fraction = 0, integer = 4,message = "Giá trị không hợp lệ")
	private int releaseYear;
	
	private MultipartFile imageFile;
	
	@NotEmpty(message = "Chưa nhập giá trị")
	private String shortContent;
	
	@NotEmpty(message = "Chưa nhập giá trị")
	private String context;
	
	@NotEmpty(message = "Chưa chọn giá trị")
	private String publisherCode;
	
	@NotEmpty(message = "Chưa chọn giá trị")
	private String categoryCode;
	
	@NotEmpty(message = "Chưa chọn giá trị")
	private String authorCode;
	
	@NotEmpty(message = "Chưa chọn giá trị")
	private String tagCode;
	
	private String warehouseCode;
	
	private String publisherName;
	private String categoryName;
	private String authorName;
	private String tagName;
	
	private List<Integer> quantityWareHouse;
	
	private List<String> wareHouseId;
	
	private List<WareHouseDTO> wareHouseDTO ;
	private List<BookWareHouseDTO> bookWareHouseDTOs;
	
	private List<WareHouseDTO> houseDTO;
	private int quantity;
	private short status;
	private Map<String,String> mapw;
	
	
	private List<CommentDTO> comments;
	
	private String comment;
	
	
	private Long[] categoryKey;
	private List<Long> authorKey;
	private List<Long> publisherKey;
	
	
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<BookWareHouseDTO> getBookWareHouseDTOs() {
		return bookWareHouseDTOs;
	}
	public void setBookWareHouseDTOs(List<BookWareHouseDTO> bookWareHouseDTOs) {
		this.bookWareHouseDTOs = bookWareHouseDTOs;
	}
	public Map<String, String> getMapw() {
		return mapw;
	}
	public void setMapw(Map<String, String> mapw) {
		this.mapw = mapw;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public MultipartFile getImageFile() {
		return imageFile;
	}
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	public String getBookCode() {
		return bookCode;
	}
	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getShortContent() {
		return shortContent;
	}
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}
	public String getPublisherCode() {
		return publisherCode;
	}
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public String getAuthorCode() {
		return authorCode;
	}
	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	public String getTagCode() {
		return tagCode;
	}
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	public List<Integer> getQuantityWareHouse() {
		return quantityWareHouse;
	}
	public void setQuantityWareHouse(List<Integer> quantityWareHouse) {
		this.quantityWareHouse = quantityWareHouse;
	}
	public List<WareHouseDTO> getWareHouseDTO() {
		return wareHouseDTO;
	}
	public void setWareHouseDTO(List<WareHouseDTO> wareHouseDTO) {
		this.wareHouseDTO = wareHouseDTO;
	}
//	public WareHouseDTO getHouseDTO() {
//		return houseDTO;
//	}
//	public void setHouseDTO(WareHouseDTO houseDTO) {
//		this.houseDTO = houseDTO;
//	}
	
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public List<WareHouseDTO> getHouseDTO() {
		return houseDTO;
	}
	public void setHouseDTO(List<WareHouseDTO> houseDTO) {
		this.houseDTO = houseDTO;
	}
	public List<Long> getAuthorKey() {
		return authorKey;
	}
	public void setAuthorKey(List<Long> authorKey) {
		this.authorKey = authorKey;
	}
	public List<Long> getPublisherKey() {
		return publisherKey;
	}
	public void setPublisherKey(List<Long> publisherKey) {
		this.publisherKey = publisherKey;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public List<String> getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(List<String> wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long[] getCategoryKey() {
		return categoryKey;
	}
	public void setCategoryKey(Long[] categoryKey) {
		this.categoryKey = categoryKey;
	}
	
	
	
}
