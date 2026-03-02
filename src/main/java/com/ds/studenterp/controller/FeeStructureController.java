package com.ds.studenterp.controller;

import com.ds.studenterp.dto.FeeStructureResponse;
import com.ds.studenterp.entity.FeeStructure;
import com.ds.studenterp.service.FeeStructureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fee-structures")
@CrossOrigin
public class FeeStructureController {

    private final FeeStructureService service;

    public FeeStructureController(FeeStructureService service) {
        this.service = service;
    }

    @PostMapping
    public FeeStructure create(@RequestBody FeeStructure fee) {
        return service.create(fee);
    }

    @GetMapping
    public List<FeeStructureResponse> getAll() {
        return service.getAll()
                .stream().map(FeeStructureResponse::new)
                .toList();
    }

    @PutMapping("/{id}")
    public FeeStructure update(@PathVariable Long id,
                               @RequestBody FeeStructure fee) {
        return service.update(id, fee);
    }

    @PatchMapping("/{id}/status")
    public FeeStructure toggle(@PathVariable Long id) {
        return service.toggleStatus(id);
    }
}