package com.sbnz.SIEMCenter2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbnz.SIEMCenter2.model.MaliciousIpAddress;
@Repository
public interface MaliciousIpRepository extends JpaRepository<MaliciousIpAddress, Long>{

	MaliciousIpAddress findByIp(String ip);

}
