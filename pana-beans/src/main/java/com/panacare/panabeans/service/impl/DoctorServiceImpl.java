package com.panacare.panabeans.service.impl;

import com.google.common.reflect.TypeToken;
import com.panacare.panabeans.data.entity.DoctorEntity;
import com.panacare.panabeans.data.entity.HealthCareEntity;
import com.panacare.panabeans.data.entity.UserEntity;
import com.panacare.panabeans.data.repository.DoctorRepository;
import com.panacare.panabeans.data.repository.HealthCareRepository;
import com.panacare.panabeans.data.repository.UserRepository;
import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.exception.PanaAppException;
import com.panacare.panabeans.service.DoctorService;
import com.panacare.panabeans.shared.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.lang.reflect.Type;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService  {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DoctorRepository doctorRepo;
    private final HealthCareRepository healthCareRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<DoctorDto> getDoctors(Boolean verified, Pageable pageable) {
        Page<DoctorEntity> doctorEntities;
        if(verified != null) {
            doctorEntities = doctorRepo.findByVerified(verified, pageable);
        }else{
            doctorEntities = doctorRepo.findAll(pageable);
        }
        if (doctorEntities == null || doctorEntities.isEmpty()) {
            String errorMsg = String.format("Doctors %s", ErrorMessages.RECORDS_NOT_FOUND.getErrorMessage());
            throw new PanaAppException(errorMsg);
        }
        Type listType = new TypeToken<Page<DoctorDto>>() {
        }.getType();
        return modelMapper.map(doctorEntities, listType);
    }

    @Override
    @Transactional
    public void createDoctor(DoctorDto doctorDto) {
        System.out.println("This is the user ID: "+ doctorDto.getUserId());
        System.out.println("This is the health ID: "+ doctorDto.getHealthCareId());
        DoctorEntity doctorEntity = modelMapper.map(doctorDto, DoctorEntity.class);

        if (doctorDto.getHealthCareId() != null) {
            System.out.println("This is the health Care Id ID: "+ doctorDto.getHealthCareId());
            HealthCareEntity healthCareEntity = healthCareRepository.findById(Long.valueOf(doctorDto.getHealthCareId()))
                    .orElseThrow(() -> new RuntimeException("Healthcare facility not found"));
            System.out.println("This is the health Care object: "+ healthCareEntity);
            doctorEntity.setHealthCare(healthCareEntity);
        }

        if (doctorDto.getUserId() != null) {
            UserEntity userEntity = userRepository.findByUserId(doctorDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userEntity.setDoctor(doctorEntity);

            userRepository.save(userEntity); // Save user first
        }

        doctorRepo.save(doctorEntity); // Then save doctor
    }


}
