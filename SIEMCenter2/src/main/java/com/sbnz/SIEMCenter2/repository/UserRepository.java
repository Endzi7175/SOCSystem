package com.sbnz.SIEMCenter2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbnz.SIEMCenter2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
}
