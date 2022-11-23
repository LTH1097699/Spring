package com.springbook.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbook.entity.user.ShipAddressEntity;

public interface ShipAddressRepository extends JpaRepository<ShipAddressEntity, Long> {
	List<ShipAddressEntity> findByUserId(Long userid);
}
