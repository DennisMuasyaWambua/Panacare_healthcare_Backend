package com.panacare.panabeans.data.repository;

import com.panacare.panabeans.data.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String roleName);
    RoleEntity findByRoleId(String roleId);

    Page<RoleEntity> findByNameContainsIgnoreCaseOrRoleIdContainsIgnoreCase(String name, String roleId, Pageable pageable);

}

