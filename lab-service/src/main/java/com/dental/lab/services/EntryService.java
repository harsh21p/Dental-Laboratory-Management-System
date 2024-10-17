package com.dental.lab.services;

import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.*;
import com.dental.lab.repository.EntryRepository;
import com.dental.lab.repository.LabMaterialRepository;
import com.dental.lab.repository.LabRepository;
import com.dental.lab.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EntryService {
    private final EntryRepository entryRepository;
    private final LabRepository labRepository;
    private final MaterialRepository materialRepository;
    private final LabService labService;
    private final LabMaterialRepository labMaterialRepository;

    public Entry addEntry(String labId, String doctorId, String materialId, Date entryDate) throws Exception {
        try {
            Optional<Lab> labOpt = labRepository.findById(labId);
            Optional<Material> materialOpt = materialRepository.findById(materialId);
            Doctor doctor = labService.getDoctorById(doctorId);
            if (labOpt.isPresent() && materialOpt.isPresent()) {
                Entry entry = new Entry();
                entry.setLab(labOpt.get());
                entry.setMaterial(materialOpt.get());
                entry.setEntryDate(entryDate);
                entry.setDoctor(doctor);
                return entryRepository.save(entry);

            } else {
                throw new IllegalArgumentException("Lab, Doctor, or Material not found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public PagedResponse<Entry> getEntriesByLab(String labId,int page, int size) throws Exception {
        try {
            Optional<Lab> labOpt = labRepository.findById(labId);
            Pageable pageable = PageRequest.of(page, size);
            if (labOpt.isPresent()) {
                Page<Entry> entries = entryRepository.findByLab(labOpt,pageable);
                return new PagedResponse<Entry>(
                        entries.getContent(),
                        entries.getNumber(),
                        entries.getSize(),
                        entries.getTotalElements(),
                        entries.getTotalPages(),
                        entries.isLast()
                );
            } else {
                throw new IllegalArgumentException("Lab, Doctor, or Material not found");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
