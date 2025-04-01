package com.panacare.panabeans.data.repository;

import com.panacare.panabeans.data.entity.HealthCareEntity;
import com.panacare.panabeans.shared.HealthCareCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthCareRepository extends JpaRepository<HealthCareEntity, Long> {
    Optional<HealthCareEntity> findByHealthCareId(String healthCareId);
    Page<HealthCareEntity> findByCategory(HealthCareCategories category, Pageable pageable);
}

