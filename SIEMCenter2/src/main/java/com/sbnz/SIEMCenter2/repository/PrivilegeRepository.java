package com.sbnz.SIEMCenter2.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sbnz.SIEMCenter2.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
