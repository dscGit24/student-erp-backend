package com.ds.studenterp.service;

import com.ds.studenterp.entity.FeeStructure;
import com.ds.studenterp.repository.FeeStructureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeStructureService {

    private final FeeStructureRepository repository;

    public FeeStructureService(FeeStructureRepository repository) {
        this.repository = repository;
    }

    public FeeStructure create(FeeStructure fee) {
        return repository.save(fee);
    }

    public List<FeeStructure> getAll() {
        return repository.findAll();
    }

    public FeeStructure update(Long id, FeeStructure updated) {
        FeeStructure existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fee structure not found"));

        existing.setTuitionFee(updated.getTuitionFee());
        existing.setExamFee(updated.getExamFee());
        existing.setOtherCharges(updated.getOtherCharges());
        existing.setActive(updated.getActive());

        return repository.save(existing);
    }

    public FeeStructure toggleStatus(Long id) {
        FeeStructure fee = repository.findById(id).orElseThrow();

        fee.setActive(!fee.getActive());
        return repository.save(fee);
    }
}