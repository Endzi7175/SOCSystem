package com.sbnz.SIEMCenter2.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbnz.SIEMCenter2.model.Privilege;
import com.sbnz.SIEMCenter2.model.Role;
import com.sbnz.SIEMCenter2.model.User;
import com.sbnz.SIEMCenter2.repository.PrivilegeRepository;
import com.sbnz.SIEMCenter2.repository.RoleRepository;
import com.sbnz.SIEMCenter2.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
	private RoleRepository roleRepository;
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	
	public User save(User user){
		return userRepo.save(user);
	}
	public User findOne(String id){
		return userRepo.findById(id);
	}
	
	public void create(){
	  Privilege readPrivilege
      = createPrivilegeIfNotFound("READ_PRIVILEGE");
    Privilege writePrivilege
      = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

    List<Privilege> adminPrivileges = Arrays.asList(
      readPrivilege, writePrivilege);        
    createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
    createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

    Role adminRole = roleRepository.findByName("ROLE_ADMIN");
    User user = new User();
    user.setPassword(passwordEncoder.encode("test"));
    user.setEmail("test@test.com");
    user.setRoles(Arrays.asList(adminRole));
    userRepo.save(user);

}

@Transactional
private Privilege createPrivilegeIfNotFound(String name) {

    Privilege privilege = privilegeRepository.findByName(name);
    if (privilege == null) {
        privilege = new Privilege(name);
        privilegeRepository.save(privilege);
    }
    return privilege;
}

@Transactional
private Role createRoleIfNotFound(
  String name, Collection<Privilege> privileges) {

    Role role = roleRepository.findByName(name);
    if (role == null) {
        role = new Role(name);
        role.setPrivileges(privileges);
        roleRepository.save(role);
    }
    return role;
}

	
}
