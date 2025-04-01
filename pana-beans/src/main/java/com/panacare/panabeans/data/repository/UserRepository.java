package com.panacare.panabeans.data.repository;

import com.panacare.panabeans.data.entity.UserEntity;
import io.temporal.workflow.Promise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailIgnoreCase(String email);
    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByVerificationToken(String token);
}

