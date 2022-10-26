package com.springbook.entity.cart;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;
import com.springbook.entity.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cartt")
public class CartEntity extends AbstractEntity {
	
	@OneToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	private UserEntity user;
	
	@OneToMany(mappedBy = "cart",orphanRemoval = true)
	private List<DetailCartEntity> detailCarts = new ArrayList<>();

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<DetailCartEntity> getDetailCarts() {
		return detailCarts;
	}

	public void setDetailCarts(List<DetailCartEntity> detailCarts) {
		this.detailCarts = detailCarts;
	}
	
	
}
