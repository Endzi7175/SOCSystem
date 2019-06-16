package com.sbnz.SIEMCenter2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnz.SIEMCenter2.model.User;
import com.sbnz.SIEMCenter2.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	public User save(User user){
		return userRepo.save(user);
	}
	public User findOne(String id){
		return userRepo.findById(id);
	}
	
}
