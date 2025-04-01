package com.panacare.panabeans.service;

import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DoctorService {
    Page<DoctorDto> getDoctors(Boolean isActive, Pageable pageable);

    void createDoctor(DoctorDto doctorDto);
}
