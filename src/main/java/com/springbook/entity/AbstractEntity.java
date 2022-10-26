package com.springbook.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "createdate")
	@CreatedDate
	private Date createDate;

	@Column(name = "createby")
	@CreatedBy
	private String createBy;

	@Column(name = "modifieddate")
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "modifiedby")
	@LastModifiedBy
	private String modifiedBy;

	public Date getCreateDate() {
		return createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Long getId() {
		return id;
	}
}
