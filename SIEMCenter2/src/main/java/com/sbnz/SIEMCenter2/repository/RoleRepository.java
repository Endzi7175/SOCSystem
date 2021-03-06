package com.sbnz.SIEMCenter2.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sbnz.SIEMCenter2.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
