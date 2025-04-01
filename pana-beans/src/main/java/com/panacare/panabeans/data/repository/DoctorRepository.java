package com.panacare.panabeans.data.repository;

import com.panacare.panabeans.data.entity.DoctorEntity;
import com.panacare.panabeans.dto.DoctorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> findByDoctorId(String doctorId);

    Page<DoctorEntity> findByVerifiedTrue(Pageable pageable);
    Page<DoctorEntity> findByVerified(Boolean verified, Pageable pageable);
}

