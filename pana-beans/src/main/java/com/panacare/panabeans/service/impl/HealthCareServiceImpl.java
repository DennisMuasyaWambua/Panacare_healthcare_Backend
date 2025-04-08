package com.panacare.panabeans.service.impl;

import com.google.common.reflect.TypeToken;
import com.panacare.panabeans.data.entity.DoctorEntity;
import com.panacare.panabeans.data.entity.HealthCareEntity;
import com.panacare.panabeans.data.repository.HealthCareRepository;
import com.panacare.panabeans.dto.HealthCareDto;
import com.panacare.panabeans.dto.HealthCareRequest;
import com.panacare.panabeans.exception.PanaAppException;
import com.panacare.panabeans.service.HealthCareService;
import com.panacare.panabeans.shared.ErrorMessages;
import com.panacare.panabeans.shared.HealthCareCategories;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HealthCareServiceImpl implements HealthCareService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HealthCareRepository healthCareRepo;
    private final ModelMapper modelMapper;
    private final HealthCareRepository healthCareRepository;


    @Transactional
    @Override
    public Page<HealthCareDto> getHealthCareFacilities(HealthCareCategories category, Pageable pageable) {
        Page<HealthCareEntity> healthCareEntities;
        if(category != null) {
            healthCareEntities = healthCareRepo.findByCategory(category, pageable);
        }else{
            healthCareEntities = healthCareRepo.findAll(pageable);
        }

        if (healthCareEntities == null || healthCareEntities.isEmpty()) {
            String errorMsg = String.format("Healthcare %s", ErrorMessages.RECORDS_NOT_FOUND.getErrorMessage());
            throw new PanaAppException(errorMsg);
        }
        Type listType = new TypeToken<Page<HealthCareDto>>() {
        }.getType();
        return modelMapper.map(healthCareEntities, listType);
    }

    @Override
    @Transactional
    public void createHealthCare(HealthCareDto healthCareDto) {
        // Convert DTO to Entity
        HealthCareEntity healthCareEntity = modelMapper.map(healthCareDto, HealthCareEntity.class);

        // Map doctors and set their association with HealthCareEntity
        Set<DoctorEntity> doctorEntities = healthCareDto.getDoctors().stream()
                .map(doctorDto -> {
                    DoctorEntity doctorEntity = modelMapper.map(doctorDto, DoctorEntity.class);
                    doctorEntity.setHealthCare(healthCareEntity); // Associate with healthcare
                    return doctorEntity;
                }).collect(Collectors.toSet());

        // Attach doctors to healthCareEntity
        healthCareEntity.setDoctors(doctorEntities);

        // Save the healthcare facility (cascading should save doctors too)
        healthCareRepository.save(healthCareEntity);
    }


}
