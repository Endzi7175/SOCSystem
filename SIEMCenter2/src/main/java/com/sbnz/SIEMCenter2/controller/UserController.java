package com.sbnz.SIEMCenter2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.SIEMCenter2.dto.LoginDTO;
import com.sbnz.SIEMCenter2.dto.RolePrivilegeDto;
import com.sbnz.SIEMCenter2.model.Privilege;
import com.sbnz.SIEMCenter2.model.Role;
import com.sbnz.SIEMCenter2.repository.PrivilegeRepository;
import com.sbnz.SIEMCenter2.repository.RoleRepository;
import com.sbnz.SIEMCenter2.security.TokenUtils;
import com.sbnz.SIEMCenter2.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	UserService userService;
	@Autowired
	PrivilegeRepository privRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	TokenUtils tokenUtils;
	@RequestMapping(method = RequestMethod.GET, value="/api/crateUser")
	public void create(){
		userService.create();
	}
	@RequestMapping(method = RequestMethod.POST,value="api/addPrivilege")
	public void addPrivilege(@RequestBody RolePrivilegeDto rolePrivilegeDto){
		System.out.println(rolePrivilegeDto.getPrivilege() + " " + rolePrivilegeDto.getRole());
		userService.createPrivilegeIfNotFound(rolePrivilegeDto.getPrivilege());
		Privilege priv = privRepository.findByName(rolePrivilegeDto.getPrivilege());
		Role roleDB = roleRepository.findByName(rolePrivilegeDto.getRole());
		roleDB.getPrivileges().add(priv);
		roleRepository.save(roleDB);
	}
	//USER_READ_PRIVILEGE
	@PreAuthorize("hasPermission(#id, 'user', 'read')")
	@RequestMapping(value="api/getUser")
	public String getUser(){
		return "User read";
	}
	//NOVO_READ_PRIVILEGE
	@PreAuthorize("hasPermission(#id, 'novo', 'read')")
	@RequestMapping(value="api/getUser2")
	public String get(){
		return "uspelo";
	}
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
        	UsernamePasswordAuthenticationToken token = 
        			new UsernamePasswordAuthenticationToken(
					loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails details = userDetailsService.
            		loadUserByUsername(loginDTO.getUsername());
            return new ResponseEntity<String>(
            		tokenUtils.generateToken(details), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
        }
	}
}
