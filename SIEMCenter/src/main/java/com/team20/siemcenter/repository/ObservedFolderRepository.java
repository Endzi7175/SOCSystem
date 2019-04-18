package com.team20.siemcenter.repository;

import com.team20.siemcenter.domain.ObservedFolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ObservedFolder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservedFolderRepository extends JpaRepository<ObservedFolder, Long> {

    @Query(value = "select distinct observed_folder from ObservedFolder observed_folder left join fetch observed_folder.logTypes",
        countQuery = "select count(distinct observed_folder) from ObservedFolder observed_folder")
    Page<ObservedFolder> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct observed_folder from ObservedFolder observed_folder left join fetch observed_folder.logTypes")
    List<ObservedFolder> findAllWithEagerRelationships();

    @Query("select observed_folder from ObservedFolder observed_folder left join fetch observed_folder.logTypes where observed_folder.id =:id")
    Optional<ObservedFolder> findOneWithEagerRelationships(@Param("id") Long id);

}
