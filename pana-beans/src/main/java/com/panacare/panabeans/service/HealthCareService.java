package com.panacare.panabeans.service;

import com.panacare.panabeans.dto.HealthCareDto;
import com.panacare.panabeans.shared.HealthCareCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HealthCareService {
    Page<HealthCareDto> getHealthCareFacilities(HealthCareCategories category, Pageable pageable);

    void createHealthCare(HealthCareDto healthCareDto);
}
