package com.springbook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbook.dto.MyUser;
import com.springbook.entity.user.RoleEntity;
import com.springbook.entity.user.UserEntity;
import com.springbook.repository.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findOneByEmailAndStatus(username, 1);
		
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		RoleEntity roleEntity = userEntity.getRole();
		authorities.add(new SimpleGrantedAuthority(roleEntity.getCode()));

			
		MyUser myUser = new MyUser( userEntity.getEmail(),userEntity.getPassword(), 
							true, true, true, true, authorities);
		myUser.setFullName(userEntity.getFullName());
		myUser.setId(userEntity.getId());
//		myUser.setPermission(permissonRepository.findAllByRoleIdAndStatusActive(roleid));
//		myUser.getPermission().forEach(v-> System.out.println(v));

		return myUser;
	}

}
