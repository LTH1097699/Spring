package com.springbook.entity.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;

@Entity
@Table(name = "role")
public class RoleEntity extends AbstractEntity {	
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String nameRole;

	@OneToMany(mappedBy = "role")
	private List<UserEntity> users = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	
	
}
